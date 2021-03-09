import com.panpawelw.bookcatalog.Book;
import com.panpawelw.bookcatalog.MemoryBookService;
import com.panpawelw.bookcatalog.Misc;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.panpawelw.bookcatalog.Misc.BOOK_LIST;
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
        "test publishers", "test type");
    assertTrue(service.addBook(testBook));
    Map<Long, Book> books = service.getBooks();
    assertEquals(testBook, service.getBooks().get((long) books.size()));
  }

  @Test
  public void updateBookTest() {
    Book testBook = new Book("test ISBN", "test title", "test author",
        "test publishers", "test type");
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
        "Core Java Volume I", "Cay S. Horstmann",
        "Prentice Hall", "programming"));
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
