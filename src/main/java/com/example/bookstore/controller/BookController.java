package com.example.bookstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.bookstore.domain.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;

@Controller
public class BookController {

  private final BookRepository bookRepository;
  private final CategoryRepository categoryRepository;

  public BookController(BookRepository bookRepository, CategoryRepository categoryRepository) {
    this.bookRepository = bookRepository;
    this.categoryRepository = categoryRepository;
  }

  // ---------- MVC (HTML pages) ----------

  @RequestMapping(value = { "/", "/booklist" })
  public String bookList(Model model, Authentication authentication) {
    model.addAttribute("books", bookRepository.findAll());

    // default values (in practice, this page requires authentication)
    model.addAttribute("username", "");
    model.addAttribute("isAdmin", false);

    // Show username and "isAdmin" flag on the page
    if (authentication != null) {
      model.addAttribute("username", authentication.getName());
      boolean isAdmin = authentication.getAuthorities().stream()
          .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
      model.addAttribute("isAdmin", isAdmin);
    }

    return "booklist";
  }

  // Custom login page
  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/add")
  public String addBook(Model model) {
    model.addAttribute("book", new Book());
    model.addAttribute("categories", categoryRepository.findAll());
    return "bookform";
  }

  @GetMapping("/edit/{id}")
  public String editBook(@PathVariable Long id, Model model) {
    Book book = bookRepository.findById(id).orElse(null);
    if (book == null) {
      return "redirect:/booklist";
    }
    model.addAttribute("book", book);
    model.addAttribute("categories", categoryRepository.findAll());
    return "bookform";
  }

  @PostMapping("/save")
  public String save(Book book) {
    bookRepository.save(book);
    return "redirect:/booklist";
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable Long id) {
    bookRepository.deleteById(id);
    return "redirect:/booklist";
  }

  // ---------- REST (JSON) ----------

  @GetMapping("/books")
  public @ResponseBody Iterable<Book> bookListRest() {
    return bookRepository.findAll();
  }

  @GetMapping("/book/{id}")
  public ResponseEntity<Book> findBookRest(@PathVariable Long id) {
    return bookRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}






