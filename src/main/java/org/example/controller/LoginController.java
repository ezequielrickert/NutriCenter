package org.example.controller;


import org.example.service.LoginService;


import javax.persistence.EntityManager;




public class LoginController {


    LoginService loginService;
    public LoginController(EntityManager entityManager) {
        this.loginService = new LoginService(entityManager);
    }


    public String fetchUser(String username) {
        return loginService.fetchUser(username);
    }

}
