package pl.pjm77.app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

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