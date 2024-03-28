package org.example.service.store;

import org.example.model.Store;

public interface StoreRepository {
  public void createStore(String storeName, String storeEmail, String number);
  public Store readStore(Long storeId);
  public void updateStore(Long storeId, String storeName, String storeEmail, String number);
  public void deleteStore(Long storeId);
}
