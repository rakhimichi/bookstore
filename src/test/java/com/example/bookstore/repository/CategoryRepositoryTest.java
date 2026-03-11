package com.example.bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.bookstore.domain.Category;

@DataJpaTest
@ActiveProfiles("test")
class CategoryRepositoryTest {

  @Autowired
  private CategoryRepository categoryRepository;

  @BeforeEach
  void setUp() {
    categoryRepository.deleteAll();

    categoryRepository.save(new Category("IT"));
    categoryRepository.save(new Category("History"));
  }

  @Test
  void findByNameShouldReturnMatchingCategory() {
    List<Category> categories = categoryRepository.findByName("IT");

    assertThat(categories).hasSize(1);
    assertThat(categories.get(0).getName()).isEqualTo("IT");
  }

  @Test
  void createNewCategoryShouldSetId() {
    Category category = new Category("Testing");

    categoryRepository.save(category);

    assertThat(category.getId()).isNotNull();
  }

  @Test
  void deleteCategoryShouldRemoveItFromDatabase() {
    Category category = categoryRepository.findByName("History").get(0);

    categoryRepository.delete(category);

    List<Category> categories = categoryRepository.findByName("History");
    assertThat(categories).isEmpty();
  }
}
