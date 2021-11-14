package com.learning.microservices.loginservice.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Credentials {
    @Id
    @GeneratedValue
    private int id;
    private Long customerId;
    private String email;
    private String password;

}
