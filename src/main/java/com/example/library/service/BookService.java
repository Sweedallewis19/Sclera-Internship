package com.example.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.library.repository.BookRepository;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.UserRepository;
import com.example.library.repository.BookRatingRepository;
import com.example.library.entity.Book;
import com.example.library.entity.Author;
import com.example.library.entity.User;
import com.example.library.entity.BookRating;
import com.example.library.dto.BookRequestDTO;
import com.example.library.mapper.BookMapper;
import com.example.library.exception.BookNotPurchasedException;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final BookRatingRepository bookRatingRepository;
    private final BookMapper bookMapper;

    // CREATE
    public Book saveBook(BookRequestDTO dto, String username) {
        Optional<Book> existingBook = bookRepository.findByIsbnNumber(dto.getIsbnNumber());
        
        Author author = authorRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Author not found. Please provide authorIds or login as an author"));
        
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.getAuthors().add(author);
            return bookRepository.save(book);
        }
        
        Book book = bookMapper.toEntity(dto);
        
        if (dto.getAuthorIds() != null && !dto.getAuthorIds().isEmpty()) {
            Set<Author> authors = authorRepository.findAllById(dto.getAuthorIds()).stream()
                    .collect(java.util.stream.Collectors.toSet());
            authors.add(author);
            book.setAuthors(authors);
        } else {
            book.setAuthors(Set.of(author));
        }
        
        return bookRepository.save(book);
    }

    // READ ALL
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // SEARCH BOOKS
    public List<Book> searchBooks(String searchPattern) {
        return bookRepository.searchBooksByName(searchPattern);
    }

    // READ BY ID
    public Book getBookById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    // UPDATE
    public Book updateBook(UUID id, BookRequestDTO dto, String authorUsername) {
        Book existingBook = getBookById(id);
        
        Author author = authorRepository.findByUsername(authorUsername).orElse(null);
        boolean isAdmin = author != null && "ADMIN".equals(author.getRole());
        boolean isAuthorized = isAdmin || existingBook.getAuthors().stream()
                .anyMatch(a -> a.getUsername().equals(authorUsername));
        
        if (!isAuthorized) {
            throw new RuntimeException("You are not authorized to update this book");
        }
        
        bookMapper.updateFromDTO(dto, existingBook);

        return bookRepository.save(existingBook);
    }

    // DELETE
    public void deleteBook(UUID id, String authorUsername) {
        Book book = getBookById(id);
        
        Author author = authorRepository.findByUsername(authorUsername).orElse(null);
        boolean isAdmin = author != null && "ADMIN".equals(author.getRole());
        boolean isAuthorized = isAdmin || book.getAuthors().stream()
                .anyMatch(a -> a.getUsername().equals(authorUsername));
        
        if (!isAuthorized) {
            throw new RuntimeException("You are not authorized to delete this book");
        }
        
        bookRepository.deleteById(id);
    }

    // PAGINATION
    public Page<Book> getBooksWithPagination(int page,
                                             int size,
                                             String sortBy,
                                             String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return bookRepository.findAll(pageable);
    }

    //FIND BOOKS BY AUTHOR
    public List<Book> getMyBooks(String username) {
        return bookRepository.findBooksByAuthorUsername(username);
    }
    
    //FIND BOOKS PURCHASED BY USER
    public List<Book> getPurchasedBooks(String username) {
        return bookRepository.findPurchasedBooksByUsername(username);
    }

    // RATE BOOK
    public Book rateBook(UUID bookId, Double rating, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = getBookById(bookId);
        
        boolean hasPurchased = book.getPurchasedUsers() != null && 
                book.getPurchasedUsers().stream()
                .anyMatch(u -> u.getUsername().equals(username));

        if (!hasPurchased) {
            throw new BookNotPurchasedException("You can only rate books you have purchased");
        }

        BookRating existingRating = bookRatingRepository.findByBookIdAndUsername(bookId, username)
                .orElse(null);

        if (existingRating != null) {
            existingRating.setRating(rating);
            bookRatingRepository.save(existingRating);
        } else {
            BookRating newRating = new BookRating();
            newRating.setUsername(username);
            newRating.setRating(rating);
            newRating.setBook(book);
            book.getRatings().add(newRating);
            bookRatingRepository.save(newRating);
        }

        updateBookAverageRating(book);
        
        return bookRepository.save(book);
    }

    private void updateBookAverageRating(Book book) {
        if (book.getRatings() == null || book.getRatings().isEmpty()) {
            book.setRating(0.0);
        } else {
            double avg = book.getRatings().stream()
                    .mapToDouble(BookRating::getRating)
                    .average()
                    .orElse(0.0);
            book.setRating(avg);
        }
    }

    // PURCHASE BOOK
    public void purchaseBook(UUID bookId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setPassword("");
                    newUser.setRole("USER");
                    return userRepository.save(newUser);
                });

        Book book = getBookById(bookId);

        if (book.getPurchasedUsers() == null) {
            book.setPurchasedUsers(Set.of(user));
        } else {
            book.getPurchasedUsers().add(user);
        }

        bookRepository.save(book);
    }

    // FIND BY NAME
    public Book getBookByName(String name) {
        return bookRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    // UPDATE BY NAME
    public Book updateBookByName(String name, BookRequestDTO dto, String authorUsername) {
        Book existingBook = getBookByName(name);
        
        Author author = authorRepository.findByUsername(authorUsername).orElse(null);
        boolean isAdmin = author != null && "ADMIN".equals(author.getRole());
        boolean isAuthorized = isAdmin || existingBook.getAuthors().stream()
                .anyMatch(a -> a.getUsername().equals(authorUsername));
        
        if (!isAuthorized) {
            throw new RuntimeException("You are not authorized to update this book");
        }
        
        bookMapper.updateFromDTO(dto, existingBook);

        return bookRepository.save(existingBook);
    }

    // DELETE BY NAME
    public void deleteBookByName(String name, String authorUsername) {
        Book book = getBookByName(name);
        
        Author author = authorRepository.findByUsername(authorUsername).orElse(null);
        boolean isAdmin = author != null && "ADMIN".equals(author.getRole());
        boolean isAuthorized = isAdmin || book.getAuthors().stream()
                .anyMatch(a -> a.getUsername().equals(authorUsername));
        
        if (!isAuthorized) {
            throw new RuntimeException("You are not authorized to delete this book");
        }
        
        bookRepository.deleteByName(name);
    }

    // PURCHASE BY NAME
    public void purchaseBookByName(String bookName, String username) {
        User user = userRepository.findByUsername(username)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setPassword("");
                    newUser.setRole("USER");
                    return userRepository.save(newUser);
                });

        Book book = getBookByName(bookName);

        if (book.getPurchasedUsers() == null) {
            book.setPurchasedUsers(Set.of(user));
        } else {
            book.getPurchasedUsers().add(user);
        }

        bookRepository.save(book);
    }

    // RATE BY NAME
    public Book rateBookByName(String bookName, Double rating, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = getBookByName(bookName);
        
        boolean hasPurchased = book.getPurchasedUsers() != null && 
                book.getPurchasedUsers().stream()
                .anyMatch(u -> u.getUsername().equals(username));

        if (!hasPurchased) {
            throw new BookNotPurchasedException("You can only rate books you have purchased");
        }

        BookRating existingRating = bookRatingRepository.findByBookIdAndUsername(book.getId(), username)
                .orElse(null);

        if (existingRating != null) {
            existingRating.setRating(rating);
            bookRatingRepository.save(existingRating);
        } else {
            BookRating newRating = new BookRating();
            newRating.setUsername(username);
            newRating.setRating(rating);
            newRating.setBook(book);
            book.getRatings().add(newRating);
            bookRatingRepository.save(newRating);
        }

        updateBookAverageRating(book);
        
        return bookRepository.save(book);
    }
}