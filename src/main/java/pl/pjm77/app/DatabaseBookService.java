package pl.pjm77.app;

import javax.sql.DataSource;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseBookService implements BookService {

    private DataSource datasource;
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

    @Override
    public void addBook(String idbn, String title, String Author, String publisher, String type) {

    }

    @Override
    public void updateBook(Book book) {

    }

    @Override
    public void deleteBook(long id) {

    }

    @Override
    public Book getById(long id) {
        return null;
    }

    @Override
    public Map<Long, Book> getBooks() {
        return null;
    }
}