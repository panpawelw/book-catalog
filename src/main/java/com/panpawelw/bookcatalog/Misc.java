package com.panpawelw.bookcatalog;

import java.util.HashMap;
import java.util.Map;

public class Misc {

  public static final Book[] BOOK_LIST = {
      new Book(1, "9788324631766", "Core Java Volume I", "Cay S. Horstmann",
          "Prentice Hall", "programming"),
      new Book(2, "9780596007126", "Head First. Design Patterns",
          "Eric Freeman, Bert Bates, Kathy Sierra, Elisabeth Robson", "O'Reilly",
          "programming"),
      new Book(3, "9781932394856", "Test Driven", "Lance Koskela",
          "Manning", "programming"),
      new Book(4, "9780132350884", "Clean Code", "Robert C. Martin",
          "Prentice Hall", "programming"),
      new Book(5, "9780134685991", "Effective Java", "Joshua Bloch",
          "Addison - Wesley Professional", "programming"),
      new Book(6, "9780134684452",
          "Domain-Driven Design: Tackling Complexity in the Heart of Software",
          "Eric Evans", "Addison - Wesley Professional", "programming")
  };

  public static Map<Long, Book> getBooksAsMap() {
    Map<Long, Book> booksAsMap = new HashMap<>();
    for (Book book : BOOK_LIST) {
      booksAsMap.put(book.getId(), book);
    }
    return booksAsMap;
  }

}
