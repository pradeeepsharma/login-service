package com.learning.microservices.loginservice.repository;

import com.learning.microservices.loginservice.domain.Credentials;
import com.learning.microservices.loginservice.domain.LoginInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginInfoRepository extends CrudRepository<Credentials, Long> {

    Credentials findByCustomerId(Long id);
}
