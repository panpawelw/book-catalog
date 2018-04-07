$(document).ready(function(){
	
	var baseURL = 'http://localhost:8080/Workshop_4and5/books/';
	var bookList = $('ul.bookList');
	
	function getBookList() {
			var books;
			$.ajax({
				url: baseURL,
				type: 'GET',
				dataType: 'json'})
				.done(function(books){
					bookList.empty();
					for(book in books) {
						bookList.append('<li id=' + books[book].id + '> ' + books[book].id + ' "' + books[book].title + '" ' + books[book].author)
						.append('<button class="edit" id="' + books[book].id + '">edit</button><button class="delete" id="' + books[book].id + '">delete</button><div></div>');
					}
					
				})
				.fail(function(){alert('Error!')})
				.always(function(){});
	}
	
	function addButtonClick(event){
		alert('Add!');
	}
	
	function editButtonClick(event){
		alert('Edit ' + this.id);
	}
	
	function deleteButtonClick(event){
		alert('Delete ' + this.id);
		$.ajax({
			url: baseURL + this.id,
			type: 'DELETE'})
			.done(function(){alert ('Delete successful!');})
		getBookList();
	}
	
	$(document).on('click', '.add', addButtonClick);
	$(document).on('click', '.edit', editButtonClick);
	$(document).on('click', '.delete', deleteButtonClick);
	getBookList();
	
});