package org.example.repository.store;

import org.example.model.roles.Store;

import java.util.List;

public interface StoreRepository {
  void createStore(String storeName, String storeEmail, String storePassword);
  Store readStore(Long storeId);
  void updateStore(Long storeId, String storeName, String storeEmail, String storePassword);
  void deleteStore(Long storeId);
  Store fetchStoreByName(String storeName);
  List<Store> fetchStoreWildcard(String searchTerm);
}