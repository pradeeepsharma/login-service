package com.learning.microservices.loginservice.controller;

import com.learning.microservices.loginservice.exception.CustomerNotFoundException;
import com.learning.microservices.loginservice.model.LoginRequest;
import com.learning.microservices.loginservice.model.LoginResponse;
import com.learning.microservices.loginservice.model.PasswordRequest;
import com.learning.microservices.loginservice.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {
    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login/password", produces = "application/json")
    public ResponseEntity<Boolean> createLogin(@Valid @RequestBody PasswordRequest request)throws CustomerNotFoundException {
        return new ResponseEntity<>(loginService.createLogin(request), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login/login", produces = "application/json")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        LoginResponse response = loginService.validateLogin(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
