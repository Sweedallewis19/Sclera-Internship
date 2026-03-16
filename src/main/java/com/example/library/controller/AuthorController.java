package com.example.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.library.service.AuthorService;
import com.example.library.dto.AuthorRequestDTO;
import com.example.library.dto.AuthorResponseDTO;
import com.example.library.mapper.AuthorMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    // CREATE
    @PostMapping
    public AuthorResponseDTO createAuthor(@RequestBody AuthorRequestDTO dto) {
        return authorMapper.toDTO(authorService.saveAuthor(dto));
    }

    // READ ALL
    @GetMapping
    public List<AuthorResponseDTO> getAllAuthors() {
        return authorMapper.toDTOList(authorService.getAllAuthors());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public AuthorResponseDTO getAuthorById(@PathVariable UUID id) {
        return authorMapper.toDTO(authorService.getAuthorById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public AuthorResponseDTO updateAuthor(@PathVariable UUID id,
                               @RequestBody AuthorRequestDTO dto) {
        return authorMapper.toDTO(authorService.updateAuthor(id, dto));
    }

    // DELETE (with exception handling)
    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable UUID id) {
        authorService.deleteAuthor(id);
        return "Author deleted successfully";
    }

    // DELETE BY USERNAME
    @DeleteMapping("/by-username/{username}")
    public String deleteAuthorByUsername(@PathVariable String username) {
        authorService.deleteAuthorByUsername(username);
        return "Author deleted successfully";
    }

    // UPDATE BY USERNAME
    @PutMapping("/by-username/{username}")
    public AuthorResponseDTO updateAuthorByUsername(@PathVariable String username,
                                      @RequestBody AuthorRequestDTO dto) {
        return authorMapper.toDTO(authorService.updateAuthorByUsername(username, dto));
    }

    // AVG RATING
    @GetMapping("/avg-rating")
    public Double getAvgRating() {
        return authorService.getAverageRating();
    }

    // AUTHORS WITH AVG PRICE
    @GetMapping("/avg-price")
    public List<AuthorResponseDTO> getAuthorsWithAvgPrice(
            @RequestParam(defaultValue = "asc") String direction) {

        return authorMapper.toDTOList(authorService.getAuthorsWithAvgPrice(direction));
    }
}