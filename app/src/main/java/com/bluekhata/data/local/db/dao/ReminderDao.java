package com.bluekhata.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.bluekhata.data.model.db.Reminder;

import java.util.List;

@Dao
public interface ReminderDao {

    @Query("SELECT * FROM reminder_table")
    List<Reminder> getReminderList();

    @Query("SELECT * FROM reminder_table WHERE reminderId = :reminderId")
    Reminder getReminderById(long reminderId);

    @Query("SELECT * FROM reminder_table WHERE reminderTitle = :reminderTitle")
    Reminder getReminderByTitle(String reminderTitle);

    @Insert
    void insertAllReminders(List<Reminder> reminders);
}
