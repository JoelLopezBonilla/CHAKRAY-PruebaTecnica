package com.chakray.prueba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chakray.prueba.model.User;
import com.chakray.prueba.repository.UserRepository;
import com.chakray.prueba.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/sortedBy")
    public List<User> getSortedByUser(@RequestParam String sortedBy) {
        return userService.getSortedByUser(sortedBy);
    }

    @GetMapping("/filter")
    public List<User> getFilterByUser(@RequestParam String filter) {
        return userService.getFilterByUser(filter);
    }
    
    
}
