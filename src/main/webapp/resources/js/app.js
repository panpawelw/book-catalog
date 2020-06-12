$(document).ready(function(){

    const baseURL = window.location.pathname + 'books/';
    let bookList = $('ul.book-list');

    function getBookList() {

        $.ajax({
            type: 'GET',
            url: baseURL,
            dataType: 'JSON'})
            .done(function(books){
                bookList.empty();
                for(let book in books) {
                    bookList.append('<li class="list-item" id=' + books[book].id + '> ' + books[book].id + '. "' + books[book].title + '" ' + books[book].author)
                        .append('<button class="update" id="' + books[book].id + '">update</button><button class="delete" id="' + books[book].id + '">delete</button>')
                        .append('<div class="book-details" id="' + books[book].id + '"></div><div class="update-book" id="' + books[book].id + '"></div>');
                }
            })
            .fail(function(){alert('Error!')})
            .always(function(){});
    }

    function showBookDetails(){
        let bookNumber = this.id;
        if(!$(this).hasClass('details-shown')){
            $(this).addClass('details-shown');
            $('div#' + bookNumber + '.book-details').hide(1);
            $.ajax({
                type: 'GET',
                url: baseURL + bookNumber,
                dataType: 'JSON'
            })
                .done(function(book){
                    let html = $('<table>');
                    for (let key in book) {
                        html.append($('<tr>')
                            .append($('<td>', {text: key}, '</td>'))
                            .append($('<td>', {text: book[key]}, '</td>'))
                            .append($('</tr>')))
                    }
                    html.append($('</table>'));
                    $('div#' + bookNumber + '.book-details').html(html);
                    $('div#' + bookNumber + '.book-details').show(333);
                });
        }else {
            $(this).removeClass('details-shown');
            $('div#' + bookNumber + '.book-details').hide(333);
        }
    }

    function hideBookDetails(){
        $(this).hide(333);
        $('li#'+ this.id +'.details-shown').removeClass('details-shown');
    }

    function addButtonClick(){
        $('div.add-book').toggle(333);
    }

    function updateButtonClick(){
        let bookNumber = this.id;
        if(!$(this).hasClass('update-shown')){
            $(this).addClass('update-shown');
            $.ajax({
                type: 'GET',
                url: baseURL + bookNumber,
                dataType: 'JSON'
            })
                .done(function(book){
                    let html = '<form class="update-form" id="';
                    html += bookNumber;
                    html += action='"books/update/';
                    html += bookNumber;
                    html += '" method="put">';
                    for (let key in book) {
                        if(key==='id'){
                            html += '<input type="hidden" id="id" name="id" value="';
                            html += book[key].toString();
                            html += '"/>';
                            html +='<table>';
                        }else{
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
                    $('div#' + bookNumber + '.update-book').append(html);
                    $('div#' + bookNumber + '.update-book').show(333);
                });
        }else{
            $(this).removeClass('update-shown');
            $('div#' + bookNumber + '.update-book').hide(333);
            $('div#' + bookNumber + '.update-book').html('');
        }
    }

    function deleteButtonClick(){
        $.ajax({
            type: 'DELETE',
            url: baseURL + this.id,
        })
            .done(function(){
                getBookList();
            })
    }

    function updateSubmitClick(){
        let bookRough = $('form.update-form').serializeArray();
        let book = {};
        $.map(bookRough, function(n, i){
            book[n['name']] = n['value'];
        });
        book.id = Number(book.id);
        $.ajax({
            url: baseURL + 'update/' + book.id,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(book),
        }).done(function (){
            getBookList();
        });
    }

    function updateCancelClick(){
        let bookNumber = this.id;
        $('button#' + bookNumber + '.update-shown').removeClass('update-shown');
        $('div#' + bookNumber + '.update-book').hide(333);
        $('div#' + bookNumber + '.update-book').html('');
    }

    $(document).on('click', '.list-item', showBookDetails);
    $(document).on('click', '.book-details', hideBookDetails);
    $(document).on('click', '.add', addButtonClick);
    $(document).on('click', '.add-cancel', addButtonClick);
    $(document).on('click', '.update', updateButtonClick);
    $(document).on('click', '.delete', deleteButtonClick);
    $(document).on('click', '.update-submit', updateSubmitClick);
    $(document).on('click', '.update-cancel', updateCancelClick);
    getBookList();
});