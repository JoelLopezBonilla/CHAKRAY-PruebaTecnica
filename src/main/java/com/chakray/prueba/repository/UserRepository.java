package com.chakray.prueba.repository;

import com.chakray.prueba.model.Address;
import com.chakray.prueba.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    @PostConstruct
    public void init() {

        users.add(
                User.builder()
                        .id(UUID.randomUUID())
                        .email("user1@mail.com")
                        .name("user1")
                        .phone("+525555555551")
                        .password("password1")
                        .taxId("AARR990101XXX")
                        .createdAt("01-01-2026 00:00")
                        .addresses(List.of(
                                Address.builder()
                                        .id(1L)
                                        .name("workaddress")
                                        .street("street No. 1")
                                        .countryCode("UK")
                                        .build()
                        ))
                        .build()
        );

        users.add(
                User.builder()
                        .id(UUID.randomUUID())
                        .email("user2@mail.com")
                        .name("user2")
                        .phone("+525555555552")
                        .password("password2")
                        .taxId("AARR990102XXX")
                        .createdAt("01-01-2026 00:00")
                        .addresses(new ArrayList<>())
                        .build()
        );

        users.add(
                User.builder()
                        .id(UUID.randomUUID())
                        .email("user3@mail.com")
                        .name("user3")
                        .phone("+525555555553")
                        .password("password3")
                        .taxId("AARR990103XXX")
                        .createdAt("01-01-2026 00:00")
                        .addresses(new ArrayList<>())
                        .build()
        );
    }

    public List<User> findAll() {
        return users;
    }

    public List<User> findOrderByEmail(){
        return users.stream()
                .sorted(Comparator.comparing(User::getEmail))
                .toList();
    }
    public List<User> findOrderById(){
        return users.stream()
                .sorted(Comparator.comparing(User::getId))
                .toList();
    }
    public List<User> findOrderByName(){
        return users.stream()
                .sorted(Comparator.comparing(User::getName))
                .toList();
    }

    public List<User> findOrderByPhone(){
        return users.stream()
                .sorted(Comparator.comparing(User::getPhone))
                .toList();
    }

    public List<User> findOrderByTaxId(){
        return users.stream()
                .sorted(Comparator.comparing(User::getTaxId))
                .toList();
    }
    
    public List<User> findOrderByCreatedAt(){
        return users.stream()
                .sorted(Comparator.comparing(User::getCreatedAt))
                .toList();
    }

    public Optional<User> findById(UUID id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public Optional<User> findByTaxId(String taxId) {
        return users.stream()
                .filter(user -> user.getTaxId().equalsIgnoreCase(taxId))
                .findFirst();
    }

    public void save(User user) {
        users.add(user);
    }

    public void delete(User user) {
        users.remove(user);
    }
}