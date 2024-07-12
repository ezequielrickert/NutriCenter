package org.example.repository.weighthistory;

import org.example.model.history.WeightHistory;
import org.example.model.roles.Customer;

import java.time.LocalDate;
import java.util.List;

public interface WeightHistoryRepository {

    public WeightHistory createWeightHistory(double weight, LocalDate date, Customer customer);

    public WeightHistory getWeightHistory();

    public void updateWeightHistory(Long id, double weight, LocalDate date);

    public void deleteWeightHistory(Long id);

    List<WeightHistory> getCustomerHistory(Long customerId);
}
