package org.example.repository.customerhistory;

import org.example.model.history.CustomerHistory;
import org.example.model.history.Day;
import org.example.service.DayService;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.util.List;

public class CustomerHistoryRepositoryImplementation implements CustomerHistoryRepository {

    EntityManager entityManager;

    public CustomerHistoryRepositoryImplementation(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CustomerHistory createCustomerHistory() {
        entityManager.getTransaction().begin();
        CustomerHistory customerHistory = new CustomerHistory();
        entityManager.persist(customerHistory);
        return customerHistory;
    }

    @Override
    public CustomerHistory readCustomerHistory(Long weeklyHistoryId) {
        entityManager.getTransaction().begin();
        CustomerHistory customerHistory = entityManager.find(CustomerHistory.class, weeklyHistoryId);
        entityManager.getTransaction().commit();
        return customerHistory;
    }

    // Le paso id del history y un dia para agregar al historial
    @Override
    public CustomerHistory updateCustomerHistory(Long customerHistoryId, DayOfWeek dayName){
        entityManager.getTransaction().begin();
        CustomerHistory customerHistory = entityManager.find(CustomerHistory.class, customerHistoryId);
        DayService dayService = new DayService(entityManager);
        Day day = dayService.createDay(dayName);
        customerHistory.getDays().add(day);
        entityManager.merge(customerHistory);
        entityManager.getTransaction().commit();
        return customerHistory;
    }

    @Override
    public void deleteCustomerHistory(Long weeklyHistoryId) {
        entityManager.getTransaction().begin();
        CustomerHistory customerHistory = entityManager.find(CustomerHistory.class, weeklyHistoryId);
        entityManager.remove(customerHistory);
        entityManager.getTransaction().commit();
    }
}
