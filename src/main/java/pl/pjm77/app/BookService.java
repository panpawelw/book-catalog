package pl.pjm77.app;

import java.util.Map;

public interface BookService {

    long addBook(Book book);

    boolean updateBook(Book book);

    boolean deleteBook(long id);

    Book getBookById(long id);

    Map<Long, Book> getBooks();
}