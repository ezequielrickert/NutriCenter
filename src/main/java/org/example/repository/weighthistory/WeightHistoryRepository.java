package org.example.repository.weighthistory;

import org.example.model.history.WeightHistory;

import java.time.LocalDate;

public interface WeightHistoryRepository {

    public WeightHistory createWeightHistory(double weight, LocalDate date);

    public WeightHistory getWeightHistory();

    public void updateWeightHistory(Long id, double weight, LocalDate date);

    public void deleteWeightHistory(Long id);
}
