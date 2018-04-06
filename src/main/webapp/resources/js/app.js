$(document).ready(function(){
	
	var bookList = $('ul.bookList');
	
	function getBookList() {
			var books;
			$.ajax({
				url: 'http://localhost:8080/Workshop_4and5/books/',
				type: 'GET',
				dataType: 'json'})
				.done(function(books){
					bookList.empty();
					for(book in books) {
						bookList.append('<li> ' + books[book].id + ' "' + books[book].title + '" ' + books[book].author)
						.append(('<button class="edit">edit</button><button class="delete">delete</button><div></div>'));
					}
					
				})
				.fail(function(){alert('Error!')})
				.always(function(){});
	}
	
	getBookList();
	
});