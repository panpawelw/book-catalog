$(document).ready(function(){
	
	function testConnection() {
		$('.testButton').on('click', function(event){
			var books;
			$.ajax({
				url: 'http://localhost:8080/Workshop_4and5/books/',
				data: {books: books},
				type: 'GET',
				dataType: 'json'})
				.done(function(result){
					alert(result[0].id + '\n' + result[0].isbn + '\n' + result[0].title +'\n' + result[0].author + '\n' + result[0].publisher + '\n' + result[0].type +
							'\n' + result[1].id + '\n' + result[2].id);
					})
				.fail(function(){alert('Error!')})
				.always(function(){alert('Always!')});
		});
	}
	
	testConnection();
	
});