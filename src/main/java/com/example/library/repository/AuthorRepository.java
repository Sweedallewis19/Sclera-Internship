package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.library.entity.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    @Query("SELECT AVG(b.rating) FROM Book b")
    Double getAverageRatingOfAllBooks();

    Optional<Author> findByUsername(String username);

    void deleteByUsername(String username);
}