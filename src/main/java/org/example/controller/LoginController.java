package org.example.controller;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.model.login.Authenticator;
import org.example.model.login.LoginData;
import org.example.service.LoginService;
import spark.Spark;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.example.Application.gson;


public class LoginController {

    public void run() {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
        LoginService loginService = new LoginService(entityManagerFactory.createEntityManager());

        Spark.post("/authenticateUser", (req, res) -> {
            String body = req.body();
            LoginData loginData = gson.fromJson(body, LoginData.class);
            String username = loginData.getUsername();
            String password = loginData.getPassword();
            String userPassword = loginService.fetchUser(username);
            if (userPassword != null && userPassword.equals(password)) {
                // if the user is found and the password matches, return a token
                String token = UUID.randomUUID().toString();
                Authenticator.storeToken(username, token);
                Map<String, String> responseMap = new HashMap<>();
                // Saves the token asigned to the user
                responseMap.put("token", token);
                // Saves the name of the user
                responseMap.put("username", username);
                // Saves the role of the user
                responseMap.put("role", loginService.fetchUserRol(username));
                return responseMap;
            } else {
                // if the username is not found or the password does not match, return an error message
                res.status(401);
                return "Username or password is incorrect";
            }
        }, gson::toJson);

        Spark.post("/validateUser", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String username = gson.fromJson(jsonObject.get("username"), String.class);
            String token = gson.fromJson(jsonObject.get("token"), String.class);
            if (Authenticator.validateUser(username, token)) {
                return "User is valid";
            } else {
                res.status(401);
                return "User is not valid";
            }
        }, gson::toJson);

    }
}
