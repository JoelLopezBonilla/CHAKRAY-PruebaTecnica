package com.chakray.prueba.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.chakray.prueba.dto.UserDTO;
import com.chakray.prueba.model.User;

@Service
public interface UserService {

    List<User> getAllUser();

    List<User> getSortedByUser(String sortedBy);

    List<User> getFilterByUser(String filter);

    UserDTO createUser(User user);

    UserDTO updateUser(UUID id, User user);

    void deleteUser(UUID id);
}
