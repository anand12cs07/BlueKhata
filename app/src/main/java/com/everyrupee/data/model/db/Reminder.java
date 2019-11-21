package com.everyrupee.data.model.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.ArrayList;

@Entity(tableName = "reminder_table")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long reminderId;

    @NonNull
    private String reminderTitle;

    @Ignore
    public Reminder(String reminderTitle) {
        this.reminderTitle = reminderTitle;
    }

    public Reminder(long reminderId, String reminderTitle) {
        this.reminderId = reminderId;
        this.reminderTitle = reminderTitle;
    }

    public long getReminderId() {
        return reminderId;
    }

    @NonNull
    public String getReminderTitle() {
        return reminderTitle;
    }

    public static ArrayList<Reminder> getInitialReminders() {
        ArrayList<Reminder> reminders = new ArrayList<>();
        reminders.add(new Reminder("None"));
        reminders.add(new Reminder("Same Day"));
        reminders.add(new Reminder("One Day Before"));
        reminders.add(new Reminder("Two Day Before"));
        reminders.add(new Reminder("Three Day Before"));
        return reminders;
    }
}
