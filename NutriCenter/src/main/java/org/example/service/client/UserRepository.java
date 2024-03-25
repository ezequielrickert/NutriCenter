package org.example.service.client;

import java.sql.ResultSet;

public interface UserRepository {
  public void createUser(String username, String email);
  public ResultSet readUser(Long clientId);

  public void updateUser(Long clientId, String username, String email);
  public void deleteUser(Long clientId);

}
