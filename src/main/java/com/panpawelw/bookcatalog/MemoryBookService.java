package com.panpawelw.bookcatalog;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class MemoryBookService implements BookService {

    private final Map<Long, Book> list;
    private long nextId;

    public MemoryBookService() {
        list = new HashMap<>();
        populateDatabase();
    }

    public void populateDatabase() {
        nextId = 0;
        list.clear();
        for(Book book : Misc.BOOK_LIST) {
            list.put(getNextFreeId(), book);
        }
    }

    private long getNextFreeId() {
        return ++nextId;
    }

    @Override
    public boolean addBook(Book book) {
        long idCounter = getNextFreeId();
        book.setId(idCounter);
        return list.put(idCounter, book) == null;
    }

    @Override
    public boolean updateBook(long id, Book book) {
        if (book == null || id <= 0) return false;
        return list.replace(id, book) != null;
    }

    @Override
    public boolean deleteBook(long id) {
        if (id <= 0 || list.get(id) == null) return false;
        return list.remove(id) != null;
    }

    @Override
    public Book getBookById(long id) {
        return list.get(id);
    }

    @Override
    public Map<Long, Book> getBooks() {
        return list;
    }
}