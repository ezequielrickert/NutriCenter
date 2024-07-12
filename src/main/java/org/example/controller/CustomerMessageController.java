package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.roles.Customer;
import org.example.model.stock.CustomerMessage;
import org.example.service.CustomerMessageService;
import org.example.service.CustomerService;
import spark.Spark;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class CustomerMessageController {

    public void run() {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
        CustomerMessageService customerMessageService = new CustomerMessageService(entityManagerFactory.createEntityManager());
        CustomerService customerService = new CustomerService(entityManagerFactory.createEntityManager());

        Spark.get("/message/:username", (req, res) -> {
            String username = req.params(":username");
            Customer customer = customerService.getCustomerByName(username);
            List<CustomerMessage> messages = customerMessageService.getMessage(customer);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.toJson(messages);
        });

        Spark.get("/message/unread/:username", (req, res) -> {
            String username = req.params(":username");
            Customer customer = customerService.getCustomerByName(username);
            List<CustomerMessage> messages = customerMessageService.getUnreadMessages(customer);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.toJson(messages);
        });

        Spark.put("/message/read/:id", (req, res) -> {
            long messageId = Long.parseLong(req.params(":id"));
            customerMessageService.markMessageAsRead(messageId);
            return "OK";
        });

        Spark.put("/message/readAll/:username", (req, res) -> {
            String username = req.params(":username");
            customerMessageService.markAllAsRead(username);
            return "OK";
        });
    }
}
