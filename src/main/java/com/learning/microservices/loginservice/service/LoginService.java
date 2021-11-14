package com.learning.microservices.loginservice.service;

import com.learning.microservices.loginservice.domain.Credentials;
import com.learning.microservices.loginservice.domain.Customer;
import com.learning.microservices.loginservice.exception.CustomerNotFoundException;
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
    @Value("${customer.service.uri}")
    private String customerService;


    public Boolean createLogin(PasswordRequest passwordRequest) throws CustomerNotFoundException {
        Boolean loginCreated = false;
        Customer customer = getCustomer(passwordRequest.getCustomerId());
        Credentials credentials = Credentials.builder()
                .customerId(passwordRequest.getCustomerId())
                .password(passwordRequest.getPassword())
                .email(customer.getEmail())
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
}
