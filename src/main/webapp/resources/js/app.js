$(document).ready(function(){
	
	var baseURL = 'http://localhost:8080/Workshop_4and5/books/';
	var bookList = $('ul.bookList');
	
	function getBookList() {
			var books;
			$.ajax({
				type: 'GET',
				url: baseURL,
				dataType: 'json'})
				.done(function(books){
					bookList.empty();
					for(book in books) {
						bookList.append('<li class="listItem" id=' + books[book].id + '> ' + books[book].id + ' "' + books[book].title + '" ' + books[book].author)
						.append('<button class="edit" id="' + books[book].id + '">edit</button><button class="delete" id="' + books[book].id + '">delete</button><div id="div' + books[book].id + '"></div>');
					}
				})
				.fail(function(){alert('Error!')})
				.always(function(){});
	}
	
	function showBookDetails(event){
		alert('Book ' + this.id + ' details...');
		$('#div' + this.id).html('Book ' + this.id + ' details...');
	}
	function addButtonClick(event){
		alert('Add!');
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
	$(document).on('click', '.add', addButtonClick);
	$(document).on('click', '.edit', editButtonClick);
	$(document).on('click', '.delete', deleteButtonClick);
	getBookList();
	
});