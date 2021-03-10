import com.panpawelw.bookcatalog.Book;
import com.panpawelw.bookcatalog.MemoryBookService;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.panpawelw.bookcatalog.Misc.BOOK_LIST;
import static org.junit.Assert.*;

public class MemoryBookServiceTests {

  public static final Book TEST_BOOK = new Book("test ISBN", "test title",
      "test author", "test publishers", "test type");

  public static final Book GET_BOOK_BY_ID_TEST_BOOK = new Book(3, "9781932394856",
      "Test Driven", "Lance Koskela", "Manning", "programming");

  private MemoryBookService service;

  @Before
  public void setup() {
    service = new MemoryBookService();
  }

  @Test
  public void addBookTest() {
    assertTrue(service.addBook(TEST_BOOK));
    Map<Long, Book> books = service.getBooks();
    assertEquals(TEST_BOOK, service.getBooks().get((long) books.size()));
  }

  @Test
  public void updateBookTest() {
    assertTrue(service.updateBook(1, TEST_BOOK));
    assertEquals(service.getBookById(1), TEST_BOOK);
  }

  @Test
  public void deleteBookTest() {
    assertTrue(service.deleteBook(1));
    assertNull(service.getBookById(1));
  }

  @Test
  public void getBookByIdTest() {
    assertEquals(service.getBookById(3), GET_BOOK_BY_ID_TEST_BOOK);
  }

  @Test
  public void getBooksTest() {
    Map<Long, Book> expectedDatabase = new HashMap<>();
    for (Book book : BOOK_LIST) {
      expectedDatabase.put(book.getId(), book);
    }
    assertEquals(service.getBooks(), expectedDatabase);
  }
}
