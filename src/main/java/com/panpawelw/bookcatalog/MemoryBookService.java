package com.panpawelw.bookcatalog;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import static com.panpawelw.bookcatalog.Misc.getBooksAsMap;

@Service
public class MemoryBookService implements BookService {

  private final Map<Long, Book> bookList;
  private long nextId;

  public MemoryBookService() {
    bookList = new HashMap<>();
    populateDatabase();
  }

  public void populateDatabase() {
    getBooksAsMap(bookList);
    nextId = bookList.size();
  }

  private long getNextFreeId() {
    return ++nextId;
  }

  @Override
  public boolean addBook(Book book) {
    long idCounter = getNextFreeId();
    book.setId(idCounter);
    return bookList.put(idCounter, book) == null;
  }

  @Override
  public boolean updateBook(long id, Book book) {
    if (book == null || id <= 0) return false;
    return bookList.replace(id, book) != null;
  }

  @Override
  public boolean deleteBook(long id) {
    if (id <= 0 || bookList.get(id) == null) return false;
    return bookList.remove(id) != null;
  }

  @Override
  public Book getBookById(long id) {
    return bookList.get(id);
  }

  @Override
  public Map<Long, Book> getBooks() {
    return bookList;
  }
}