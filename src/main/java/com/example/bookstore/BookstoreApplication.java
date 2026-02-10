package com.example.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Category;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;

@SpringBootApplication
public class BookStoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookStoreApplication.class, args);
  }

  @Bean
  @Profile("!test")
  public CommandLineRunner demo(BookRepository bookRepository, CategoryRepository categoryRepository) {
    return args -> {
      Category it = categoryRepository.save(new Category("IT"));
      Category fiction = categoryRepository.save(new Category("Fiction"));
      Category history = categoryRepository.save(new Category("History"));

      bookRepository.save(new Book("Clean Code", "Robert C. Martin", 2008, "9780132350884", 39.90, it));
      bookRepository.save(new Book("Effective Java", "Joshua Bloch", 2018, "9780134685991", 45.00, it));
      bookRepository.save(new Book("1984", "George Orwell", 1949, "9780451524935", 12.50, fiction));
      bookRepository.save(new Book("Sapiens", "Yuval Noah Harari", 2011, "9780062316097", 18.00, history));
    };
  }
}

