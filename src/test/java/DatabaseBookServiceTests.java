import com.panpawelw.bookcatalog.Book;
import com.panpawelw.bookcatalog.DatabaseBookService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Map<Long, Book> expectedDatabase = Stream.of(new Object[][] {
            {1L, new Book(1, "9788324631766", "Core Java Volume I",
                "Cay S. Horstmann " ,"Prentice Hall", "programming")},
            {2L, new Book(2, "9780596007126", "Head First. Design Patterns",
                "Eric Freeman, Bert Bates, Kathy Sierra, Elisabeth Robson", "O'Reilly",
                "programming")},
            {3L, new Book(3, "9781932394856", "Test Driven", "Lance Koskela",
                "Manning", "programming")},
            {4L, new Book(4, "9780132350884", "Clean Code", "Robert C. Martin",
                "Prentice Hall", "programming")},
            {5L, new Book(5, "9780134685991", "Effective Java", "Joshua Bloch",
                "Addison - Wesley Professional", "programming")},
            {6L, new Book(6, "9780134684452",
                "Domain-Driven Design: Tackling Complexity in the Heart of Software", "Eric Evans",
                "Addison - Wesley Professional", "programming")},
        }).collect(Collectors.toMap(data -> (Long) data[0], data -> (Book) data[1]));

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
