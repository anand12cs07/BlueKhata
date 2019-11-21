package com.everyrupee.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.everyrupee.data.model.db.Recurrence;

import java.util.List;

@Dao
public interface RecurrenceDao {

    @Query("SELECT * FROM recurrence_table")
    List<Recurrence> getAllRecurrences();

    @Query("SELECT * FROM recurrence_table WHERE recurrenceId = :recurrenceId")
    Recurrence getRecurrenceById(long recurrenceId);

    @Query("SELECT * FROM recurrence_table WHERE recurrenceTitle = :recurrenceTitle")
    Recurrence getRecurrenceByTitle(String recurrenceTitle);

    @Insert
    void insertAllRecurrences(List<Recurrence> recurrences);
}
