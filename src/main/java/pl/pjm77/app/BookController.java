package pl.pjm77.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping("/")
	@ResponseBody
	public List<Book >getBooks() {
		return memoryBookService.getList();
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
}