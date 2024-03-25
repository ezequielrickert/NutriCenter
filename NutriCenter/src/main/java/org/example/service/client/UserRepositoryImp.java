package org.example.service.client;

import java.sql.*;

public class UserRepositoryImp implements UserRepository{
  private static final String URL = "jdbc:hsqldb:file:/Users/ezequielrickert/projects/lab1/Database/hsqldb-2.7.2/hsqldb";
  private static final String USER = "sa";
  private static final String PASSWORD = "";
  @Override
  public void createUser(String username, String email) {
    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
      String sql = "INSERT INTO Client (clientName, clientEmail) VALUES (?, ?)";
      PreparedStatement pst = con.prepareStatement(sql);
      pst.setString(1, username);
      pst.setString(2, email);
      pst.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public ResultSet readUser(Long clientId) {
    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
      String sql = "SELECT * FROM Client WHERE clientId = ?";
      PreparedStatement pst = con.prepareStatement(sql);
      pst.setLong(1, clientId);
      ResultSet resultSet = pst.executeQuery();
      return resultSet;
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  @Override
  public void updateUser(Long clientId, String username, String email) {
    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
      String sql = "UPDATE Client SET clientName = ?, clientEmail = ? WHERE clientId = ?";
      PreparedStatement pst = con.prepareStatement(sql);
      pst.setString(1, username);
      pst.setString(2, email);
      pst.setLong(3, clientId);
      pst.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void deleteUser(Long clientId) {
    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
      String sql = "DELETE FROM Client WHERE clientId = ?";
      PreparedStatement pst = con.prepareStatement(sql);
      pst.setLong(1, clientId);
      pst.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
