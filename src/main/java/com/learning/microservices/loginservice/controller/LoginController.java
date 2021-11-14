package com.learning.microservices.loginservice.controller;

import com.learning.microservices.loginservice.exception.CustomerNotFoundException;
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
@RequiredArgsConstructor
//@Validated
public class LoginController {
    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    /*@PostMapping(value = "/customer", produces = " application/json")
    public ResponseEntity<CustomerResponse> addCustomer(@Valid@RequestBody CustomerRequest request)throws CustomerAlreadyExistException {
        logger.debug("request received :"+request);
        Customer customer = customerService.addCustomer(request);
        CustomerResponse response = CustomerResponse.builder().customerId(customer.getId()).build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/customer")
    public ResponseEntity<List<Customer>> getCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(),HttpStatus.OK);
    }*/

    @PostMapping(value = "/login/password", produces = "application/json")
    public ResponseEntity<Boolean> createLogin( @RequestBody PasswordRequest request)throws CustomerNotFoundException {
        return new ResponseEntity<>(loginService.createLogin(request), HttpStatus.CREATED);
    }
}
