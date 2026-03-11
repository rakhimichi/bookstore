package com.example.bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Category;

@DataJpaTest
@ActiveProfiles("test")
class BookRepositoryTest {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @BeforeEach
  void setUp() {
    bookRepository.deleteAll();
    categoryRepository.deleteAll();

    Category category = categoryRepository.save(new Category("IT"));

    bookRepository.save(new Book(
        "Clean Code",
        "Robert C. Martin",
        2008,
        "9780132350884",
        39.90,
        category));

    bookRepository.save(new Book(
        "Spring in Action",
        "Craig Walls",
        2022,
        "9781617297571",
        44.90,
        category));
  }

  @Test
  void findByTitleContainingIgnoreCaseShouldReturnMatchingBook() {
    List<Book> books = bookRepository.findByTitleContainingIgnoreCase("clean");

    assertThat(books).hasSize(1);
    assertThat(books.get(0).getAuthor()).isEqualTo("Robert C. Martin");
  }

  @Test
  void createNewBookShouldSetId() {
    Category category = categoryRepository.save(new Category("Testing"));

    Book book = new Book(
        "JUnit in Action",
        "Petar Tahchiev",
        2010,
        "9781935182023",
        34.90,
        category);

    bookRepository.save(book);

    assertThat(book.getId()).isNotNull();
  }

  @Test
  void deleteBookShouldRemoveItFromDatabase() {
    Book book = bookRepository.findByTitleContainingIgnoreCase("clean").get(0);

    bookRepository.delete(book);

    List<Book> books = bookRepository.findByTitleContainingIgnoreCase("clean");
    assertThat(books).isEmpty();
  }
}