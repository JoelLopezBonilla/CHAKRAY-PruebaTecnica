package com.chakray.prueba.model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String email;
    private String name;
    private String phone;
    private String password;
    private String taxId;
    private String createdAt;
    private List<Address> addresses;
    
}
