package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.library.entity.BookRating;
import java.util.Optional;
import java.util.UUID;

public interface BookRatingRepository extends JpaRepository<BookRating, UUID> {
    Optional<BookRating> findByBookIdAndUsername(UUID bookId, String username);
}
