package com.learning.microservices.loginservice.service;

import com.learning.microservices.loginservice.domain.Credentials;
import com.learning.microservices.loginservice.domain.Customer;
import com.learning.microservices.loginservice.exception.CustomerNotFoundException;
import com.learning.microservices.loginservice.exception.InvalidLoginAttempt;
import com.learning.microservices.loginservice.jwt.JwtGenerator;
import com.learning.microservices.loginservice.jwt.JwtValidator;
import com.learning.microservices.loginservice.model.LoginRequest;
import com.learning.microservices.loginservice.model.LoginResponse;
import com.learning.microservices.loginservice.model.PasswordRequest;
import com.learning.microservices.loginservice.repository.LoginInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static java.util.Objects.nonNull;

@Service
public class LoginService {
    @Autowired
    private LoginInfoRepository repository;
    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private JwtValidator jwtValidator;
    @Value("${customer.service.uri}")
    private String customerService;



    public Boolean createLogin(PasswordRequest passwordRequest) throws CustomerNotFoundException {
        Boolean loginCreated = false;
        Customer customer = getCustomer(passwordRequest.getCustomerId());
        Credentials credentials = Credentials.builder()
                .customerId(passwordRequest.getCustomerId())
                .password(passwordRequest.getPassword())
               // .email(customer.getEmail())
                .build();
        if(nonNull(repository.save(credentials)))
            loginCreated=true;
        return loginCreated;
    }

    private Customer getCustomer(Long id) throws CustomerNotFoundException {
        return WebClient.create()
                .get()
                .uri(customerService + "/" + id)
                .retrieve()
                .bodyToMono(Customer.class)
                .onErrorMap(v -> {
                    throw new CustomerNotFoundException("");
                })
                .block();
    }

    public LoginResponse validateLogin(LoginRequest loginRequest) {
        Customer customer = getCustomer(loginRequest.getUserName());
        Credentials credentials = repository.findByCustomerId(customer.getId());
        if(!credentials.getPassword().equals(loginRequest.getPassword())){
            throw new InvalidLoginAttempt("invalid login credentials");
        }
        String token = jwtGenerator.generateToken(customer);

        return LoginResponse.builder().token(token).build();
    }

    private Customer getCustomer(String email) throws CustomerNotFoundException {
        return WebClient.create()
                .get()
                .uri(customerService + "/login/" + email)
                .retrieve()
                .bodyToMono(Customer.class)
                .onErrorMap(v -> {
                    throw new CustomerNotFoundException("");
                })
                .block();
    }
}
