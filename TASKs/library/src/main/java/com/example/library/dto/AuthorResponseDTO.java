package com.example.library.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class AuthorResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String country;
    private String username;
    private String role;
}
