package com.bluekhata.data.local.db;

import com.bluekhata.data.model.db.Category;
import com.bluekhata.data.model.db.Recurrence;
import com.bluekhata.data.model.db.Tag;
import com.bluekhata.data.model.db.Reminder;
import com.bluekhata.data.model.db.Transaction;
import com.bluekhata.data.model.db.TransactionTags;
import com.bluekhata.data.model.db.custom.RecurrenceTransactionCategory;
import com.bluekhata.data.model.db.custom.TransactionWithCategory;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppDbHelper implements DbHelper {
    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    // Add Edit Category Module


    @Override
    public Observable<Boolean> isCategoryEmpty() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return mAppDatabase.categoryDao().getAllCategory().isEmpty();
            }
        });
    }

    @Override
    public Observable<Boolean> saveCategoryList(final List<Category> categoryList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.categoryDao().insertCategoryList(categoryList);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> insertCategory(final Category category) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.categoryDao().insertCategory(category);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> updateCategory(final Category category) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.categoryDao().updateCategory(category);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteCategory(final long categoryId) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.categoryDao().deleteCategoryById(categoryId);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteTransactionByCategoryId(final long categoryId) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.transactionDao().deleteTransactionByCategoryId(categoryId);
                return true;
            }
        });
    }

    @Override
    public Observable<List<Category>> getCategoryExpenses() {
        return Observable.fromCallable(new Callable<List<Category>>() {
            @Override
            public List<Category> call() throws Exception {
                return mAppDatabase.categoryDao().getExpenses();
            }
        });
    }

    @Override
    public Observable<List<Category>> getCategoryIncomes() {
        return Observable.fromCallable(new Callable<List<Category>>() {
            @Override
            public List<Category> call() throws Exception {
                return mAppDatabase.categoryDao().getIncomes();
            }
        });
    }

    @Override
    public Observable<List<Category>> getAllCategory() {
        return Observable.fromCallable(new Callable<List<Category>>() {
            @Override
            public List<Category> call() throws Exception {
                return mAppDatabase.categoryDao().getAllCategories();
            }
        });
    }

    @Override
    public Observable<Category> getCategory(final long categoryId) {
        return Observable.fromCallable(new Callable<Category>() {
            @Override
            public Category call() throws Exception {
                return mAppDatabase.categoryDao().getCategoryById(categoryId);
            }
        });
    }

    @Override
    public Observable<Integer> getMaxExpenseShortIndex() {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return mAppDatabase.categoryDao().getMaxExpenseShortIndex();
            }
        });
    }


    @Override
    public Observable<Integer> getMaxIncomeShortIndex() {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return mAppDatabase.categoryDao().getMaxIncomeShortIndex();
            }
        });
    }

    @Override
    public Observable<Boolean> updateDragCategoryListShortIndex(final int fromIndex, final int toIndex, final long id) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return mAppDatabase.categoryDao().dragCategoryList(fromIndex, toIndex, id);
            }
        });
    }

    @Override
    public Observable<Boolean> updateCategoryList(final List<Category> categoryList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.categoryDao().updateCategoryList(categoryList);
                return true;
            }
        });
    }

    // TODO Reminder Module

    @Override
    public Observable<Boolean> isReminderEmpty() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return mAppDatabase.reminderDao().getReminderList().isEmpty();
            }
        });
    }

    @Override
    public Observable<List<Reminder>> loadAllReminders() {
        return Observable.fromCallable(new Callable<List<Reminder>>() {
            @Override
            public List<Reminder> call() throws Exception {
                return mAppDatabase.reminderDao().getReminderList();
            }
        });
    }

    @Override
    public Observable<Reminder> getReminderById(final long id) {
        return Observable.fromCallable(new Callable<Reminder>() {
            @Override
            public Reminder call() throws Exception {
                return mAppDatabase.reminderDao().getReminderById(id);
            }
        });
    }

    @Override
    public Observable<Reminder> getReminderByTitle(final String title) {
        return Observable.fromCallable(new Callable<Reminder>() {
            @Override
            public Reminder call() throws Exception {
                return mAppDatabase.reminderDao().getReminderByTitle(title);
            }
        });
    }

    @Override
    public Observable<Boolean> saveReminderList(final List<Reminder> reminders) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.reminderDao().insertAllReminders(reminders);
                return true;
            }
        });
    }

    // Recurrence Module

    @Override
    public Observable<Boolean> isRecurrenceEmpty() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return mAppDatabase.recurrenceDao().getAllRecurrences().isEmpty();
            }
        });
    }

    @Override
    public Observable<List<Recurrence>> loadAllRecurrence() {
        return Observable.fromCallable(new Callable<List<Recurrence>>() {
            @Override
            public List<Recurrence> call() throws Exception {
                return mAppDatabase.recurrenceDao().getAllRecurrences();
            }
        });
    }

    @Override
    public Observable<Boolean> saveRecurrenceList(final List<Recurrence> recurrences) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.recurrenceDao().insertAllRecurrences(recurrences);
                return true;
            }
        });
    }

    @Override
    public Observable<Recurrence> getRecurrenceById(final long id) {
        return Observable.fromCallable(new Callable<Recurrence>() {
            @Override
            public Recurrence call() throws Exception {
                return mAppDatabase.recurrenceDao().getRecurrenceById(id);
            }
        });
    }

    @Override
    public Observable<Recurrence> getRecurrenceByTitle(final String title) {
        return Observable.fromCallable(new Callable<Recurrence>() {
            @Override
            public Recurrence call() throws Exception {
                return mAppDatabase.recurrenceDao().getRecurrenceByTitle(title);
            }
        });
    }

    // Tag Module

    @Override
    public Observable<Boolean> isTagEmpty() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return mAppDatabase.tagDao().getTagList().isEmpty();
            }
        });
    }

    @Override
    public Observable<List<Tag>> loadAllTags() {
        return Observable.fromCallable(new Callable<List<Tag>>() {
            @Override
            public List<Tag> call() throws Exception {
                return mAppDatabase.tagDao().getTagList();
            }
        });
    }

    @Override
    public Observable<Tag> getTagById(final long id) {
        return Observable.fromCallable(new Callable<Tag>() {
            @Override
            public Tag call() throws Exception {
                return mAppDatabase.tagDao().getTagById(id);
            }
        });
    }

    @Override
    public Observable<Tag> getTagByTitle(final String title) {
        return Observable.fromCallable(new Callable<Tag>() {
            @Override
            public Tag call() throws Exception {
                return mAppDatabase.tagDao().getTagByTitle(title);
            }
        });
    }

    @Override
    public Observable<Boolean> saveTagList(final List<Tag> tags) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.tagDao().insetTagList(tags);
                return true;
            }
        });
    }

    @Override
    public Observable<Long> addTag(final Tag tag) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mAppDatabase.tagDao().insetTag(tag);
            }
        });
    }

    @Override
    public Observable<Boolean> updateTag(final Tag tag) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.tagDao().updateTag(tag);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteTag(final Tag tag) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.tagDao().deleteTag(tag);
                return true;
            }
        });
    }

    @Override
    public Observable<List<Tag>> insertTransactionTag(final List<String> tagTitles, final long transactionId) {
        return Observable.fromCallable(new Callable<List<Tag>>() {
            @Override
            public List<Tag> call() throws Exception {
                return mAppDatabase.tagDao().checkAndInsertTags(tagTitles, transactionId);
            }
        });
    }

    // Transaction Module
    @Override
    public Observable<Transaction> getTransactionById(final long id) {
        return Observable.fromCallable(new Callable<Transaction>() {
            @Override
            public Transaction call() throws Exception {
                return mAppDatabase.transactionDao().getTransactionById(id);
            }
        });
    }

    @Override
    public Observable<Long> addTransaction(final Transaction transaction) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mAppDatabase.transactionDao().insertTransaction(transaction);
            }
        });
    }

    @Override
    public Observable<Boolean> addTransactionTag(final TransactionTags transactionTags) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.transactionTagsDao().addTransactionTag(transactionTags);
                return true;
            }
        });
    }

    // Home Module

    @Override
    public Observable<Double> getExpenseHeader(final long startDate, final long endDate) {
        return Observable.fromCallable(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return mAppDatabase.transactionDao().getExpensesByDate(startDate, endDate);
            }
        });
    }

    @Override
    public Observable<Double> getIncomeHeader(final long startDate, final long endDate) {
        return Observable.fromCallable(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return mAppDatabase.transactionDao().getIncomeByDate(startDate, endDate);
            }
        });
    }

    @Override
    public Observable<List<TransactionWithCategory>> getAllExpenseTransactionWithCategory(final long startDate, final long endDate) {
        return Observable.fromCallable(new Callable<List<TransactionWithCategory>>() {
            @Override
            public List<TransactionWithCategory> call() throws Exception {
                return mAppDatabase.transactionDao().getExpenseTransactionWithCategory(startDate, endDate);
            }
        });
    }

    @Override
    public Observable<List<TransactionWithCategory>> getAllIncomeTransactionWithCategory(final long startDate, final long endDate) {
        return Observable.fromCallable(new Callable<List<TransactionWithCategory>>() {
            @Override
            public List<TransactionWithCategory> call() throws Exception {
                return mAppDatabase.transactionDao().getIncomeTransactionWithCategory(startDate, endDate);
            }
        });
    }

    // Home Detail


    @Override
    public Observable<List<Tag>> getTagListByTransactionId(final long transactionId) {
        return Observable.fromCallable(new Callable<List<Tag>>() {
            @Override
            public List<Tag> call() throws Exception {
                return mAppDatabase.transactionTagsDao().getTagIdList(transactionId);
            }
        });
    }

    @Override
    public Observable<List<Transaction>> getTransactionByCategoryIdAndDate(final long categoryId, final long startDate, final long endDate) {
        return Observable.fromCallable(new Callable<List<Transaction>>() {
            @Override
            public List<Transaction> call() throws Exception {
                return mAppDatabase.transactionDao().getTransactionByDateAndCategoryId(categoryId, startDate, endDate);
            }
        });
    }

    @Override
    public Observable<Boolean> updateTransaction(final List<String> tagTitles, final Transaction transaction) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.transactionTagsDao().updateTransaction(tagTitles, transaction);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteTransactionById(final long transactionId) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.transactionDao().deleteTransactionById(transactionId);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteTransactionTagById(final long transactionId) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.transactionTagsDao().deleteTransactionTagById(transactionId);
                return true;
            }
        });
    }

    // History Module


    @Override
    public Observable<List<Transaction>> getAllTransactionUpToCurrentDate(final long startDate, final long endDate) {
        return Observable.fromCallable(new Callable<List<Transaction>>() {
            @Override
            public List<Transaction> call() throws Exception {
                return mAppDatabase.transactionDao().getAllTransactionUpToCurrentDate(startDate, endDate);
            }
        });
    }

    @Override
    public Observable<List<Long>> getSearchTransactionIDs(final String searchTag) {
        return Observable.fromCallable(new Callable<List<Long>>() {
            @Override
            public List<Long> call() throws Exception {
                return mAppDatabase.transactionTagsDao().getSearchTransactionId(searchTag);
            }
        });
    }

    @Override
    public Observable<List<Transaction>> getSearchTransactions(final List<Long> ids, final long startDate, final long endDate) {
        return Observable.fromCallable(new Callable<List<Transaction>>() {
            @Override
            public List<Transaction> call() throws Exception {
                return mAppDatabase.transactionDao().getSearchTransaction(ids, startDate, endDate);
            }
        });
    }

    // Recurrence Module

    @Override
    public Observable<List<RecurrenceTransactionCategory>> getAllRecurrenceTransactionCategory(final long startDate) {
        return Observable.fromCallable(new Callable<List<RecurrenceTransactionCategory>>() {
            @Override
            public List<RecurrenceTransactionCategory> call() throws Exception {
                return mAppDatabase.transactionDao().getRecurrenceTransactionCategory(startDate);
            }
        });
    }
}
