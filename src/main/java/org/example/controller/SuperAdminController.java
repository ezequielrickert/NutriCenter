package org.example.controller;
import org.example.service.SignUpService;
import org.example.service.SuperAdminService;

import org.example.model.roles.SuperAdmin;
import org.example.repository.superadmin.SuperAdminRepositoryImpl;
import spark.Spark;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.example.Application.gson;

public class SuperAdminController {

    public void run() {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
        SuperAdminService superAdminService = new SuperAdminService(entityManagerFactory.createEntityManager());
        SignUpService signUpService = new SignUpService(entityManagerFactory.createEntityManager());

        Spark.post("/createSuperAdmin", (req, res) -> {
            String body = req.body();
            SuperAdmin superAdmin = gson.fromJson(body, SuperAdmin.class);
            String username = superAdmin.getAdminUsername();
            if (!signUpService.validate(username)) {
                res.status(401);
                return "Username already exists";
            }
            String password = superAdmin.getAdminPassword();
            superAdminService.createSuperAdmin(username, password);
            return superAdmin;
        }, gson::toJson);

    }

}
