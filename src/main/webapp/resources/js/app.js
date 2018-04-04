$(document).ready(function(){
	
	function testConnection() {
		$('.testButton').on('click', function(event){
			var books;
			$.ajax({
				url: 'http://localhost:8080/Workshop_4and5/books/helloBook',
				data: {books: books},
				type: 'GET',
				dataType: 'json'})
				.done(function(result){
					alert(result.id + '\n' + result.isbn + '\n' + result.title +'\n' + result.author + '\n' + result.publisher + '\n' + result.type);
					})
				.fail(function(){alert('Error!')})
				.always(function(){alert('Always!')});
		});
	}
	
	testConnection();
	
});