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
                            <div id="details-div-${books[book].id}" class="book-details"></div>
                            <div id="update-div-${books[book].id}" class="update-book"></div>
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
        let bookNumber = this.parentNode.id;
        toggleBookDetails(bookNumber);
    }

    function updateButtonHandler() {
        let bookNumber = this.parentNode.id;
        toggleBookDetails(bookNumber);
        toggleBookDetailsEdition(bookNumber);
    }

    function toggleBookDetails(bookNumber) {
        const bookListEntry = $(`li#book-${bookNumber}`);
        const bookDetailsDiv = $(`div#new-details-${bookNumber}`);
        if (bookListEntry.hasClass('new-details-shown')) {
            bookListEntry.removeClass('new-details-shown');
            bookDetailsDiv.hide(333);
            setTimeout(function () {bookDetailsDiv.html('');}, 333);
            return;
        }
        bookListEntry.addClass('new-details-shown');
        $.ajax({
            type: 'GET',
            url: baseURL + bookNumber,
            dataType: 'JSON'
        })
            .done(function (book) {
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
                                <td><input id="${key}" name="${key}" 
                                      type="text" value="${book[key]}" readonly/></td>
                            </tr>
                        `;
                    }
                }
                html += `
                    <tr id="update-controls" style="display: none;">
                        <td><button type="reset" class="update-cancel"
                                id="update-cancel-button-${bookNumber}">Cancel</button></td>
                        <td><input id="update-submit-button-${bookNumber}" type="button" 
                                class="update-submit" value="Edit this book entry"/></td>
                    </tr>
                    </table>
                    </form>
                `;
                $(`div#new-details-${bookNumber}`).append(html).show(333);
            })
            .fail(function () {
                alert('Error retrieving book details!')
            });
    }

    function toggleBookDetailsEdition(bookNumber) {
        console.log('Enable book details edition - book' + bookNumber);
    }

    function showBookDetails() {
        let bookNumber = this.parentNode.id;
        if (!$(`li#book-${bookNumber}`).hasClass('details-shown')) {
            $(`li#book-${bookNumber}`).addClass('details-shown');
            $(`div#details-div-${bookNumber}`).hide(1);
            $.ajax({
                type: 'GET',
                url: baseURL + bookNumber,
                dataType: 'JSON'
            })
                .done(function (book) {
                    let html = $('<table>');
                    for (let key in book) {
                        html.append($('<tr>')
                            .append($('<td>', {text: key}, '</td>'))
                            .append($('<td>', {text: book[key]}, '</td>'))
                            .append($('</tr>')))
                    }
                    html.append($('</table>'));
                    $(`div#details-div-${bookNumber}`).html(html).show(333);
                })
                .fail(function () {
                    alert('Error retrieving book details!')
                });
        } else {
            $(`li#book-${bookNumber}`).removeClass('details-shown');
            $(`div#details-div-${bookNumber}`).hide(333);
        }
    }

    function hideBookDetails() {
        $(this).hide(333);
        $(`li#book-${this.parentNode.id}`).removeClass('details-shown');
    }

    function addButtonClick() {
        $('div.add-book').toggle(333);
    }

    function updateButtonClick() {
        let bookNumber = this.parentNode.id;
        const updateContainer = this.parentNode.querySelector('.update-book');
        if (!$(`li#book-${bookNumber}`).hasClass('update-shown')) {
            $(`li#book-${bookNumber}`).addClass('update-shown');
            $.ajax({
                type: 'GET',
                url: baseURL + bookNumber,
                dataType: 'JSON'
            })
                .done(function (book) {
                    let html = `
                        <form class="update-form" action="books/update/${bookNumber}" method="put">
                    `;
                    for (let key in book) {
                        if (key === 'id') {
                            html += `<input type="hidden" id="id" name="id" value="${book[key]}"/>
                                <table>`;
                        } else {
                            html += `
                                <tr>
                                    <td>
                                        <label for="${key}">${key}</label>
                                    </td>
                                    <td>
                                        <input id="${key}" name="${key}" 
                                            type="text" value="${book[key]}"/>
                                    </td>
                                </tr>
                            `;
                        }
                    }
                    html += `
                                <tr>
                                    <td>
                                        <button type="reset" class="update-cancel"
                                            id="update-cancel-button-${bookNumber}">Cancel</button>
                                    </td>
                                    <td>
                                        <input id="update-submit-button-${bookNumber}" type="button" 
                                            class="update-submit" value="Edit this book entry"/>
                                    </td>
                                </tr>
                            </table>
                        </form>`;
                    $(updateContainer).append(html).show(333);
                })
                .fail(function () {
                    alert('Error updating book details!')
                });
        } else {
            $(`li#book-${bookNumber}`).removeClass('update-shown');
            $(updateContainer).hide(333).html('');
        }
    }

    function deleteButtonClick() {
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

    $(document).on('click', '.test1', detailsButtonHandler);
    $(document).on('click', '.test2', updateButtonHandler);
    $(document).on('click', '.details', showBookDetails);
    $(document).on('click', '.book-details', hideBookDetails);
    $(document).on('click', '.add', addButtonClick);
    $(document).on('click', '.add-cancel', addButtonClick);
    $(document).on('click', '.update', updateButtonClick);
    $(document).on('click', '.delete', deleteButtonClick);
    $(document).on('click', '.update-submit', updateSubmitClick);
    $(document).on('click', '.update-cancel', updateCancelClick);
    $(document).on('click', '#memory-database', switchToMemoryDatabase);
    $(document).on('click', '#mysql-database', switchToMysqlDatabase);
    getBookList();
});