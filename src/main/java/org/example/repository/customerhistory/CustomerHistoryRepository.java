package org.example.repository.customerhistory;

import org.example.model.history.CustomerHistory;
import org.example.model.history.Day;

import java.time.DayOfWeek;

public interface CustomerHistoryRepository {

    CustomerHistory createCustomerHistory();

    CustomerHistory readCustomerHistory(Long weeklyHistoryId);

    CustomerHistory updateCustomerHistory(Long weeklyHistoryId, DayOfWeek day);

    void deleteCustomerHistory(Long weeklyHistoryId);

}
