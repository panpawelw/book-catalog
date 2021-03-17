import com.panpawelw.bookcatalog.BookController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

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
    mockMvc.perform(get("/getallbooks").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
