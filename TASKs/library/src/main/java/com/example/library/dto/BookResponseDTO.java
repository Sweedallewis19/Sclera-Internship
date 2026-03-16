package com.example.library.dto;

import lombok.Data;
import java.util.Set;
import java.util.List;
import java.util.UUID;

@Data
public class BookResponseDTO {
    private UUID id;
    private String isbnNumber;
    private String name;
    private String category;
    private double rating;
    private double price;
    private Set<AuthorResponseDTO> authors;
    private List<BookRatingDTO> ratings;
}
