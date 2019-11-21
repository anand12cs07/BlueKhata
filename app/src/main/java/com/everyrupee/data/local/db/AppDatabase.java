package com.everyrupee.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.everyrupee.data.local.db.dao.CategoryDao;
import com.everyrupee.data.local.db.dao.TransactionDao;
import com.everyrupee.data.model.db.Category;
import com.everyrupee.data.model.db.Recurrence;
import com.everyrupee.data.model.db.Tag;
import com.everyrupee.data.local.db.dao.RecurrenceDao;
import com.everyrupee.data.local.db.dao.ReminderDao;
import com.everyrupee.data.local.db.dao.TagDao;
import com.everyrupee.data.local.db.dao.TransactionTagDao;
import com.everyrupee.data.model.db.Reminder;
import com.everyrupee.data.model.db.Transaction;
import com.everyrupee.data.model.db.TransactionTags;

@Database(entities = {Category.class, Recurrence.class, Reminder.class, Tag.class,
        Transaction.class, TransactionTags.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract RecurrenceDao recurrenceDao();

    public abstract ReminderDao reminderDao();

    public abstract TagDao tagDao();

    public abstract TransactionDao transactionDao();

    public abstract TransactionTagDao transactionTagsDao();
}
