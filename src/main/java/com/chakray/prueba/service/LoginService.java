package com.chakray.prueba.service;

import com.chakray.prueba.dto.Request.LoginRequest;
import com.chakray.prueba.dto.Response.LoginResponse;

public interface LoginService {

    LoginResponse login(LoginRequest loginRequest);
    
}
