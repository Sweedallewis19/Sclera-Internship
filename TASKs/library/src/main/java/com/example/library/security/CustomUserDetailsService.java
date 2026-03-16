package com.example.library.security;

import com.example.library.entity.Author;
import com.example.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Author author = authorRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Author not found"));

        return new User(
                author.getUsername(),
                author.getPassword(),//for logging in
                List.of(new SimpleGrantedAuthority(author.getRole()))
        );
    }
}
