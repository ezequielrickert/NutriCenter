package org.example.repository.weeklyhistory;

import org.example.model.history.WeekDay;
import org.example.model.history.WeeklyHistory;

import java.util.List;

public interface WeeklyHistoryRepository {

    WeeklyHistory createWeeklyHistory(List<WeekDay> weekDays);

    WeeklyHistory readWeeklyHistory(Long weeklyHistoryId);

    void updateWeeklyHistory(Long weeklyHistoryId, WeekDay weekDay);

    void deleteWeeklyHistory(Long weeklyHistoryId);

}
