package pl.pjm77.app;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseBookService implements BookService {

    private DataSource datasource;
    private JdbcTemplate jdbcTemplate;

    public DatabaseBookService() {
        if (datasource == null) {
            try {
                datasource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc" +
                  "/book_catalog");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        jdbcTemplate = new JdbcTemplate(datasource);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS books (id BIGINT, isbn VARCHAR(255), " +
          "title VARCHAR(255), author VARCHAR(255), publisher VARCHAR(255), type VARCHAR(255));");
    }

    @Override
    public long addBook(Book book) {
        return jdbcTemplate.update("INSERT INTO books (isbn, title, author, publisher, type) " +
          "VALUES (?, ?, ?, ?, ?)", book.getIsbn(), book.getTitle(), book.getAuthor(),
          book.getPublisher(), book.getType());
    }

    @Override
    public boolean updateBook(Book book) {
        return jdbcTemplate.update("UPDATE book SET isbn=?, title=?, author=?, publisher=?, " +
          "type=?", book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublisher(),
          book.getType()) == 1;
    }

    @Override
    public boolean deleteBook(long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        return jdbcTemplate.update(sql, new Object[] {id}) == 1;
    }

    @Override
    public Book getBookById(long id) {
        return null;
    }

    @Override
    public Map<Long, Book> getBooks() {
        return null;
    }
}