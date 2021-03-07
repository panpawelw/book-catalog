package com.panpawelw.bookcatalog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseBookService implements BookService {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseBookService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        populateDatabase();
    }

    public void populateDatabase() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS books;");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS books (id BIGINT AUTO_INCREMENT NOT " +
                "NULL, isbn VARCHAR(255), title VARCHAR(255), author VARCHAR(255), publisher VARCHAR" +
                "(255), type VARCHAR(255), PRIMARY KEY(id));");

        for(Book book : Misc.BOOK_LIST) {
            this.addBook(book);
        }
    }

    @Override
    public boolean addBook(Book book) {
        return jdbcTemplate.update("INSERT INTO books (isbn, title, author, publisher, type) " +
            "VALUES (?, ?, ?, ?, ?)", book.getIsbn(), book.getTitle(), book.getAuthor(),
          book.getPublisher(), book.getType()) == 1;
    }

    @Override
    public boolean updateBook(long id, Book book) {
        return jdbcTemplate.update("UPDATE books SET isbn=?, title=?, author=?, publisher=?, " +
            "type=? WHERE id=?", book.getIsbn(), book.getTitle(), book.getAuthor(),
          book.getPublisher(), book.getType(), id) == 1;
    }

    @Override
    public boolean deleteBook(long id) {
        return jdbcTemplate.update("DELETE FROM books WHERE id=?", id) == 1;
    }

    @Override
    public Book getBookById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM books WHERE id=?", (rs, rowNum) ->
            new Book(
              rs.getLong("id"),
              rs.getString("isbn"),
              rs.getString("title"),
              rs.getString("author"),
              rs.getString("publisher"),
              rs.getString("type")
            ), id);
    }

    @Override
    public Map<Long, Book> getBooks() {
        Map<Long, Book> list = new HashMap<>();
        List<Map<String, Object>> tempList = jdbcTemplate.queryForList("SELECT * FROM books");
        for (Map<String, Object> map : tempList) {
            list.put((long) map.get("id"),
              new Book(
                (long) map.get("id"),
                (String) map.get("isbn"),
                (String) map.get("title"),
                (String) map.get("author"),
                (String) map.get("publisher"),
                (String) map.get("type")));
        }
        return list;
    }
}