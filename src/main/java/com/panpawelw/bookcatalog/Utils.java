package com.panpawelw.bookcatalog;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class that provides a list of books to serve both as an example and for testing purposes.
 * It also contains several methods helping with populating the database.
 *
 * @author panpawelw
 */
public class Utils {

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

  /**
   * Copies the content of the book array to a new map.
   *
   * @return  map containing books.
   */
  public static Map<Long, Book> getBooksAsMap() {
    Map<Long, Book> booksAsMap = new HashMap<>();
    return copyBookListToMap(booksAsMap);
  }

  /**
   * Copies the content of the book array when the map is given as a parameter.
   * The map is emptied before copying.
   *
   * @param booksAsMap  map to be emptied and to copy books into.
   * @return            map containing books.
   */
  public static Map<Long, Book> getBooksAsMap(Map<Long, Book> booksAsMap) {
    booksAsMap.clear();
    return copyBookListToMap(booksAsMap);
  }

  /**
   * Copies the content of Book array to a map.
   *
   * @param booksAsMap  map to copy books into.
   * @return            map containing books.
   */
  public static Map<Long, Book> copyBookListToMap(Map<Long, Book> booksAsMap) {
    for (Book book : BOOK_LIST) {
      booksAsMap.put(book.getId(), book);
    }
    return booksAsMap;
  }
}
