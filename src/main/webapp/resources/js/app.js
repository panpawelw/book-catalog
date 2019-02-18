$(document).ready(function(){

    var baseURL = 'http://localhost:8080/book_catalog/books/';
    // var baseURL = 'http://panpawelw:8080/book_catalog/books/';
    var bookList = $('ul.bookList');

    function getBookList() {
        var books;
        $.ajax({
            type: 'GET',
            url: baseURL,
            dataType: 'JSON'})
            .done(function(books){
                bookList.empty();
                for(book in books) {
                    bookList.append('<li class="listItem" id=' + books[book].id + '> ' + books[book].id + '. "' + books[book].title + '" ' + books[book].author)
                        .append('<button class="update" id="' + books[book].id + '">update</button><button class="delete" id="' + books[book].id + '">delete</button>')
                        .append('<div class="bookDetails" id="' + books[book].id + '"></div><div class="updateBook" id="' + books[book].id + '"></div>');
                }
            })
            .fail(function(){alert('Error!')})
            .always(function(){});
    }

    function showBookDetails(event){
        if(!$(this).hasClass('detailsShown')){
            $(this).addClass('detailsShown');
            var bookNumber = this.id;
            $('div#' + bookNumber + '.bookDetails').hide(1);
            $.ajax({
                type: 'GET',
                url: baseURL + bookNumber,
                dataType: 'JSON'
            })
                .done(function(book){
                    var html = $('<table>');
                    for (var key in book) {
                        html.append($('<tr>')
                            .append($('<td>', {text: key}, '</td>'))
                            .append($('<td>', {text: book[key]}, '</td>'))
                            .append($('</tr>')))
                    }
                    html.append($('</table>'));
                    $('div#' + bookNumber + '.bookDetails').html(html);
                    $('div#' + bookNumber + '.bookDetails').show(333);
                });
        }
    }

    function hideBookDetails(event){
        $(this).hide(333);
        $('li#'+ this.id +'.detailsShown').removeClass('detailsShown');
    }

    function addButtonClick(event){
        $('div.addBook').toggle(333);
    }

    function updateButtonClick(event){
        var bookNumber = this.id;
        if(!$(this).hasClass('updateShown')){
            $(this).addClass('updateShown');
            $.ajax({
                type: 'GET',
                url: baseURL + bookNumber,
                dataType: 'JSON'
            })
                .done(function(book){
                    var html = '<form class="updateForm" id="';
                    html += bookNumber;
                    html += action='"books/update/';
                    html += bookNumber;
                    html += '" method="put">';
                    for (var key in book) {
                        if(key=='id'){
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
                    html += '" type="reset" class="updateCancel">Cancel</button></td>';
                    html += '<td><input class="updateSubmit" type="submit" value="Edit this book entry"/></td></tr></table></form>';
                    $('div#' + bookNumber + '.updateBook').append(html);
                    $('div#' + bookNumber + '.updateBook').show(333);
                });
        }else{
            $(this).removeClass('updateShown');
            $('div#' + bookNumber + '.updateBook').hide(333);
            $('div#' + bookNumber + '.updateBook').html('');
        }
    }

    function deleteButtonClick(event){
        $.ajax({
            type: 'DELETE',
            url: baseURL + this.id,
            async: false
        })
            .done(function(){
                getBookList();
            })
    }

    function updateSubmitClick(event){
        var bookRough = $('form.updateForm').serializeArray();
        var book = {};
        $.map(bookRough, function(n, i){
            book[n['name']] = n['value'];
        });
        book.id = Number(book.id);
        $.ajax({
            url: baseURL + 'update/' + book.id,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(book),
            async: false
        }).done(function (){
            getBookList();
        });
    }

    function updateCancelClick(event){
        var bookNumber = this.id;
        $('button#' + bookNumber + '.updateShown').removeClass('updateShown');
        $('div#' + bookNumber + '.updateBook').hide(333);
        $('div#' + bookNumber + '.updateBook').html('');
    }

    $(document).on('click', '.listItem', showBookDetails);
    $(document).on('click', '.bookDetails', hideBookDetails);
    $(document).on('click', '.add', addButtonClick);
    $(document).on('click', '.addCancel', addButtonClick);
    $(document).on('click', '.update', updateButtonClick);
    $(document).on('click', '.delete', deleteButtonClick);
    $(document).on('click', '.updateSubmit', updateSubmitClick);
    $(document).on('click', '.updateCancel', updateCancelClick);
    getBookList();

});