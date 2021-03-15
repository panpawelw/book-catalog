import com.panpawelw.bookcatalog.Book;
import com.panpawelw.bookcatalog.DatabaseBookService;
import com.panpawelw.bookcatalog.Misc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DatabaseBookServiceIT {

  public static final Book TEST_BOOK = new Book("test ISBN", "test title",
      "test author", "test publishers", "test type");

  @Autowired
  private DatabaseBookService service;

  @Test
  public void getBooksTest() {
    service.populateDatabase();
    assertEquals(service.getBooks(), Misc.getBooksAsMap());
  }

  @Test
  public void addBookTest() {
    service.addBook(TEST_BOOK);
    Map<Long, Book> bookList = service.getBooks();
    long id = Collections.max(bookList.keySet());
    TEST_BOOK.setId(id);
    assertTrue(bookList.containsValue(TEST_BOOK));
  }
}
