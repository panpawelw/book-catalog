import com.panpawelw.bookcatalog.Book;
import com.panpawelw.bookcatalog.MemoryBookService;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class MemoryBookServiceTests {

    private MemoryBookService service;

    @Before
    public void setup() {
        service = new MemoryBookService();
    }

    @Test
    public void populateDatabaseTest() {
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

    @Test
    public void addBookTest() {}

    @Test
    public void updateBookTest() {}

    @Test
    public void deleteBookTest() {}

    @Test
    public void getBookByIdTest() {}

    @Test
    public void getBooksTest() {}
}
