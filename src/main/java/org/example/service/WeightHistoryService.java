package org.example.service;

import org.example.model.history.CustomerHistory;
import org.example.model.history.WeightHistory;
import org.example.model.roles.Customer;
import org.example.repository.customer.CostumerRepository;
import org.example.repository.customer.CostumerRepositoryImp;
import org.example.repository.weighthistory.WeightHistoryRepository;
import org.example.repository.weighthistory.WeightHistoryRepositoryImpl;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WeightHistoryService {

    EntityManager entityManager;
    WeightHistoryRepository weightHistoryRepository;
    CostumerRepository costumerRepository;

    public WeightHistoryService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.weightHistoryRepository = new WeightHistoryRepositoryImpl(entityManager);
        this.costumerRepository = new CostumerRepositoryImp(entityManager);
    }

    public void addWeight(String username, Double weight) {
        Customer customer = costumerRepository.fetchCustomerByUsername(username);
        List<WeightHistory> weightHistoryList = customer.getWeightHistory();
        weightHistoryList = verifyWeightHistory(weightHistoryList, weight, customer);
        // Ensure the WeightHistory entities are managed
        weightHistoryList = weightHistoryList.stream()
                .map(wh -> entityManager.contains(wh) ? wh : entityManager.merge(wh))
                .collect(Collectors.toList());
        costumerRepository.updateUser(customer.getCustomerId(), customer.getCustomerName(), customer.getCustomerEmail(),
                customer.getNutritionists(),customer.getStores(), customer.getIngredients(),
                weightHistoryList);
    }

    private List<WeightHistory> verifyWeightHistory(List<WeightHistory> weightHistoryList, Double weight, Customer customer) {
        int i = 0;
        for (WeightHistory weightHistory : weightHistoryList) {
            if (Objects.equals(weightHistory.getDate(), LocalDate.now())){
                Long id = weightHistory.getWeightHistoryId();
                weightHistoryRepository.updateWeightHistory(id, weight, LocalDate.now());
                i = 1;
            }
        }
        if(i == 0) {
            WeightHistory newWeight = weightHistoryRepository.createWeightHistory(weight, LocalDate.now(), customer);
            weightHistoryList.add(newWeight);
        }
        return weightHistoryList;
    }

    public List<WeightHistory> getWeightHistory(Customer customer, LocalDate fromDate) {
        List<WeightHistory> weightHistoryList = weightHistoryRepository.getCustomerHistory(customer.getCustomerId());
        return weightHistoryList.stream()
                .filter(wh -> wh.getDate().isAfter(fromDate))
                .collect(Collectors.toList());
    }
}
