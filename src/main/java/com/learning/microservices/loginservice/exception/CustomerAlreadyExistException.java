package com.learning.microservices.loginservice.exception;

public class CustomerAlreadyExistException extends RuntimeException {
    public CustomerAlreadyExistException(String message){
        super(message);
    }
}
