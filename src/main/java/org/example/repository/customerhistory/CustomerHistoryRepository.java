package org.example.repository.customerhistory;

import org.example.model.history.CustomerHistory;
import org.example.model.history.Day;

public interface CustomerHistoryRepository {

    CustomerHistory createCustomerHistory();

    CustomerHistory readCustomerHistory(Long weeklyHistoryId);

    void updateCustomerHistory(Long weeklyHistoryId, Day day);

    void deleteCustomerHistory(Long weeklyHistoryId);

}
