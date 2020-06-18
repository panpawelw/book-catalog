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
                    <li class="list-item">
                        <div id="${books[book].id}">
                            ${books[book].id}."${books[book].title}" ${books[book].author}
                            <br>
                            <button class="details">details</button>
                            <button class="update">update</button>
                            <button class="delete">delete</button>
                            <div class="book-details"></div>
                            <div class="update-book"></div>
                        </div>
                    </li>
                `);
            }
        })
            .fail(function () {
                alert('Error getting book list!')
            })
            .always(function () {
            });
    }

    function showBookDetails() {
        let bookNumber = this.id;
        if (!$(this).hasClass('details-shown')) {
            $(this).addClass('details-shown');
            $('div#' + bookNumber + '.book-details').hide(1);
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
                    $(`div#${bookNumber}.book-details`).html(html).show(333);
                })
                .fail(function () {
                    alert('Error getting book details!')
                });
        } else {
            $(this).removeClass('details-shown');
            $('div#' + bookNumber + '.book-details').hide(333);
        }
    }

    function hideBookDetails() {
        $(this).hide(333);
        $('li#' + this.id + '.details-shown').removeClass('details-shown');
    }

    function toggleBookDetails() {

    }

    function addButtonClick() {
        $('div.add-book').toggle(333);
    }

    function updateButtonClick() {
        let bookNumber = this.parentNode.id;
        const updateContainer = this.parentNode.querySelector('.update-book');
        if (!$(this).hasClass('update-shown')) {
            $(this).addClass('update-shown');
            $.ajax({
                type: 'GET',
                url: baseURL + bookNumber,
                dataType: 'JSON'
            })
                .done(function (book) {
                    let html = '<form class="update-form" id="';
                    html += bookNumber;
                    html += action = '"books/update/';
                    html += bookNumber;
                    html += '" method="put">';
                    for (let key in book) {
                        if (key === 'id') {
                            html += '<input type="hidden" id="id" name="id" value="';
                            html += book[key].toString();
                            html += '"/>';
                            html += '<table>';
                        } else {
                            html += '<tr><td><label for="';
                            html += key.toString();
                            html += '">';
                            html += key.toString();
                            html += '</td><td><input id="';
                            html += key.toString();
                            html += '" type="text" name="';
                            html += key.toString();
                            html += '" value="';
                            html += book[key].toString();
                            html += '"/></td>';
                        }
                    }
                    html += '<tr><td><button id="'
                    html += bookNumber;
                    html += '" type="reset" class="update-cancel">Cancel</button></td>';
                    html += '<td><input class="update-submit" type="submit" value="Edit this' +
                        ' book entry"/></td></tr></table></form>';
                    $(updateContainer).append(html).show(333);
                })
                .fail(function () {
                    alert('Error updating book details!')
                });
        } else {
            $(this).removeClass('update-shown');
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
                alert('Error getting book list!')
            });
    }

    function updateCancelClick() {
        let bookNumber = this.id;
        $('button#' + bookNumber + '.update-shown').removeClass('update-shown');
        $(`div#${bookNumber}.update-book`).hide(333).html('');
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

    // $(document).on('click', '.list-item', showBookDetails);
    // $(document).on('click', '.book-details', hideBookDetails);
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