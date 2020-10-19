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
        long idCounter = getNextFreeId();
        list.put(idCounter, new Book(idCounter, "9788324631766", "Core Java Volume I",
                "Cay S. Horstmann " ,"Prentice Hall", "programming"));
        idCounter = getNextFreeId();
        list.put(idCounter, new Book(idCounter, "9780596007126", "Head First. Design Patterns",
                "Eric Freeman, Bert Bates, Kathy Sierra, Elisabeth Robson", "O'Reilly",
                "programming"));
        idCounter = getNextFreeId();
        list.put(idCounter, new Book(idCounter, "9781932394856", "Test Driven", "Lance Koskela",
                "Manning", "programming"));
        idCounter = getNextFreeId();
        list.put(idCounter, new Book(idCounter, "9780132350884", "Clean Code", "Robert C. Martin",
                "Prentice Hall", "programming"));
        idCounter = getNextFreeId();
        list.put(idCounter, new Book(idCounter, "9780134685991", "Effective Java", "Joshua Bloch",
                "Addison - Wesley Professional", "programming"));
        idCounter = getNextFreeId();
        list.put(idCounter, new Book(idCounter, "9780134684452",
                "Domain-Driven Design: Tackling Complexity in the Heart of Software", "Eric Evans",
                "Addison - Wesley Professional", "programming"));
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