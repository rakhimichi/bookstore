package com.example.bookstore.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.bookstore.domain.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
  Optional<AppUser> findByUsername(String username);
}
