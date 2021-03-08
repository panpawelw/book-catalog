import com.panpawelw.bookcatalog.Book;
import com.panpawelw.bookcatalog.DatabaseBookService;
import com.panpawelw.bookcatalog.Misc;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class DatabaseBookServiceTests {

    @InjectMocks
    private DatabaseBookService service;

    @Mock
    JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.service = new DatabaseBookService(jdbcTemplate);
    }

    @Test
    public void addBookTest() {
        Book testBook = new Book ("test ISBN", "test title", "test author",
            "test publishers","test type");
        when(jdbcTemplate.update("INSERT INTO books (isbn, title, author, publisher, type) " +
            "VALUES (?, ?, ?, ?, ?)", testBook.getIsbn(), testBook.getTitle(),
            testBook.getAuthor(), testBook.getPublisher(), testBook.getType())).thenReturn(1);
        assertTrue(service.addBook(testBook));
    }

    @Test
    public void updateBookTest() {
        Book testBook = new Book ("test ISBN", "test title", "test author",
            "test publishers","test type");
        when(jdbcTemplate.update("UPDATE books SET isbn=?, title=?, author=?, publisher=?, " +
                "type=? WHERE id=?", testBook.getIsbn(), testBook.getTitle(), testBook.getAuthor(),
            testBook.getPublisher(), testBook.getType(), 1L)).thenReturn(1);
        assertTrue(service.updateBook(1, testBook));
    }

    @Test
    public void deleteBookTest() {
        when(jdbcTemplate.update("DELETE FROM books WHERE id=?", 1L)).thenReturn(1);
        assertTrue(service.deleteBook(1));
    }

    @Test
    public void getBookByIdTest() {
        Book testBook = new Book (1, "test ISBN", "test title", "test author",
            "test publishers","test type");
        when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.<RowMapper<Book>>any(),
            anyLong())).thenReturn(testBook);
        assertEquals(service.getBookById(1), testBook);
    }

    @Test
    public void getBooksTest() {
        Map<Long, Book> expectedDatabase = new HashMap<>();
        for(Book book : Misc.BOOK_LIST) {
            expectedDatabase.put(book.getId(), book);
        }

        //Create a list of rows to be returned from jdbcTemplate.queryForList
        List<Map<String, Object>> tempList = new ArrayList<>();
        for(Map.Entry<Long, Book> entry : expectedDatabase.entrySet()) {
            Map<String, Object> tempMap = new HashMap<>();
            Book tempBook = entry.getValue();
            tempMap.put("id", tempBook.getId());
            tempMap.put("isbn", tempBook.getIsbn());
            tempMap.put("title", tempBook.getTitle());
            tempMap.put("author", tempBook.getAuthor());
            tempMap.put("publisher", tempBook.getPublisher());
            tempMap.put("type", tempBook.getType());
            tempList.add(tempMap);
        }

        when(jdbcTemplate.queryForList("SELECT * FROM books")).thenReturn(tempList);
        assertEquals(service.getBooks(), expectedDatabase);
    }
}
