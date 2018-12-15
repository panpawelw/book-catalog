package pl.pjm77.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class MemoryBookService implements BookService {

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
		idCounter = getNextFreeId();
		list.put(idCounter, new Book(idCounter, "9781932394856", "Test Driven.", "Lance Kosskella",
				"Manning", "programming"));
		idCounter = getNextFreeId();
		list.put(idCounter, new Book(idCounter, "9780134685991", "Effective Java.", "Joshua Bloch",
				"Addison - Wesley Professional", "programming"));
		idCounter = getNextFreeId();
		list.put(idCounter, new Book(idCounter, "9780132350884", "Clean Code.", "Robert C. Martin",
				"Prentice Hall", "programming"));
		idCounter = getNextFreeId();
		list.put(idCounter, new Book(idCounter, "9780596007126", "Head First. Design Patterns",
				"Eric Freeman, Bert Bates. Kathy Sierra, Elisabeth Robson", "O'Reilly",
				"programming"));
	}

	private long getNextFreeId() {
		nextId++;
		return nextId;
	}

	@Override
	public void addBook(String isbn, String title, String author, String publisher, String type) {
		long idCounter = getNextFreeId();
		list.put(idCounter, new Book(idCounter, isbn, title, author, publisher, type));
	}

	@Override
	public void updateBook(Book book) {
		if (book != null && book.getId() != 0) {
			list.replace(book.getId(), book);
		}
	}

	@Override
	public void deleteBook(long id) {
		if (id > 0 && list.get(id) != null) {
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