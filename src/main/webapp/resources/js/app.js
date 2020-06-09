$(document).ready(function(){

    let baseURL = window.location.href + '/books/';
    let bookList = $('ul.bookList');

    function getBookList() {
        $.ajax({
            type: 'GET',
            url: baseURL,
            dataType: 'JSON'})
            .done(function(books){
                bookList.empty();
                for(let book in books) {
                    bookList.append('<li class="listItem" id=' + books[book].id + '> ' + books[book].id + '. "' + books[book].title + '" ' + books[book].author)
                        .append('<button class="update" id="' + books[book].id + '">update</button><button class="delete" id="' + books[book].id + '">delete</button>')
                        .append('<div class="bookDetails" id="' + books[book].id + '"></div><div class="updateBook" id="' + books[book].id + '"></div>');
                }
            })
            .fail(function(){alert('Error!')})
            .always(function(){});
    }

    function showBookDetails(){
        let bookNumber = this.id;
        if(!$(this).hasClass('detailsShown')){
            $(this).addClass('detailsShown');
            $('div#' + bookNumber + '.bookDetails').hide(1);
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
                    $('div#' + bookNumber + '.bookDetails').html(html);
                    $('div#' + bookNumber + '.bookDetails').show(333);
                });
        }else {
            $(this).removeClass('detailsShown');
            $('div#' + bookNumber + '.bookDetails').hide(333);
        }
    }

    function hideBookDetails(){
        $(this).hide(333);
        $('li#'+ this.id +'.detailsShown').removeClass('detailsShown');
    }

    function addButtonClick(){
        $('div.addBook').toggle(333);
    }

    function updateButtonClick(){
        let bookNumber = this.id;
        if(!$(this).hasClass('updateShown')){
            $(this).addClass('updateShown');
            $.ajax({
                type: 'GET',
                url: baseURL + bookNumber,
                dataType: 'JSON'
            })
                .done(function(book){
                    let html = '<form class="updateForm" id="';
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
        let bookRough = $('form.updateForm').serializeArray();
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