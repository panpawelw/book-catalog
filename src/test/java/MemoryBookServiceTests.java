import com.panpawelw.bookcatalog.Book;
import com.panpawelw.bookcatalog.MemoryBookService;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class MemoryBookServiceTests {

    private MemoryBookService service;

    @Before
    public void setup() {
        service = new MemoryBookService();
    }

    @Test
    public void addBookTest() {
        Book testBook = new Book("test ISBN", "test title", "test author",
                "test publishers","test type");
        assertTrue(service.addBook(testBook));
        Map<Long, Book> books = service.getBooks();
        assertEquals(testBook, service.getBooks().get((long) books.size()));
    }

    @Test
    public void updateBookTest() {
        Book testBook = new Book("test ISBN", "test title", "test author",
                "test publishers","test type");
        assertTrue(service.updateBook(1, testBook));
        assertEquals(service.getBookById(1), testBook);
    }

    @Test
    public void deleteBookTest() {
        assertTrue(service.deleteBook(1));
        assertNull(service.getBookById(1));
    }

    @Test
    public void getBookByIdTest() {
        assertEquals(service.getBookById(1), new Book(1, "9788324631766",
                "Core Java Volume I", "Cay S. Horstmann " ,
                "Prentice Hall", "programming"));
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

        assertEquals(service.getBooks(), expectedDatabase);
    }
}
