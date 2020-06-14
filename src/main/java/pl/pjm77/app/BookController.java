package pl.pjm77.app;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(DatabaseBookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    @GetMapping("/mysqldatabase")
    public void switchToMySQLDatabase() {
        this.bookService = new DatabaseBookService();
    }

    @GetMapping("/memorydatabase")
    public void switchToMemoryDatabase() {
        this.bookService = new MemoryBookService();
    }

    @GetMapping("/")
    @ResponseBody
    public Map<Long, Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{bookId}")
    @ResponseBody
    public Book getBookById(@PathVariable long bookId) {
        return bookService.getBookById(bookId);
    }

    @PostMapping("/add")
    public void addBook(@RequestParam String isbn, String title, String author, String publisher, String type,
                        HttpServletRequest request, HttpServletResponse response) {
        bookService.addBook(new Book(isbn, title, author, publisher, type));
        try {
            response.sendRedirect(request.getContextPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/update/{bookId}")
    @ResponseBody
    public void updateBook(@PathVariable long bookId, @RequestBody(required=true) Book book) {
        bookService.updateBook(book);	}

    @DeleteMapping("/{bookId}")
    @ResponseBody
    public void deleteBook(@PathVariable long bookId) {
        bookService.deleteBook(bookId);
    }
}