package org.example.repository.weighthistory;

import org.example.model.history.WeightHistory;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class WeightHistoryRepositoryImpl implements WeightHistoryRepository{

    EntityManager entityManager;

    public WeightHistoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public WeightHistory createWeightHistory(double weight, LocalDate date) {
        entityManager.getTransaction().begin();
        WeightHistory weightHistory = new WeightHistory();
        weightHistory.setWeight(weight);
        weightHistory.setDate(date);
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
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteWeightHistory(Long id) {
        entityManager.getTransaction().begin();
        WeightHistory weightHistory = entityManager.find(WeightHistory.class, id);
        entityManager.remove(weightHistory);
        entityManager.getTransaction().commit();

    }
}
