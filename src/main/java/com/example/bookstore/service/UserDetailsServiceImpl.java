package com.example.bookstore.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bookstore.domain.AppUser;
import com.example.bookstore.repository.AppUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final AppUserRepository appUserRepository;

  public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
    this.appUserRepository = appUserRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser user = appUserRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    return User.withUsername(user.getUsername())
        .password(user.getPassword())     // это BCrypt hash
        .roles(user.getRole())            // "USER" / "ADMIN"
        .build();
  }
}
