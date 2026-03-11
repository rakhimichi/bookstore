package com.example.bookstore.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Category;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookControllerRestTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  private Long bookId;

  @BeforeEach
  void setUp() {
    bookRepository.deleteAll();
    categoryRepository.deleteAll();

    Category testingCategory = categoryRepository.save(new Category("Testing"));

    Book book = bookRepository.save(new Book(
        "Test Driven Development",
        "Kent Beck",
        2003,
        "9780321146533",
        29.90,
        testingCategory));

    bookId = book.getId();
  }

  @Test
  void booksEndpointWithoutLoginShouldRedirectToLogin() throws Exception {
    mockMvc.perform(get("/books"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/login"));
  }

  @Test
  @WithMockUser(username = "user", roles = { "USER" })
  void booksEndpointShouldReturnJsonList() throws Exception {
    mockMvc.perform(get("/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].title", is("Test Driven Development")))
        .andExpect(jsonPath("$[0].author", is("Kent Beck")));
  }

  @Test
  @WithMockUser(username = "user", roles = { "USER" })
  void bookEndpointShouldReturnOneBookAsJson() throws Exception {
    mockMvc.perform(get("/book/{id}", bookId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is("Test Driven Development")))
        .andExpect(jsonPath("$.author", is("Kent Beck")));
  }

  @Test
  @WithMockUser(username = "user", roles = { "USER" })
  void bookEndpointShouldReturn404WhenBookIsMissing() throws Exception {
    mockMvc.perform(get("/book/{id}", 999999L))
        .andExpect(status().isNotFound());
  }
}
