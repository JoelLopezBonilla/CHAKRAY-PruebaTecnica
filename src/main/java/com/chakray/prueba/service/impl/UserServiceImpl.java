package com.chakray.prueba.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import com.chakray.prueba.model.User;
import com.chakray.prueba.repository.UserRepository;
import com.chakray.prueba.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getSortedByUser(String sortedBy){

        List<User> users = userRepository.findAll();

        if (sortedBy == null || sortedBy.isEmpty()) {
            return users;
        }

        switch(sortedBy){
            case "email":
                return userRepository.findOrderByEmail();
            case "id":
                return userRepository.findOrderById();
            case "name":
                return userRepository.findOrderByName();
            case "phone":
                return userRepository.findOrderByPhone();
            case "tax_id":
                return userRepository.findOrderByTaxId();
            case "created_at":
                return userRepository.findOrderByCreatedAt();
             
        }

        return users;

    }

    @Override
    public List<User> getFilterByUser(String filter){
        List<User> users = userRepository.findAll();

        if (filter != null && !filter.isEmpty()) {
            users = applyFilter(users, filter);
        }
        return users;
    }
    
    private List<User> applyFilter(List<User> users, String filter){
        String[] partes = filter.split(" ");
        if (partes.length != 3) {
            return users;
        }

        String field = partes[0];
        String operador = partes[1];
        String value = partes[2];


        if ("name".equals(field)) {

            /*return users.stream()
                        .filter(user -> user.getName().equals(value))
                        .toList();*/
            
            return users.stream()
                .filter(user -> {
                if ("eq".equals(operador)) {
                    return user.getName().equals(value);
                }
                if ("co".equals(operador)) {
                    return user.getName().contains(value);
                }
                if ("sw".equals(operador)) {
                    return user.getName().startsWith(value);
                }
                if ("ew".equals(operador)) {
                    return user.getName().endsWith(value);
                }
                return false;
                })
                .toList();
            
        }else if ("email".equals(field)) {
            return users.stream()
                .filter(user -> {
                if ("eq".equals(operador)) {
                    return user.getEmail().equals(value);
                }
                if ("co".equals(operador)) {
                    return user.getEmail().contains(value);
                }
                if ("sw".equals(operador)) {
                    return user.getEmail().startsWith(value);
                }
                if ("ew".equals(operador)) {
                    return user.getEmail().endsWith(value);
                }
                return false;
                })
                .toList();
            
        }else if ("phone".equals(field)) {
                        return users.stream()
                .filter(user -> {
                if ("eq".equals(operador)) {
                    return user.getPhone().equals(value);
                }
                if ("co".equals(operador)) {
                    return user.getPhone().contains(value);
                }
                if ("sw".equals(operador)) {
                    return user.getPhone().startsWith(value);
                }
                if ("ew".equals(operador)) {
                    return user.getPhone().endsWith(value);
                }
                return false;
                })
                .toList();
            
        }else if ("tax_id".equals(field)) {
                        return users.stream()
                .filter(user -> {
                if ("eq".equals(operador)) {
                    return user.getTaxId().equals(value);
                }
                if ("co".equals(operador)) {
                    return user.getTaxId().contains(value);
                }
                if ("sw".equals(operador)) {
                    return user.getTaxId().startsWith(value);
                }
                if ("ew".equals(operador)) {
                    return user.getTaxId().endsWith(value);
                }
                return false;
                })
                .toList();
        }
        return users;
    }

}
