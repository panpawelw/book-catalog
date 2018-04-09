package pl.pjm77.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin
@RestController
@RequestMapping("/books")
public class BookController {

	public MemoryBookService memoryBookService;
	
	@Autowired
	public BookController(MemoryBookService memoryBookService) {
		this.memoryBookService = memoryBookService;
	}
	
	@GetMapping("/")
	@ResponseBody
	public Map<Long, Book>getBooks() {
		return memoryBookService.getBooks();
	}
	@GetMapping("/{bookId}")
	@ResponseBody
	public Book getBookById(@PathVariable long bookId) {
		return memoryBookService.getById(bookId);
	}
	
	@PostMapping("/add")
	public String addBook(@RequestParam String isbn, String title, String author, String publisher, String type) {
		memoryBookService.addBook(isbn, title, author, publisher, type);
		return "";
	}
	
	@DeleteMapping("/{bookId}")
	@ResponseBody
	public void deleteBook(@PathVariable long bookId) {
		System.out.println(bookId);
		memoryBookService.deleteBook(bookId);
		System.out.println(memoryBookService.getBooks());
	}
	
	@GetMapping("/edit")
	public void editBook() {
		System.out.println("edit!");
	}
}