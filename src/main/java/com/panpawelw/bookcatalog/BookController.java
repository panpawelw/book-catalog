package com.panpawelw.bookcatalog;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("/")
public class BookController {

  private BookService bookService;
  private final ApplicationContext context;

  @Autowired
  public BookController(MemoryBookService bookService, ApplicationContext context) {
    this.bookService = bookService;
    this.context = context;
  }

  public BookService getBookService() {
    return this.bookService;
  }

  @GetMapping("/getallbooks")
  @ResponseBody
  public Map<Long, Book> getBooks() {
    return bookService.getBooks();
  }

  @GetMapping("/book/{bookId}")
  @ResponseBody
  public Book getBookById(@PathVariable long bookId) {
    return bookService.getBookById(bookId);
  }

  @PostMapping("/book")
  public void addBook(@RequestParam String isbn, String title, String author, String publisher,
                      String type, HttpServletRequest request, HttpServletResponse response) {
    if (!bookService.addBook(new Book(isbn, title, author, publisher, type))) {
      System.out.println("Error adding book!");
    }
    try {
      response.sendRedirect(request.getContextPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @PutMapping("/book/{bookId}")
  @ResponseBody
  public void updateBook(@PathVariable long bookId, @RequestBody() Book book) {
    if (!bookService.updateBook(bookId, book)) {
      System.out.println("Error updating book!");
    }
  }

  @DeleteMapping("/book/{bookId}")
  @ResponseBody
  public void deleteBook(@PathVariable long bookId) {
    if (!bookService.deleteBook(bookId)) {
      System.out.println("Error deleting book!");
    }
  }

  @GetMapping("/mysqldatabase")
  public void switchToMySQLDatabase() {
    this.bookService = context.getBean(DatabaseBookService.class);
  }

  @GetMapping("/memorydatabase")
  public void switchToMemoryDatabase() {
    this.bookService = context.getBean(MemoryBookService.class);
  }

  @GetMapping("/resetdatabase")
  public void resetDatabase() {
    bookService.populateDatabase();
  }
}