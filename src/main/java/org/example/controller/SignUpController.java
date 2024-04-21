package org.example.controller;

import org.example.service.SignUpService;

import javax.persistence.EntityManager;

public class SignUpController {

    EntityManager entityManager;
    SignUpService signUpService;

    public SignUpController(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.signUpService = new SignUpService(entityManager);
    }
    public boolean validate(String username) {
        return signUpService.validate(username);
    }

}
