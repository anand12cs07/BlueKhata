package com.bluekhata.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.bluekhata.data.local.db.dao.CategoryDao;
import com.bluekhata.data.local.db.dao.TransactionDao;
import com.bluekhata.data.model.db.Category;
import com.bluekhata.data.model.db.Recurrence;
import com.bluekhata.data.model.db.Tag;
import com.bluekhata.data.local.db.dao.RecurrenceDao;
import com.bluekhata.data.local.db.dao.ReminderDao;
import com.bluekhata.data.local.db.dao.TagDao;
import com.bluekhata.data.local.db.dao.TransactionTagDao;
import com.bluekhata.data.model.db.Reminder;
import com.bluekhata.data.model.db.Transaction;
import com.bluekhata.data.model.db.TransactionTags;

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
