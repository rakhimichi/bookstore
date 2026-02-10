package com.example.bookstore.web;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.bookstore.domain.Category;
import com.example.bookstore.repository.CategoryRepository;

@Component
public class CategoryConverter implements Converter<String, Category> {

  private final CategoryRepository categoryRepository;

  public CategoryConverter(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Category convert(String source) {
    if (source == null || source.isBlank()) return null;
    return categoryRepository.findById(Long.valueOf(source)).orElse(null);
  }
}


