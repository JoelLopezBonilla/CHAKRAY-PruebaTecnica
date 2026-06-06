package com.chakray.prueba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chakray.prueba.dto.Request.LoginRequest;
import com.chakray.prueba.dto.Response.LoginResponse;
import com.chakray.prueba.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(loginService.login(loginRequest));
    }    
}
