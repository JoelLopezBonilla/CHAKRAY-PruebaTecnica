package com.chakray.prueba.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chakray.prueba.dto.Request.LoginRequest;
import com.chakray.prueba.dto.Response.LoginResponse;
import com.chakray.prueba.exception.ResponseException;
import com.chakray.prueba.model.User;
import com.chakray.prueba.repository.UserRepository;
import com.chakray.prueba.service.EncryptionService;
import com.chakray.prueba.service.LoginService;

@Service(value = "loginService")
public class LoginServiceImpl implements LoginService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncryptionService encryptionService;

    @Override
    public LoginResponse login(LoginRequest loginRequest){

        User user = userRepository.findByTaxId(loginRequest.getTaxId())
                                                            .orElseThrow(()-> new ResponseException("RFC no encontrado"));

        String loginPassword = encryptionService.encrypt(loginRequest.getPassword());

        if (!user.getPassword().equals(loginPassword)) {
            throw new ResponseException("Contraseña incorrecta");
        }

        return new LoginResponse("Credenciales correctas");
    }
    
}
