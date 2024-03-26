package org.example.controller;

import org.example.model.Client;
import org.example.service.client.UserRepositoryImp;

public class ClientController {
  UserRepositoryImp userRepositoryImp;
  public ClientController() {
    this.userRepositoryImp = new UserRepositoryImp();
  }
  public void createClient(String username, String email) {
    userRepositoryImp.createUser(username, email);
  }

  public void readClient(Long inClientId) {
    Client client = userRepositoryImp.readUser(inClientId);
    if (client != null) {
      System.out.println("Client ID: " + client.getClientId());
      System.out.println("Client Name: " + client.getClientName());
      System.out.println("Client Email: " + client.getClientEmail());
    } else {
      System.out.println("Client not found");
    }
  }

  public void updateClient(Long clientId, String username, String email) {
    userRepositoryImp.updateUser(clientId, username, email);
  }

  public void deleteClient(Long clientId) {
    userRepositoryImp.deleteUser(clientId);
  }
}
