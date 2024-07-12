package org.example.repository.weighthistory;

import org.example.model.history.WeightHistory;
import org.example.model.roles.Customer;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class WeightHistoryRepositoryImpl implements WeightHistoryRepository{

    EntityManager entityManager;

    public WeightHistoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public WeightHistory createWeightHistory(double weight, LocalDate date, Customer customer) {
        entityManager.getTransaction().begin();
        WeightHistory weightHistory = new WeightHistory();
        weightHistory.setWeight(weight);
        weightHistory.setDate(date);
        weightHistory.setCustomer(customer);
        entityManager.persist(weightHistory);
        entityManager.getTransaction().commit();
        return weightHistory;
    }

    @Override
    public WeightHistory getWeightHistory() {
        entityManager.getTransaction().begin();
        WeightHistory weightHistory = entityManager.find(WeightHistory.class, 1L);
        entityManager.getTransaction().commit();
        return weightHistory;
    }

    @Override
    public void updateWeightHistory(Long id, double weight, LocalDate date) {
        entityManager.getTransaction().begin();
        WeightHistory weightHistory = entityManager.find(WeightHistory.class, id);
        weightHistory.setWeight(weight);
        weightHistory.setDate(date);
        entityManager.merge(weightHistory); // merge instead of persist (update instead of insert
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteWeightHistory(Long id) {
        entityManager.getTransaction().begin();
        WeightHistory weightHistory = entityManager.find(WeightHistory.class, id);
        entityManager.remove(weightHistory);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<WeightHistory> getCustomerHistory(Long customerId) {
        entityManager.getTransaction().begin();
        List<WeightHistory> weightHistoryList = entityManager.createQuery("SELECT wh FROM WeightHistory wh WHERE wh.customer.customerId = :customerId")
                .setParameter("customerId", customerId)
                .getResultList();
        entityManager.getTransaction().commit();
        return weightHistoryList;
    }

}
