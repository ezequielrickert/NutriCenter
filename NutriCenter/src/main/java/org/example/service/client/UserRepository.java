package org.example.service.client;

import org.example.model.Client;

public interface UserRepository {
  public void createUser(String username, String email);
  public Client readUser(Long clientId);

  public void updateUser(Long clientId, String username, String email);
  public void deleteUser(Long clientId);

}
