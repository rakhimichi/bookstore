package com.example.bookstore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.bookstore.domain.Book;

@RepositoryRestResource
public interface BookRepository extends CrudRepository<Book, Long> {

  List<Book> findByTitleContainingIgnoreCase(@Param("title") String title);
}

