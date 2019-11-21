package com.everyrupee.data.model.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DATE;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;

@Entity(tableName = "recurrence_table")
public class Recurrence {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long recurrenceId;

    @NonNull
    private String recurrenceTitle;


    @Ignore
    public Recurrence(String recurrenceTitle) {
        this.recurrenceTitle = recurrenceTitle;
    }

    public Recurrence(long recurrenceId, String recurrenceTitle) {
        this.recurrenceId = recurrenceId;
        this.recurrenceTitle = recurrenceTitle;
    }

    public long getRecurrenceId() {
        return recurrenceId;
    }

    @NonNull
    public String getRecurrenceTitle() {
        return recurrenceTitle;
    }

    public static ArrayList<Recurrence> getInitialRecurrences() {
        ArrayList<Recurrence> recurrences = new ArrayList<>();
        recurrences.add(new Recurrence("None"));
        recurrences.add(new Recurrence("Daily"));
        recurrences.add(new Recurrence("Monday to Friday"));
        recurrences.add(new Recurrence("Evey Saturday"));
        recurrences.add(new Recurrence("Every Sunday"));
        recurrences.add(new Recurrence("Saturday and Sunday"));
        recurrences.add(new Recurrence("Every Week"));
        recurrences.add(new Recurrence("Every 2 Week"));
        recurrences.add(new Recurrence("Every Month"));
        return recurrences;
    }

    public static Date getNextDate(Date date, String recurrenceTitle) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (recurrenceTitle) {
            case "Daily":
                calendar.add(DATE, 1);
                return calendar.getTime();
            case "Monday to Friday":
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    calendar.add(DATE, 3);
                    return calendar.getTime();
                } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    calendar.add(DATE, 2);
                    return calendar.getTime();
                } else {
                    calendar.add(DATE, 1);
                    return calendar.getTime();
                }
            case "Evey Saturday":
                calendar.set(Calendar.DAY_OF_WEEK, SATURDAY);
                return calendar.getTime();
            case "Every Sunday":
                calendar.set(Calendar.DAY_OF_WEEK, SUNDAY);
                calendar.add(DATE, 7);
                return calendar.getTime();
            case "Saturday and Sunday":
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    calendar.add(DATE, 1);
                    return calendar.getTime();
                } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    calendar.set(DATE, 6);
                    return calendar.getTime();
                } else {
                    calendar.set(Calendar.DAY_OF_WEEK, SATURDAY);
                    return calendar.getTime();
                }
            case "Every Week":
                calendar.add(Calendar.DAY_OF_WEEK, 1);
                return calendar.getTime();
            case "Every 2 Week":
                calendar.add(Calendar.DAY_OF_WEEK, 2);
                return calendar.getTime();
            case "Every Month":
                calendar.add(Calendar.MONTH, 1);
                return calendar.getTime();
            default:
                return null;
        }
    }
}
