package com.example.bookstore.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.bookstore.domain.Book;
import com.example.bookstore.repository.BookRepository;

@Controller
public class BookController {

  private final BookRepository bookRepository;

  public BookController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @GetMapping("/")
  public String home() {
    return "redirect:/booklist";
  }

  @GetMapping("/booklist")
  public String bookList(Model model) {
    model.addAttribute("books", bookRepository.findAll());
    return "booklist";
  }

  @GetMapping("/add")
  public String addBookForm(Model model) {
    model.addAttribute("book", new Book());
    return "bookform";
  }

  @GetMapping("/edit/{id}")
  public String editBookForm(@PathVariable Long id, Model model) {
    Optional<Book> bookOpt = bookRepository.findById(id);
    if (bookOpt.isEmpty()) {
      return "redirect:/booklist";
    }
    model.addAttribute("book", bookOpt.get());
    return "bookform";
  }

  @PostMapping("/save")
  public String saveBook(@ModelAttribute Book book) {
    bookRepository.save(book);
    return "redirect:/booklist";
  }

  @GetMapping("/delete/{id}")
  public String deleteBook(@PathVariable Long id) {
    bookRepository.deleteById(id);
    return "redirect:/booklist";
  }
}




