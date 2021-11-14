package com.learning.microservices.loginservice.exception;

public class InvalidLoginAttempt extends RuntimeException {
    public InvalidLoginAttempt(String invalid_login_credentials) {
    }
}
