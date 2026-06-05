package com.chakray.prueba.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.chakray.prueba.model.User;

@Service
public interface UserService {

    List<User> getAllUser();
    List<User> getSortedByUser(String sortedBy);
    List<User> getFilterByUser(String filter);
    
}
