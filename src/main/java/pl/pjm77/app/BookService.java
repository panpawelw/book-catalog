package pl.pjm77.app;

import java.util.Map;

public interface BookService {

	void addBook(String idbn, String title, String Author, String publisher, String type);
	
	void updateBook(Book book);
	
	void deleteBook(long id);
	
	Book getById(long id);
	
	Map<Long, Book> getBooks();
}