package com.learning.microservices.loginservice.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PasswordResponse {
    private boolean accepted;
}
