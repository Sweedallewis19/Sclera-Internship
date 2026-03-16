package com.example.library.controller;

import com.example.library.entity.User;
import com.example.library.entity.Author;
import com.example.library.service.UserService;
import com.example.library.repository.UserRepository;
import com.example.library.repository.AuthorRepository;
import com.example.library.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Map<String,String> request){

        String username = request.get("username");
        String password = request.get("password");

        String role = null;
        String encodedPassword = null;

        if (userRepository.findByUsername(username).isPresent()) {
            User user = userRepository.findByUsername(username).get();
            role = user.getRole();
            encodedPassword = user.getPassword();
        } else if (authorRepository.findByUsername(username).isPresent()) {
            Author author = authorRepository.findByUsername(username).get();
            role = author.getRole();
            encodedPassword = author.getPassword();
        } else {
            throw new RuntimeException("User not found");
        }

        if(!passwordEncoder.matches(password, encodedPassword))
            throw new RuntimeException("Invalid credentials");

        String token = jwtUtil.generateToken(username, role);

        return Map.of("token",token);
    }

    @PostMapping("/register")
    public Map<String,String> register(@RequestBody Map<String,String> request){
        String username = request.get("username");
        String password = request.get("password");
        String role = request.getOrDefault("role", "USER").toUpperCase();

        userService.saveUser(username, password, role);

        return Map.of("message","User registered successfully with role: " + role);
    }
}
