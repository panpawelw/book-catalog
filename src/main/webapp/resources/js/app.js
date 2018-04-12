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
						.append('<button class="edit" id="' + books[book].id + '">edit</button><button class="delete" id="' + books[book].id + '">delete</button>')
						.append('<div class="bookDetails" id="' + books[book].id + '"></div><div class="editBook" id="' + books[book].id + '"></div>');
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
			                .append($('<td>', {text: key}, '</td>'))
			                .append($('<td>', {text: book[key]}, '</td>'))
			                .append($('</tr>')))
		        }
				html.append($('</table>'));
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
		var bookNumber = this.id;
		if(!$(this).hasClass('editShown')){
			$(this).addClass('editShown');
			$.ajax({
				type: 'GET',
				url: baseURL + bookNumber,
				dataType: 'JSON'
			})
			.done(function(book){
				var html = '<form action="books/update/'
					html += bookNumber.toString();
					html += '" method="put"><table>';
				for (var key in book) {
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
				html += '<tr><td><button type="reset" class="editCancel">Cancel</button></td>';
				html += '<td><input type="submit" value="Edit this book entry"/></td></tr></table></form>';
				$('div#' + bookNumber + '.editBook').append(html);
				$('div#' + bookNumber + '.editBook').show(333);
				var theBook={id:3, isbn:'666', title:'whatever', author:'whoever', publisher:'blahblah', type:'mboo'};
				alert(JSON.stringify(theBook));
				$.ajax({
					url: baseURL + 'update/' + theBook.id,
					type: 'PUT',
					contentType: 'application/json',
					data: JSON.stringify(theBook)
					}).done(function (){
						alert('done!');
					}).fail(function (){
						alert('fail!');
					});
				});
		}else{
			$(this).removeClass('editShown');
			$('div#' + bookNumber + '.editBook').hide(333);
			$('div#' + bookNumber + '.editBook').html('');
		}
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
	$(document).on('click', '.addCancel', addButtonClick);
	$(document).on('click', '.edit', editButtonClick);
	$(document).on('click', '.delete', deleteButtonClick);
	getBookList();
	
});