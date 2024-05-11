package org.example.service;


import org.example.model.history.WeekDay;
import org.example.model.history.WeeklyHistory;
import org.example.model.roles.Customer;
import org.example.repository.customer.CostumerRepository;
import org.example.repository.customer.CostumerRepositoryImp;
import org.example.repository.weekday.WeekDayRepository;
import org.example.repository.weekday.WeekDayRepositoryImpl;
import org.example.repository.weeklyhistory.WeeklyHistoryRepository;
import org.example.repository.weeklyhistory.WeeklyHistoryRepositoryImpl;


import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CustomerService {


    private EntityManager entityManager;
    private CostumerRepository customerRepository;
    private WeeklyHistoryRepository weeklyHistoryRepository;
    private WeekDayRepository weekDayRepository;


    public CustomerService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.customerRepository = new CostumerRepositoryImp(entityManager);
        this.weeklyHistoryRepository = new WeeklyHistoryRepositoryImpl(entityManager);
        this.weekDayRepository = new WeekDayRepositoryImpl(entityManager);
    }


    public void createUser(String username, String email, String password) {
        List<WeekDay> dayList = createDays();
        WeeklyHistory weeklyHistory = weeklyHistoryRepository.createWeeklyHistory(dayList);
        customerRepository.createUser(username, email, password, weeklyHistory);
    }


    public Customer readUser(Long inClientId) {
        return customerRepository.readUser(inClientId);
    }


    public void updateUser(Long clientId, String username, String email) {
        customerRepository.updateUser(clientId, username, email);
    }


    public void deleteUser(Long clientId) {
        customerRepository.deleteUser(clientId);
    }

    private List<WeekDay> createDays() {
        List<WeekDay> weekDays = new ArrayList<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            WeekDay weekDay = weekDayRepository.createWeekDay(day);
            weekDays.add(weekDay);
        }
        return weekDays;
    }
}

