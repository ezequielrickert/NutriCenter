package org.example.repository.store;

import org.example.model.Store;

public interface StoreRepository {
  void createStore(String storeName, String storeEmail, String storePassword);
  Store readStore(Long storeId);
  void updateStore(Long storeId, String storeName, String storeEmail, String number);
  void deleteStore(Long storeId);
}
