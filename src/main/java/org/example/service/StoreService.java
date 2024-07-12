package org.example.service;

import org.example.model.roles.Customer;
import org.example.model.roles.Store;
import org.example.repository.store.StoreRepositoryImpl;
import javax.persistence.EntityManager;
import java.util.List;

public class StoreService {

    StoreRepositoryImpl storeRepository;

    public StoreService(EntityManager entityManager) {
        storeRepository = new StoreRepositoryImpl(entityManager);
    }

    public void createStore(String storeName, String storeEmail, String storePassword) {
        storeRepository.createStore(storeName, storeEmail, storePassword);
    }

    public Store readStore(Long storeId) {
        return storeRepository.readStore(storeId);
    }

    public void updateStore(Long storeId, String storeName, String storeEmail, String storePassword) {
        storeRepository.updateStore(storeId, storeName, storeEmail, storePassword);
    }

    public void deleteStore(Long storeId) {
        storeRepository.deleteStore(storeId);
    }

    public Store getStoreByUsername(String username) {
        return storeRepository.fetchStoreByName(username);
    }

    public List<Store> getStoresBySearchTerm(String searchTerm) {
        return storeRepository.fetchStoreWildcard(searchTerm);
    }
}