package com.example.library.controller;

import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET BY USERNAME
    @GetMapping("/by-username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    // UPDATE BY USERNAME
    @PutMapping("/by-username/{username}")
    public User updateUser(@PathVariable String username,
                           @RequestParam(required = false) String password,
                           @RequestParam(required = false) String role) {
        return userService.updateUser(username, password, role);
    }

    // DELETE BY USERNAME
    @DeleteMapping("/by-username/{username}")
    public String deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return "User deleted successfully";
    }
}
