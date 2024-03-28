package org.example.controller;

import org.example.model.Store;
import org.example.service.store.StoreRepositoryImpl;

import javax.persistence.EntityManager;

public class StoreController {

  StoreRepositoryImpl storeRepository;
  public StoreController(EntityManager entityManager) {
    storeRepository = new StoreRepositoryImpl(entityManager);
  }

  public void createStore(String storeName, String storeEmail, String storeNumber) {
    storeRepository.createStore(storeName, storeEmail, storeNumber);
  }

  public Store readStore(Long storeId) {
    return storeRepository.readStore(storeId);
  }

  public void updateStore(Long storeId, String storeName, String storeEmail, String storeNumber) {
    storeRepository.updateStore(storeId, storeName, storeEmail, storeNumber);
  }

  public void deleteStore(Long storeId) {
    storeRepository.deleteStore(storeId);
  }
}