package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.library.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, UUID> {
    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.username = :username")
    List<Book> findBooksByAuthorUsername(@Param("username") String username);
    
    @Query("SELECT b FROM Book b JOIN b.purchasedUsers u WHERE u.username = :username")
    List<Book> findPurchasedBooksByUsername(@Param("username") String username);
}