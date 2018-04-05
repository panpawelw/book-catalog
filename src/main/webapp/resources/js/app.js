$(document).ready(function(){
	
	var bookList = $('ul.bookList');
	
	function getBookList() {
			var books;
			$.ajax({
				url: 'http://localhost:8080/Workshop_4and5/books/',
				data: {books: books},
				type: 'GET',
				dataType: 'json'})
				.done(function(result){
					bookList.empty();
					for(var i=0;i<result.length;i++){
						bookList.append('<li>' + result[i].id + '. "' + result[i].title + '" ' + result[i].author)
						.append('<button class="edit">Edit</button><button class="delete">Delete</button>');
					}
				})
				.fail(function(){alert('Error!')})
				.always(function(){});
	}
	
	getBookList();
	
});