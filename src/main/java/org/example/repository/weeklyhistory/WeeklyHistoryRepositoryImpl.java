package org.example.repository.weeklyhistory;

import org.example.model.history.WeekDay;
import org.example.model.history.WeeklyHistory;
import javax.persistence.EntityManager;
import java.util.List;

public class WeeklyHistoryRepositoryImpl implements WeeklyHistoryRepository{

    EntityManager entityManager;

    public WeeklyHistoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public WeeklyHistory createWeeklyHistory(List<WeekDay> weekDays) {
        entityManager.getTransaction().begin();
        WeeklyHistory weeklyHistory = new WeeklyHistory();
        weeklyHistory.setDays(weekDays);
        entityManager.persist(weeklyHistory);
        return weeklyHistory;
    }

    @Override
    public WeeklyHistory readWeeklyHistory(Long weeklyHistoryId) {
        entityManager.getTransaction().begin();
        WeeklyHistory weeklyHistory = entityManager.find(WeeklyHistory.class, weeklyHistoryId);
        entityManager.getTransaction().commit();
        return weeklyHistory;
    }

    @Override
    public void updateWeeklyHistory(Long weeklyHistoryId, WeekDay weekDay) {
        entityManager.getTransaction().begin();
        WeeklyHistory weeklyHistory = entityManager.find(WeeklyHistory.class, weeklyHistoryId);
        List<WeekDay> dayList = weeklyHistory.getDays();
        List<WeekDay> updatedDayList = updateDays(dayList, weekDay);
        weeklyHistory.setDays(updatedDayList);
        entityManager.persist(weeklyHistory);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteWeeklyHistory(Long weeklyHistoryId) {
        entityManager.getTransaction().begin();
        WeeklyHistory weeklyHistory = entityManager.find(WeeklyHistory.class, weeklyHistoryId);
        entityManager.remove(weeklyHistory);
        entityManager.getTransaction().commit();
    }

    private List<WeekDay> updateDays(List<WeekDay> dayList, WeekDay weekDay) {
        for (WeekDay day : dayList) {
            if (day.getDayName() == weekDay.getDayName()) {
                day = weekDay;
            }
        }
        return dayList;
    }
}
