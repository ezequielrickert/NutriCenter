package org.example.repository.store;

import org.example.model.Store;

import javax.persistence.EntityManager;

public class StoreRepositoryImpl implements StoreRepository{

  EntityManager entityManager;
  public StoreRepositoryImpl(EntityManager entityManager){
    this.entityManager = entityManager;
  }

  @Override
  public void createStore(String storeName, String storeEmail, String storePassword) {
    entityManager.getTransaction().begin();
    Store store = new Store(storeName, storeEmail, storePassword);
    entityManager.persist(store);
    entityManager.getTransaction().commit();
  }

  @Override
  public Store readStore(Long storeId) {
    entityManager.getTransaction().begin();
    Store store = entityManager.find(Store.class, storeId);
    entityManager.getTransaction().commit();
    return store;
  }

  @Override
  public void updateStore(Long storeId, String storeName, String storeEmail, String storePassword) {
    entityManager.getTransaction().begin();
    Store store = entityManager.find(Store.class, storeId);
    store.setStoreName(storeName);
    store.setStoreMail(storeEmail);
    store.setStorePhoneNumber(storePassword);
    entityManager.persist(store);
    entityManager.getTransaction().commit();
  }

  @Override
  public void deleteStore(Long storeId) {
    entityManager.getTransaction().begin();

    Store store = entityManager.find(Store.class, storeId);
    if (store != null) {
      entityManager.remove(store);
    }

    entityManager.getTransaction().commit();
  }
}
