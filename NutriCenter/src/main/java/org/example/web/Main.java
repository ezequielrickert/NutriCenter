package org.example.web;

import static spark.Spark.*;

public class Main {
  public static void main(String[] args) {
    before((req, res) -> {
      res.header("Access-Control-Allow-Origin", "*");
      res.header("Access-Control-Allow-Methods", "GET,POST");
      res.header("Access-Control-Allow-Headers", "*");
    });

    get("/api/data", (req, res) -> {
      res.type("application/json");
      return "{\"message\":\"Hello from Spark Java\"}";
    });
  }
}