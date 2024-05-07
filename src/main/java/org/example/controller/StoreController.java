package org.example.controller;
import org.example.model.Store;
import org.example.service.StoreService;

import org.example.model.roles.Store;
import org.example.repository.store.StoreRepositoryImpl;

import javax.persistence.EntityManager;

public class StoreController {

  StoreService storeService;

  public StoreController(EntityManager entityManager) {
    storeService = new StoreService(entityManager);
  }

  public void createStore(String storeName, String storeEmail, String storePassword) {
    storeService.createStore(storeName, storeEmail, storePassword);
  }

  public Store readStore(Long storeId) {
    return storeService.readStore(storeId);
  }

  public void updateStore(Long storeId, String storeName, String storeEmail, String storePassword) {
    storeService.updateStore(storeId, storeName, storeEmail, storePassword);
  }

  public void deleteStore(Long storeId) {
    storeService.deleteStore(storeId);
  }
}