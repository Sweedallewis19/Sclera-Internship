package com.example.library.dto;

import lombok.Data;
import java.util.Set;
import java.util.UUID;

@Data
public class BookRequestDTO {
    private String isbnNumber;
    private String name;
    private String category;
    private double rating;
    private double price;
    private Set<UUID> authorIds;
}
