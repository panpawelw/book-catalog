$(document).ready(function(){
	
	var baseURL = 'http://localhost:8080/Workshop_4and5/books/';
	var bookList = $('ul.bookList');
	
	function getBookList() {
			var books;
//			$.getJSON(baseURL)
			$.ajax({
				type: 'GET',
				url: baseURL,
				dataType: 'JSON'})
				.done(function(books){
					bookList.empty();
					for(book in books) {
						bookList.append('<li class="listItem" id=' + books[book].id + '> ' + books[book].id + '. "' + books[book].title + '" ' + books[book].author)
						.append('<button class="edit" id="' + books[book].id + '">edit</button><button class="delete" id="' + books[book].id + '">delete</button><div class="bookDetails" id="' + books[book].id + '"></div>');
					}
				})
				.fail(function(){alert('Error!')})
				.always(function(){});
	}
	
	function showBookDetails(event){
		if(!$(this).hasClass('detailsShown')){
			$(this).addClass('detailsShown');
			var bookNumber = this.id;
//			$('div[id=' + bookNumber + ']').hide(1);
			$('div#' + bookNumber + '.bookDetails').hide(1);
	//		$.getJSON(baseURL + bookNumber)
			$.ajax({
				type: 'GET',
				url: baseURL + bookNumber,
				dataType: 'JSON'
			})
			.done(function(book){
				var html = $('<table>');
				for (var key in book) {
		            html.append($('<tr>')
		                .append($('<td>', {text: key}))
		                .append($('<td>', {text: book[key]}))
		                .append($('</tr>')))
		        }
				html.append($('</table>'));
				html.append($(''));
//				$('div[id=' + bookNumber + ']').html(html);
//				$('div[id=' + bookNumber + ']').show(333);
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
	
	function editButtonClick(event){
		alert('Edit ' + this.id);
	}
	
	function deleteButtonClick(event){
		$.ajax({
			type: 'DELETE',
			url: baseURL + this.id
			})
			.done(function(){
				getBookList();
			})
	}
	
	$(document).on('click', '.listItem', showBookDetails);
	$(document).on('click', '.bookDetails', hideBookDetails);
	$(document).on('click', '.add', addButtonClick);
	$(document).on('click', '.edit', editButtonClick);
	$(document).on('click', '.delete', deleteButtonClick);
	getBookList();
	
});