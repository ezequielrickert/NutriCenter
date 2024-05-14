package org.example.service;


import org.example.model.history.CustomerHistory;
import org.example.model.roles.Customer;
import org.example.repository.customer.CostumerRepository;
import org.example.repository.customer.CostumerRepositoryImp;
import org.example.repository.day.DayRepository;
import org.example.repository.day.DayRepositoryImpl;
import org.example.repository.customerhistory.CustomerHistoryRepository;
import org.example.repository.customerhistory.CustomerHistoryRepositoryImplementation;


import javax.persistence.EntityManager;


public class CustomerService {


    private EntityManager entityManager;
    private CostumerRepository customerRepository;
    private CustomerHistoryRepository customerHistoryRepository;
    private DayRepository dayRepository;


    public CustomerService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.customerRepository = new CostumerRepositoryImp(entityManager);
        this.customerHistoryRepository = new CustomerHistoryRepositoryImplementation(entityManager);
        this.dayRepository = new DayRepositoryImpl(entityManager);
    }


    public void createUser(String username, String email, String password) {
        // List<Day> dayList = createDays();
        CustomerHistory customerHistory = customerHistoryRepository.createCustomerHistory();
        customerRepository.createUser(username, email, password, customerHistory);
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

    /*
    private List<Day> createDays() {
        List<Day> weekDays = new ArrayList<>();
        for (DayOfWeek Day : DayOfWeek.values()) {
            Day weekDay = dayRepository.createWeekDay(Day);
            weekDays.add(weekDay);
        }
        return weekDays;
    }

     */

    public Customer getCustomerByName(String username){
        return customerRepository.fetchCustomerByUsername(username);
    }
}

