package com.example.bookstore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.bookstore.domain.Category;

@RepositoryRestResource
public interface CategoryRepository extends CrudRepository<Category, Long> {

  List<Category> findByName(String name);
}



