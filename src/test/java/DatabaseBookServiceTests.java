import com.panpawelw.bookcatalog.Book;
import com.panpawelw.bookcatalog.DatabaseBookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.panpawelw.bookcatalog.Utils.getBooksAsMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseBookServiceTests {

  public static final Book TEST_BOOK = new Book("test ISBN", "test title",
      "test author", "test publishers", "test type");

  @Mock
  JdbcTemplate jdbcTemplate;

  @InjectMocks
  private DatabaseBookService service;

  @Before
  public void setup() {
    this.service = new DatabaseBookService(jdbcTemplate);
  }

  @Test
  public void addBookTest() {
    when(jdbcTemplate.update(
        "INSERT INTO books (isbn, title, author, publisher, type) VALUES (?, ?, ?, ?, ?)",
        TEST_BOOK.getIsbn(), TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(),
        TEST_BOOK.getPublisher(), TEST_BOOK.getType())).thenReturn(1);
    assertTrue(service.addBook(TEST_BOOK));
    verify(jdbcTemplate, times(1)).update(
        "INSERT INTO books (isbn, title, author, publisher, type) VALUES (?, ?, ?, ?, ?)",
        TEST_BOOK.getIsbn(), TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(),
        TEST_BOOK.getPublisher(), TEST_BOOK.getType());
  }

  @Test
  public void updateBookTest() {
    when(jdbcTemplate.update(
        "UPDATE books SET isbn=?, title=?, author=?, publisher=?, type=? WHERE id=?",
        TEST_BOOK.getIsbn(), TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(),
        TEST_BOOK.getPublisher(), TEST_BOOK.getType(), 1L)).thenReturn(1);
    assertTrue(service.updateBook(1, TEST_BOOK));
    verify(jdbcTemplate, times(1)).update(
        "UPDATE books SET isbn=?, title=?, author=?, publisher=?, type=? WHERE id=?",
        TEST_BOOK.getIsbn(), TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(),
        TEST_BOOK.getPublisher(), TEST_BOOK.getType(), 1L);
  }

  @Test
  public void deleteBookTest() {
    when(jdbcTemplate.update("DELETE FROM books WHERE id=?", 1L)).thenReturn(1);
    assertTrue(service.deleteBook(1));
    verify(jdbcTemplate, times(1)).update(
        "DELETE FROM books WHERE id=?", 1L);
  }

  @Test
  public void getBookByIdTest() {
    when(jdbcTemplate.queryForObject(eq("SELECT * FROM books WHERE id=?"),
        ArgumentMatchers.<RowMapper<Book>>any(),eq(1L))).thenReturn(TEST_BOOK);
    assertEquals(TEST_BOOK, service.getBookById(1));
    verify(jdbcTemplate, times(1)).queryForObject(eq(
        "SELECT * FROM books WHERE id=?"), ArgumentMatchers.<RowMapper<Book>>any(),eq(1L));
  }

  @Test
  public void getBooksTest() {
    Map<Long, Book> expectedResult = getBooksAsMap();
    when(jdbcTemplate.queryForList("SELECT * FROM books"))
        .thenReturn(jdbcTemplateQueryResult(expectedResult));
    assertEquals(service.getBooks(), expectedResult);
    verify(jdbcTemplate, times(1)).queryForList("SELECT * FROM books");
  }

  private List<Map<String, Object>> jdbcTemplateQueryResult(Map<Long, Book> bookMap) {
    List<Map<String, Object>> result = new ArrayList<>();
    for (Map.Entry<Long, Book> entry : bookMap.entrySet()) {
      Map<String, Object> tempMap = new HashMap<>();
      Book tempBook = entry.getValue();
      tempMap.put("id", tempBook.getId());
      tempMap.put("isbn", tempBook.getIsbn());
      tempMap.put("title", tempBook.getTitle());
      tempMap.put("author", tempBook.getAuthor());
      tempMap.put("publisher", tempBook.getPublisher());
      tempMap.put("type", tempBook.getType());
      result.add(tempMap);
    }
    return result;
  }
}
