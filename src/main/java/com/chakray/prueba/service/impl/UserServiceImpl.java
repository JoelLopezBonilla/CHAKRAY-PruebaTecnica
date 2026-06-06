package com.chakray.prueba.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chakray.prueba.dto.UserDTO;
import com.chakray.prueba.exception.ResponseException;
import com.chakray.prueba.model.User;
import com.chakray.prueba.repository.UserRepository;
import com.chakray.prueba.service.EncryptionService;
import com.chakray.prueba.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncryptionService encryptionService;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getSortedByUser(String sortedBy) {

        List<User> users = userRepository.findAll();

        if (sortedBy == null || sortedBy.isEmpty()) {
            return users;
        }

        switch (sortedBy) {
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
    public List<User> getFilterByUser(String filter) {
        List<User> users = userRepository.findAll();

        if (filter != null && !filter.isEmpty()) {
            users = applyFilter(users, filter);
        }
        return users;
    }

    @Override
    public UserDTO createUser(User user) {
        UserDTO userDTO = new UserDTO();

        String dateTimeMadagascar = LocalDateTime.now(ZoneId.of("Indian/Antananarivo"))
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

        user.setId(UUID.randomUUID());
        user.setCreatedAt(dateTimeMadagascar);
        user.setPassword(encryptionService.encrypt(user.getPassword()));

        if (userRepository.findByTaxId(user.getTaxId()).isPresent()) {
            throw new ResponseException("RFC ya registrado");
        }

        boolean correctRFC = validateRFC(user.getTaxId());
        boolean correctPhone = validatePhone(user.getPhone());

        if (correctRFC && correctPhone) {
            userRepository.save(user);
        } else if (!correctPhone && !correctRFC) {
            throw new ResponseException("RFC y Telefono invalido usuario no creado");
        } else if (!correctRFC) {
            throw new ResponseException("RFC invalido usuariod no creado");
        } else {
            throw new ResponseException("Telefono invalido usuario no creado");
        }

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPhone(user.getPhone());
        userDTO.setTaxId(user.getTaxId());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setAddresses(user.getAddresses());
        return userDTO;

    }

    @Override
    public UserDTO updateUser(UUID id, User user) {
        User buscarUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        UserDTO userDTO = new UserDTO();

        String newPassword = encryptionService.encrypt(user.getPassword());

        if (newPassword != buscarUser.getPassword()) {
            buscarUser.setPassword(newPassword);
        }

        if (user.getName() != buscarUser.getName()) {
            buscarUser.setName(user.getName());
        }
        if (user.getEmail() != buscarUser.getEmail()) {
            buscarUser.setEmail(user.getEmail());
        }
        if (user.getPhone() != buscarUser.getPhone()) {
            buscarUser.setPhone(user.getPhone());
        }
        if (user.getAddresses() != buscarUser.getAddresses()) {
            buscarUser.setAddresses(user.getAddresses());
        }

        userDTO.setId(buscarUser.getId());
        userDTO.setEmail(buscarUser.getEmail());
        userDTO.setName(buscarUser.getName());
        userDTO.setPhone(buscarUser.getPhone());
        userDTO.setTaxId(buscarUser.getTaxId());
        userDTO.setCreatedAt(buscarUser.getCreatedAt());
        userDTO.setAddresses(buscarUser.getAddresses());

        return userDTO;
    }

    @Override
    public void deleteUser(UUID id) {
        User buscarUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        userRepository.delete(buscarUser);
    }

    private List<User> applyFilter(List<User> users, String filter) {
        String[] partes = filter.split(" ");
        if (partes.length != 3) {
            return users;
        }

        String field = partes[0];
        String operador = partes[1];
        String value = partes[2];

        if ("name".equals(field)) {
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

        } else if ("email".equals(field)) {
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

        } else if ("phone".equals(field)) {
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

        } else if ("tax_id".equals(field)) {
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

    private boolean validateRFC(String taxId) {

        if (taxId == null || taxId.length() != 13) {
            return false;
        }

        char[] chars = taxId.toCharArray();

        for (int i = 0; i < 4; i++) {
            if (!Character.isLetter(chars[i])) {
                return false;
            }
        }

        for (int i = 4; i < 10; i++) {
            if (!Character.isDigit(chars[i])) {
                return false;
            }
        }

        for (int i = 10; i < 13; i++) {
            if (!Character.isLetterOrDigit(chars[i])) {
                return false;
            }
        }

        return true;
    }

    private boolean validatePhone(String phone) {

        String cleanPhone = phone.replace("+", "");
        boolean validate = false;

        if (!cleanPhone.matches("\\d+")) {
            return false;
        }
        if (cleanPhone.length() >= 10 && cleanPhone.length() <= 13) {
            validate = true;
        }
        return validate;

    }

}
