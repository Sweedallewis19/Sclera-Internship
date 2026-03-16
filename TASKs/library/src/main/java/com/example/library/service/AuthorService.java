package com.example.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.library.repository.*;
import com.example.library.entity.*;
import com.example.library.dto.AuthorRequestDTO;
import com.example.library.exception.AuthorDeleteException;
import com.example.library.mapper.AuthorMapper;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorMapper authorMapper;

    // CREATE
    public Author saveAuthor(AuthorRequestDTO dto) {
        if (dto.getFirstName() == null || dto.getFirstName().isBlank() ||
            dto.getLastName() == null || dto.getLastName().isBlank() ||
            dto.getCountry() == null || dto.getCountry().isBlank()) {
            throw new IllegalArgumentException("First name, last name, and country are required");
        }
        Author author = authorMapper.toEntity(dto);
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        return authorRepository.save(author);
    }

    // READ ALL
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // READ BY ID
    public Author getAuthorById(UUID id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    // UPDATE
    public Author updateAuthor(UUID id, AuthorRequestDTO dto) {

        Author existing = getAuthorById(id);
        authorMapper.updateFromDTO(dto, existing);

        return authorRepository.save(existing);
    }

    // DELETE WITH EXCEPTION
    public void deleteAuthor(UUID id) {

        boolean assigned = bookRepository.findAll().stream()
                .anyMatch(book ->
                        book.getAuthors().stream()
                                .anyMatch(author -> author.getId().equals(id)));

        if (assigned) {
            throw new AuthorDeleteException(
                    "Cannot delete author assigned to a book");
        }

        authorRepository.deleteById(id);
    }

    // AVG RATING
    public Double getAverageRating() {
        return bookRepository.findAll().stream()
                .mapToDouble(Book::getRating)
                .average()
                .orElse(0.0);
    }

    // AUTHORS WITH AVG PRICE
    public List<Author> getAuthorsWithAvgPrice(String direction) {

        List<Author> authors = authorRepository.findAll();

        for (Author author : authors) {

            double avg = bookRepository.findAll().stream()
                    .filter(b -> b.getAuthors().contains(author))
                    .mapToDouble(Book::getPrice)
                    .average()
                    .orElse(0.0);

            author.setAvgPrice(avg);
        }

        authors.sort((a1, a2) ->
                direction.equalsIgnoreCase("asc") ?
                        a1.getAvgPrice().compareTo(a2.getAvgPrice()) :
                        a2.getAvgPrice().compareTo(a1.getAvgPrice())
        );

        return authors;
    }
}