import com.panpawelw.bookcatalog.BookController;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;

@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SmokeTest {

  @Autowired
  private BookController controller;

  @Test
  public void smokeTest() {
    assertNotNull(controller);
  }
}
