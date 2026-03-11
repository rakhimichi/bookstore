package com.example.bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.bookstore.domain.AppUser;

@DataJpaTest
@ActiveProfiles("test")
class AppUserRepositoryTest {

  @Autowired
  private AppUserRepository appUserRepository;

  @BeforeEach
  void setUp() {
    appUserRepository.deleteAll();
    appUserRepository.save(new AppUser(
        "user",
        "passwordhash",
        "user@example.com",
        "USER"));
  }

  @Test
  void findByUsernameShouldReturnUser() {
    Optional<AppUser> user = appUserRepository.findByUsername("user");

    assertThat(user).isPresent();
    assertThat(user.get().getEmail()).isEqualTo("user@example.com");
  }

  @Test
  void createNewUserShouldSetId() {
    AppUser user = new AppUser(
        "admin",
        "passwordhash2",
        "admin@example.com",
        "ADMIN");

    appUserRepository.save(user);

    assertThat(user.getId()).isNotNull();
  }

  @Test
  void deleteUserShouldRemoveItFromDatabase() {
    AppUser user = appUserRepository.findByUsername("user").get();

    appUserRepository.delete(user);

    Optional<AppUser> deletedUser = appUserRepository.findByUsername("user");
    assertThat(deletedUser).isEmpty();
  }
}
