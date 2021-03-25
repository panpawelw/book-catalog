import com.panpawelw.bookcatalog.Book;
import com.panpawelw.bookcatalog.DatabaseBookService;
import com.panpawelw.bookcatalog.Misc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DatabaseBookServiceIT {

  public static final Book TEST_BOOK = new Book("test ISBN", "test title",
      "test author", "test publishers", "test type");

  public static final Book GET_BOOK_BY_ID_TEST_BOOK = new Book(3, "9781932394856",
      "Test Driven", "Lance Koskela", "Manning", "programming");

  @Autowired
  private DatabaseBookService service;

  @Before
  public void setup() {
    service.populateDatabase();
  }

  @Test
  public void getBooksTest() {
    assertEquals(service.getBooks(), Misc.getBooksAsMap());
  }

  @Test
  public void addBookTest() {
    service.addBook(TEST_BOOK);
    Map<Long, Book> bookList = service.getBooks();
    TEST_BOOK.setId(Collections.max(bookList.keySet()));
    assertTrue(bookList.containsValue(TEST_BOOK));
  }

  @Test
  public void updateBookTest() {
    service.updateBook(1, TEST_BOOK);
    TEST_BOOK.setId(1);
    assertEquals(service.getBookById(1), TEST_BOOK);
  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void deleteBookTest() {
    service.deleteBook(1);
    service.getBookById(1);
  }

  @Test
  public void getBookByIdTest() {
    assertEquals(service.getBookById(3), GET_BOOK_BY_ID_TEST_BOOK);
  }
}
