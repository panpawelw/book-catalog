package pl.pjm77.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping("/hello")
	public String hello() {
		return "{hello:world}";
	}
	
	@RequestMapping("/helloBook")
	public Book helloBook() {
		return new Book(1L,"9788324631766","Thinking in Java",
				"Bruce Eckel","Helion","programming");
	}
	
	@GetMapping("/delete")
	public void deleteBook() {
		System.out.println("delete!");
	}
	
	@GetMapping("/edit")
	public void editBook() {
		System.out.println("edit!");
	}
}