import com.panpawelw.bookcatalog.Book;
import com.panpawelw.bookcatalog.BookController;
import com.panpawelw.bookcatalog.DatabaseBookService;
import com.panpawelw.bookcatalog.MemoryBookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.panpawelw.bookcatalog.Misc.getBooksAsMap;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTests {

  public static final Book TEST_BOOK = new Book("test ISBN", "test title",
      "test author", "test publishers", "test type");

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Mock
  private JdbcTemplate jdbcTemplate;

  @Mock
  private ApplicationContext context;

  @Mock
  private MemoryBookService service;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  private BookController bookController;

  @Before
  public void setup() {
    System.setOut(new PrintStream(outContent));
    bookController = new BookController(service, context);
  }

  @After
  public void cleanUp() {
    System.setOut(originalOut);
  }

  @Test
  public void getBooksTest() {
    when(service.getBooks()).thenReturn(getBooksAsMap());
    assertEquals(bookController.getBooks(), getBooksAsMap());
  }

  @Test
  public void getBookByIdTest() {
    when(service.getBookById(3)).thenReturn(TEST_BOOK);
    assertEquals(bookController.getBookById(3), TEST_BOOK);
  }

  @Test
  public void addBookWithCorrectParameterTest() {
    when(service.addBook(any())).thenReturn(true);
    bookController.addBook(TEST_BOOK.getIsbn(), TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(),
        TEST_BOOK.getPublisher(), TEST_BOOK.getType(), request, response);
    assertEquals("", outContent.toString());
  }

  @Test
  public void addBookWithIncorrectParameterTest() {
    when(service.addBook(any())).thenReturn(false);
    bookController.addBook(TEST_BOOK.getIsbn(), TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(),
        TEST_BOOK.getPublisher(), TEST_BOOK.getType(), request, response);
    assertEquals("Error adding book!\r\n", outContent.toString());
  }

  @Test
  public void updateBookWithCorrectParameterTest() {
    when(service.updateBook(1, TEST_BOOK)).thenReturn(true);
    service.updateBook(1, TEST_BOOK);
    assertEquals("", outContent.toString());
  }

  @Test
  public void updateBookWithIncorrectParameterTest() {
    when(service.updateBook(1, TEST_BOOK)).thenReturn(false);
    bookController.updateBook(1, TEST_BOOK);
    assertEquals("Error updating book!\r\n", outContent.toString());
  }

  @Test
  public void deleteBookWithCorrectParameterTest() {
    when(service.deleteBook(1)).thenReturn(true);
    bookController.deleteBook(1);
    assertEquals("", outContent.toString());
  }

  @Test
  public void deleteBookWithIncorrectParameterTest() {
    when(service.deleteBook(1)).thenReturn(false);
    bookController.deleteBook(1);
    assertEquals("Error deleting book!\r\n", outContent.toString());
  }

  @Test
  public void switchToMySQLDatabaseTest() {
    DatabaseBookService databaseBookService = new DatabaseBookService(jdbcTemplate);
    when(context.getBean(DatabaseBookService.class))
        .thenReturn(databaseBookService);
    bookController.switchToMySQLDatabase();
    assertEquals(bookController.getBookService(), databaseBookService);
  }

  @Test
  public void switchToMemoryDatabaseTest() {
    MemoryBookService memoryBookService = new MemoryBookService();
    when(context.getBean(MemoryBookService.class))
        .thenReturn(memoryBookService);
    bookController.switchToMemoryDatabase();
    assertEquals(bookController.getBookService(), memoryBookService);
  }

  @Test
  public void populateDatabaseTest() {
    service.populateDatabase();
    verify(service, times(1)).populateDatabase();
  }
}
