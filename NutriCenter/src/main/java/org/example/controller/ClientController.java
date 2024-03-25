package org.example.controller;

import org.example.service.client.UserRepositoryImp;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientController {
  UserRepositoryImp userRepositoryImp;
  public ClientController() {
    this.userRepositoryImp = new UserRepositoryImp();
  }
  public void createClient(String username, String email) {
    userRepositoryImp.createUser(username, email);
  }

  public void readClient(Long inClientId) throws SQLException {
    ResultSet resultSet = userRepositoryImp.readUser(inClientId);
    if (resultSet.next()) {
      Long clientId = resultSet.getLong("clientId");
      String clientName = resultSet.getString("clientName");
      String clientEmail = resultSet.getString("clientEmail");

      System.out.println("Client ID: " + clientId);
      System.out.println("Client Name: " + clientName);
      System.out.println("Client Email: " + clientEmail);
    } else {
      System.out.println("No user found with the provided ID.");
    }
  }

  public void updateClient(Long clientId, String username, String email) {
    userRepositoryImp.updateUser(clientId, username, email);
  }

  public void deleteClient(Long clientId) {
    userRepositoryImp.deleteUser(clientId);
  }
}
