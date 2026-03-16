package com.example.library.dto;

import lombok.Data;

@Data
public class AuthorRequestDTO {
    private String firstName;
    private String lastName;
    private String country;
    private String username;
    private String password;
    private String role;
}
