package com.example.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.library.service.BookService;
import com.example.library.dto.BookRequestDTO;
import com.example.library.dto.BookResponseDTO;
import com.example.library.mapper.BookMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    // CREATE BOOK - ADMIN or AUTHOR only
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR')")
    public BookResponseDTO createBook(@RequestBody BookRequestDTO dto, Authentication authentication) {
        String username = authentication.getName();
        return bookMapper.toDTO(bookService.saveBook(dto, username));
    }

    // GET ALL BOOKS - All authenticated users
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'AUTHOR')")
    public List<BookResponseDTO> getAllBooks(
            @RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return bookMapper.toDTOList(bookService.searchBooks(search));
        }
        return bookMapper.toDTOList(bookService.getAllBooks());
    }

    // GET BOOK BY ID - All authenticated users
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'AUTHOR')")
    public BookResponseDTO getBookById(@PathVariable UUID id) {
        return bookMapper.toDTO(bookService.getBookById(id));
    }

    // UPDATE BOOK - ADMIN or AUTHOR (owner or ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR')")
    public BookResponseDTO updateBook(@PathVariable UUID id,
                           @RequestBody BookRequestDTO dto,
                           Authentication authentication) {
        String username = authentication.getName();
        return bookMapper.toDTO(bookService.updateBook(id, dto, username));
    }

    // DELETE BOOK - ADMIN only
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@PathVariable UUID id, Authentication authentication) {
        String username = authentication.getName();
        bookService.deleteBook(id, username);
        return "Book deleted successfully";
    }

    // PAGINATION + SORTING - All authenticated users
    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'AUTHOR')")
    public Page<BookResponseDTO> getBooksPage(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String direction) {

        return bookService.getBooksWithPagination(page, size, sortBy, direction)
                .map(bookMapper::toDTO);
    }
    //GET BOOKS BY AUTHORS - All authenticated users
    @GetMapping("/my-books")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'AUTHOR')")
    public List<BookResponseDTO> getMyBooks(Authentication authentication) {

        String username = authentication.getName();

        return bookMapper.toDTOList(bookService.getMyBooks(username));
    }
    
    //GET PURCHASED BOOKS - All authenticated users
    @GetMapping("/purchased")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<BookResponseDTO> getPurchasedBooks(Authentication authentication) {

        String username = authentication.getName();

        return bookMapper.toDTOList(bookService.getPurchasedBooks(username));
    }

    // RATE BOOK - Only users who purchased the book
    @PostMapping("/{id}/rate")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public BookResponseDTO rateBook(@PathVariable UUID id,
                                     @RequestParam Double rating,
                                     Authentication authentication) {
        String username = authentication.getName();
        return bookMapper.toDTO(bookService.rateBook(id, rating, username));
    }

    // PURCHASE BOOK - Add book to user's purchased list
    @PostMapping("/{id}/purchase")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String purchaseBook(@PathVariable UUID id, Authentication authentication) {
        String username = authentication.getName();
        bookService.purchaseBook(id, username);
        return "Book purchased successfully";
    }

    // UPDATE BOOK BY NAME - ADMIN or AUTHOR (owner or ADMIN)
    @PutMapping("/by-name/{name}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR')")
    public BookResponseDTO updateBookByName(@PathVariable String name,
                           @RequestBody BookRequestDTO dto,
                           Authentication authentication) {
        String username = authentication.getName();
        return bookMapper.toDTO(bookService.updateBookByName(name, dto, username));
    }

    // DELETE BOOK BY NAME - ADMIN only
    @DeleteMapping("/by-name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBookByName(@PathVariable String name, Authentication authentication) {
        String username = authentication.getName();
        bookService.deleteBookByName(name, username);
        return "Book deleted successfully";
    }

    // PURCHASE BOOK BY NAME
    @PostMapping("/by-name/{name}/purchase")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String purchaseBookByName(@PathVariable String name, Authentication authentication) {
        String username = authentication.getName();
        bookService.purchaseBookByName(name, username);
        return "Book purchased successfully";
    }

    // RATE BOOK BY NAME
    @PostMapping("/by-name/{name}/rate")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public BookResponseDTO rateBookByName(@PathVariable String name,
                                          @RequestParam Double rating,
                                          Authentication authentication) {
        String username = authentication.getName();
        return bookMapper.toDTO(bookService.rateBookByName(name, rating, username));
    }
}