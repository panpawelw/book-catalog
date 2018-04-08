package pl.pjm77.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class MemoryBookService implements BookService{

	private Map<Long, Book> list;
	private long nextId;
	
	public MemoryBookService() {
	
		list = new HashMap<>();
		long idCounter = getNextFreeId();
		list.put(idCounter, new Book(idCounter, "9788324631766", "Thinking in Java.", "Bruce Eckel",
				"Helion", "programming"));
		idCounter = getNextFreeId();
		list.put(idCounter, new Book(idCounter, "9788324627738", "Rusz glowa, Java.",
				"Sierra Kathy, Bates Bert", "Helion", "programming"));
		idCounter = getNextFreeId();
		list.put(idCounter, new Book(idCounter, "9780130819338", "Java 2. Podstawy.",
				"Cay Horstmann, Gary Cornell", "Helion", "programming"));
	}

	private long getNextFreeId() {
		nextId++;
		return nextId;
	}

	@Override
	public void addBook(String idbn, String title, String Author, String publisher, String type) {
		
	}

	@Override
	public void updateBook(Book book) {
		if(book!=null) {
			
		}
	}

	@Override
	public void deleteBook(long id) {
		if(id>0 && list.get(id)!=null) {
			list.remove(id);
		}
	}

	@Override
	public Book getById(long id) {
		return list.get(id);
	}

	@Override
	public Map<Long, Book> getBooks() {
		return list;
	}
}