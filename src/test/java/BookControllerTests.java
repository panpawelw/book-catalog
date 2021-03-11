import com.panpawelw.bookcatalog.Book;
import com.panpawelw.bookcatalog.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.panpawelw.bookcatalog.Misc.getBooksAsMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTests {

  public static final Book TEST_BOOK = new Book("test ISBN", "test title",
      "test author", "test publishers", "test type");

  @Mock
  private BookService service;

  @Test
  public void getBooksTest(){
    when(service.getBooks()).thenReturn(getBooksAsMap());
    assertEquals(service.getBooks(), getBooksAsMap());
  }

  @Test
  public void getBookByIdTest(){
    when(service.getBookById(anyLong())).thenReturn(TEST_BOOK);
    assertEquals(service.getBookById(3), TEST_BOOK);
  }

  @Test
  public void addBookTest(){
    when(service.addBook(any())).thenReturn(true);
    assertTrue(service.addBook(TEST_BOOK));
  }

  @Test
  public void updateBookTest(){
    when(service.updateBook(anyLong(), any())).thenReturn(true);
    assertTrue(service.updateBook(1, TEST_BOOK));
  }

  @Test
  public void deleteBookTest(){
    when(service.deleteBook(anyLong())).thenReturn(true);
    assertTrue(service.deleteBook(1));
  }
}
