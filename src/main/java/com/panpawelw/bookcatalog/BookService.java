package com.panpawelw.bookcatalog;

import java.util.Map;

public interface BookService {

    boolean addBook(Book book);

    boolean updateBook(long id, Book book);

    boolean deleteBook(long id);

    Book getBookById(long id);

    Map<Long, Book> getBooks();
}