package com.learning.microservices.loginservice.exception;

public class CredentialsNotFoundException extends RuntimeException {
    public CredentialsNotFoundException(String no_credentials_found) {
        super(no_credentials_found);
    }
}
