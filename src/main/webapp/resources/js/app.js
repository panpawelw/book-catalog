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
                            <button class="btn btn-primary details-button">details</button>
                            <button class="btn btn-primary update-button">update</button>
                            <button class="btn btn-primary delete-button">delete</button>
                            <div id="details-${books[book].id}"class="details" 
                                style="display: none;"></div>
                        </div>
                    </li>
                `);
            }
        }).fail(function () {
            alert('Error retrieving book list!')
        });
    }

    function detailsHandler() {
        const bookNumber = this.parentNode.id;
        const bookListEntry = $(`li#book-${bookNumber}`);
        if(bookListEntry.hasClass('details-shown')
            && bookListEntry.hasClass('edition-enabled'))
        {
            toggleBookEditControls(bookNumber);
            return;
        }
        toggleBookDetails(bookNumber);
    }

    function updateHandler() {
        const bookNumber = this.parentNode.id;
        const bookListEntry = $(`li#book-${bookNumber}`);
        if(bookListEntry.hasClass('details-shown')
            && bookListEntry.hasClass('edition-enabled'))
        {
            toggleBookDetails(bookNumber);
            return;
        }
        if(bookListEntry.hasClass('details-shown')) {
            toggleBookEditControls(bookNumber);
        } else {
            toggleBookDetails(bookNumber, function() {toggleBookEditControls(bookNumber)});
        }
    }

    function addHandler() {
        $('div.add-book').toggle(333);
    }

    function deleteHandler() {
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
        const bookDetailsDiv = $(`div#details-${bookNumber}`);
        if (bookListEntry.hasClass('details-shown')) {
            bookListEntry.removeClass('details-shown edition-enabled');
            bookDetailsDiv.hide(333);
            setTimeout(function () {
                bookDetailsDiv.html('');
            }, 333);
            return;
        }
        bookListEntry.addClass('details-shown');
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
                    <td><button type="reset" class="update-cancel btn btn-primary "
                        id="update-cancel-button-${bookNumber}">Cancel</button></td>
                        <td><input id="update-submit-button-${bookNumber}" type="button" 
                            class="update-submit btn btn-primary" value="Edit this book entry"/></td>
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

    function updateSubmitHandler() {
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

    function updateCancelHandler() {
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

    $(document).on('click', '.details-button', detailsHandler);
    $(document).on('click', '.update-button', updateHandler);
    $(document).on('click', '.delete-button', deleteHandler);
    $(document).on('click', '.update-submit', updateSubmitHandler);
    $(document).on('click', '.update-cancel', updateCancelHandler);
    $(document).on('click', '#add-button', addHandler);
    $(document).on('click', '#add-cancel', addHandler);
    $(document).on('click', '#memory-database', switchToMemoryDatabase);
    $(document).on('click', '#mysql-database', switchToMysqlDatabase);
    getBookList();
});