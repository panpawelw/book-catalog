import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panpawelw.bookcatalog.Book;
import com.panpawelw.bookcatalog.BookController;
import com.panpawelw.bookcatalog.Misc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BookControllerIT {

  MockMvc mockMvc;

  @Autowired
  private BookController controller;

  @Before
  public void setup() {
    this.mockMvc = standaloneSetup(this.controller).build();
  }

  @Test
  public void getBooksTest() throws Exception {
    mockMvc.perform(get("/resetdatabase")).andExpect(status().isOk());
    MvcResult result = mockMvc.perform(get("/getallbooks")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();
    ObjectMapper mapper = new ObjectMapper();
    HashMap<Long, Book> bookList = mapper.readValue(result.getResponse().getContentAsString(),
        new TypeReference<HashMap<Long, Book>>() {});
    assertEquals(bookList, Misc.getBooksAsMap());
  }

  @Test
  public void addBookTest() throws Exception {

  }

  @Test
  public void updateBookTest() throws Exception {

  }

  @Test
  public void deleteBookTest() throws Exception {

  }

  @Test
  public void switchToMySQLDatabaseTest() throws Exception {

  }

  @Test
  public void switchToMemoryDatabaseTest() throws Exception {

  }

  @Test
  public void resetDatabaseTest() throws Exception {

  }
}
