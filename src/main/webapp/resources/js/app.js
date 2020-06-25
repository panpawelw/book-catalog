$(document).ready(function () {

    const baseURL = window.location.pathname + 'books/';
    let bookList = $('ul.book-list');

    function getBookList() {
        $.ajax({
            type: 'GET',
            url: baseURL,
            dataType: 'JSON'
        }).done(function (books) {
            bookList.empty();
            for (let book in books) {
                bookList.append(`
                    <li id="book-${books[book].id}">
                        <div id="${books[book].id}">
                            ${books[book].id}."${books[book].title}" ${books[book].author}<br>
                            <button id="details-button-${books[book].id}" class="details">details</button>
                            <button id="update-button-${books[book].id}" class="update">update</button>
                            <button id="test1-${books[book].id}" class="test1">test1</button>
                            <button id="test2-${books[book].id}" class="test2">test2</button>
                            <button class="delete">delete</button>
                            <div id="new-details-${books[book].id}" 
                                class="new-details" style="display: none;"></div>
                        </div>
                    </li>
                `);
            }
        }).fail(function () {
            alert('Error retrieving book list!')
        });
    }

    function detailsButtonHandler() {
        const bookNumber = this.parentNode.id;
        const bookListEntry = $(`li#book-${bookNumber}`);
        if(bookListEntry.hasClass('new-details-shown')
            && bookListEntry.hasClass('edition-enabled'))
        {
            toggleBookEditControls(bookNumber);
            return;
        }
        toggleBookDetails(bookNumber);
    }

    function updateButtonHandler() {
        const bookNumber = this.parentNode.id;
        const bookListEntry = $(`li#book-${bookNumber}`);
        if(bookListEntry.hasClass('new-details-shown')
            && bookListEntry.hasClass('edition-enabled'))
        {
            toggleBookDetails(bookNumber);
            return;
        }
        if(bookListEntry.hasClass('new-details-shown')) {
            toggleBookEditControls(bookNumber);
        } else {
            toggleBookDetails(bookNumber, function() {toggleBookEditControls(bookNumber)});
        }
    }

    function addButtonHandler() {
        $('div.add-book').toggle(333);
    }

    function deleteButtonHandler() {
        $.ajax({
            type: 'DELETE',
            url: baseURL + this.parentNode.id,
        })
            .done(function () {
                getBookList();
            })
            .fail(function () {
                alert('Error deleting book!')
            });
    }

    function toggleBookDetails(bookNumber, callback) {
        const bookListEntry = $(`li#book-${bookNumber}`);
        const bookDetailsDiv = $(`div#new-details-${bookNumber}`);
        if (bookListEntry.hasClass('new-details-shown')) {
            bookListEntry.removeClass('new-details-shown edition-enabled');
            bookDetailsDiv.hide(333);
            setTimeout(function () {
                bookDetailsDiv.html('');
            }, 333);
            return;
        }
        bookListEntry.addClass('new-details-shown');
        $.ajax({
            type: 'GET',
            url: baseURL + bookNumber,
            dataType: 'JSON'
        }).done(function (book) {
            let html = `
                <form class="update-form" action="books/update/${bookNumber}" method="put">
                `;
            for (let key in book) {
                if (key === 'id') {
                    html += `
                        <table>
                            <tr>
                                <td><label for="id">id</label></td>
                                <td><input id="id" name="id" value="${book[key]}" readonly/></td>
                            </tr>
                        `;
                } else {
                    html += `
                        <tr>
                            <td><label for="${key}">${key}</label></td>
                            <td><input id="${key}" name="${key}" class="editable" 
                                type="text" value="${book[key]}" readonly/></td>
                        </tr>
                        `;
                }
            }
            html += `
                <tr class="update-controls" style="display: none;">
                    <td><button type="reset" class="update-cancel"
                        id="update-cancel-button-${bookNumber}">Cancel</button></td>
                        <td><input id="update-submit-button-${bookNumber}" type="button" 
                            class="update-submit" value="Edit this book entry"/></td>
                </tr></table></form>
                `;
            bookDetailsDiv.append(html);
            if (callback && typeof (callback) === 'function') callback();
            bookDetailsDiv.show(333);
        })
            .fail(function () {
                alert('Error retrieving book details!')
            });
    }

    function toggleBookEditControls(bookNumber) {
        for (let input of $(`li#book-${bookNumber} input.editable`)) {
            input.toggleAttribute('readonly');
        }
        $(`li#book-${bookNumber} tr.update-controls`).toggle();
        $(`li#book-${bookNumber}`).toggleClass('edition-enabled');
    }

    function updateSubmitClick() {
        let bookRough = $('form.update-form').serializeArray();
        let book = {};
        $.map(bookRough, function (n, i) {
            book[n['name']] = n['value'];
        });
        book.id = Number(book.id);
        $.ajax({
            url: baseURL + 'update/' + book.id,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(book),
        })
            .done(function () {
                getBookList();
            })
            .fail(function () {
                alert('Error updating book details')
            });
    }

    function updateCancelClick() {
        const bookNumber = this.id.split('-')[3];
        document.getElementById(`book-${bookNumber}`)
            .classList.remove('update-shown');
        $(`#update-div-${bookNumber}`).hide(333).html('');
    }

    function switchToMemoryDatabase() {
        $.get(baseURL + 'memorydatabase/', {async: false}, function () {
        }).done(function () {
            getBookList();
        })
    }

    function switchToMysqlDatabase() {
        $.get(baseURL + 'mysqldatabase/', {async: false}, function () {
        }).done(function () {
            getBookList();
        })
    }

    $(document).on('click', '.details', detailsButtonHandler);
    $(document).on('click', '.book-details', detailsButtonHandler);
    $(document).on('click', '.add', addButtonHandler);
    $(document).on('click', '.add-cancel', addButtonHandler);
    $(document).on('click', '.update', updateButtonHandler);
    $(document).on('click', '.delete', deleteButtonHandler);
    $(document).on('click', '.update-submit', updateSubmitClick);
    $(document).on('click', '.update-cancel', updateCancelClick);
    $(document).on('click', '#memory-database', switchToMemoryDatabase);
    $(document).on('click', '#mysql-database', switchToMysqlDatabase);
    getBookList();
});