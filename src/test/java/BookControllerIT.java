import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panpawelw.bookcatalog.*;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@RunWith(Parameterized.class)
public class BookControllerIT {

  public static final Book TEST_BOOK = new Book("test ISBN", "test title",
      "test author", "test publishers", "test type");

  public Map<Long, Book> BOOK_LIST = new HashMap<>();

  @ClassRule
  public static final SpringClassRule scr = new SpringClassRule();

  @Rule
  public final SpringMethodRule smr = new SpringMethodRule();

  @Autowired
  private WebApplicationContext wac;

  MockMvc mockMvc;

  String chooseService;

  @Autowired
  private BookController controller;

  public BookControllerIT(String chooseService) {
    this.chooseService = chooseService;
  }

  @Parameterized.Parameters
  public static Collection<String> databaseType() {
    return Arrays.asList("/mysqldatabase", "/memorydatabase");
  }

  @Before
  public void setup() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    this.BOOK_LIST = Misc.getBooksAsMap();
    mockMvc.perform(get(chooseService)).andExpect(status().isOk());
    mockMvc.perform(get("/resetdatabase")).andExpect(status().isOk());
  }

  @Test
  public void getBooksTest() throws Exception {
    MvcResult result = mockMvc.perform(get("/getallbooks")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();
    ObjectMapper mapper = new ObjectMapper();
    HashMap<Long, Book> bookList = mapper.readValue(result.getResponse().getContentAsString(),
        new TypeReference<HashMap<Long, Book>>() {});
    assertEquals(bookList, BOOK_LIST);
  }

  @Test
  public void addBookTest() throws Exception {
    mockMvc.perform(post("/book").secure(true)
        .param("isbn",TEST_BOOK.getIsbn())
        .param("title", TEST_BOOK.getTitle())
        .param("author", TEST_BOOK.getAuthor())
        .param("publisher", TEST_BOOK.getPublisher())
        .param("type", TEST_BOOK.getType()))
        .andExpect(redirectedUrl(""));
    Map<Long, Book> bookList = controller.getBookService().getBooks();
    TEST_BOOK.setId(Collections.max(bookList.keySet()));
    assertTrue(bookList.containsValue(TEST_BOOK));
  }

  @Test
  public void addBookInvalidJsonTest() throws Exception {
    mockMvc.perform(post("/book").secure(true)
        .param("grrt", "whatever")
        .param("blob", "1")
        .param("wat?", "uuk")
        .param("ouch", TEST_BOOK.getAuthor()))
        .andExpect(redirectedUrl(null));
  }

  @Test
  public void updateBookTest() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    TEST_BOOK.setId(1);
    String bookJson = mapper.writeValueAsString(TEST_BOOK);
    mockMvc.perform(put("/book/1").contentType(MediaType.APPLICATION_JSON)
        .content(bookJson).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    assertTrue(controller.getBookService().getBooks().containsValue(TEST_BOOK));
  }

  @Test
  public void updateBookInvalidParameterTest() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    TEST_BOOK.setId(1);
    String bookJson = mapper.writeValueAsString(TEST_BOOK);
    mockMvc.perform(put("/book/x").contentType(MediaType.APPLICATION_JSON)
        .content(bookJson).accept(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
  }

  @Test
  public void updateBookInvalidJsonTest() throws Exception {
    String bookJson = "whatever";
    mockMvc.perform(put("/book/1").contentType(MediaType.APPLICATION_JSON)
        .content(bookJson).accept(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
  }

  @Test
  public void deleteBookTest() throws Exception {
    mockMvc.perform(delete("/book/1")).andExpect(status().isOk());
    Map<Long, Book> bookList = controller.getBookService().getBooks();
    assertFalse(bookList.containsKey(1L));
  }

  @Test
  public void deleteBookInvalidParameterTest() throws Exception {
    mockMvc.perform(delete("/book/h7")).andExpect(status().is(400));
  }

  @Test
  public void switchToMySQLDatabaseTest() throws Exception {
    mockMvc.perform(get("/mysqldatabase")).andExpect(status().isOk());
    assertTrue(controller.getBookService() instanceof DatabaseBookService);
  }

  @Test
  public void switchToMemoryDatabaseTest() throws Exception {
    mockMvc.perform(get("/memorydatabase")).andExpect(status().isOk());
    assertTrue(controller.getBookService() instanceof MemoryBookService);
  }

  @Test
  public void resetDatabaseTest() throws Exception {
    ConcurrentHashMap<Long, Book> bookList =
        new ConcurrentHashMap<>(controller.getBookService().getBooks());
    for (Map.Entry<Long, Book> entry : bookList.entrySet()) {
      mockMvc.perform(delete("/book/" + entry.getKey())).andExpect(status().isOk());
    }
    mockMvc.perform(get("/resetdatabase")).andExpect(status().isOk());
    assertEquals(controller.getBookService().getBooks(), BOOK_LIST);
  }
}
