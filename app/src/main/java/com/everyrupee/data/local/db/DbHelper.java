package com.everyrupee.data.local.db;

import com.everyrupee.data.model.db.Category;
import com.everyrupee.data.model.db.Recurrence;
import com.everyrupee.data.model.db.Tag;
import com.everyrupee.data.model.db.Reminder;
import com.everyrupee.data.model.db.Transaction;
import com.everyrupee.data.model.db.TransactionTags;
import com.everyrupee.data.model.db.custom.RecurrenceTransactionCategory;
import com.everyrupee.data.model.db.custom.TransactionWithCategory;

import java.util.List;

import io.reactivex.Observable;

public interface DbHelper {
    // Category Module
    Observable<Category> getCategory(long categoryId);
    Observable<List<Category>> getCategoryExpenses();
    Observable<List<Category>> getCategoryIncomes();
    Observable<List<Category>> getAllCategory();
    Observable<Integer> getMaxExpenseShortIndex();
    Observable<Integer> getMaxIncomeShortIndex();
    Observable<Boolean> updateDragCategoryListShortIndex(int fromIndex, int toIndex, long id);

    // Add Edit Category Module
    Observable<Boolean> isCategoryEmpty();
    Observable<Boolean> saveCategoryList(List<Category> categoryList);
    Observable<Boolean> insertCategory(Category category);
    Observable<Boolean> updateCategory(Category category);
    Observable<Boolean> deleteCategory(long categoryId);
    Observable<Boolean> deleteTransactionByCategoryId(long categoryId);

    // Reminder Module
    Observable<Boolean> isReminderEmpty();
    Observable<List<Reminder>> loadAllReminders();
    Observable<Reminder> getReminderById(long id);
    Observable<Reminder> getReminderByTitle(String title);
    Observable<Boolean> saveReminderList(List<Reminder> reminders);

    // Recurrence Module
    Observable<Boolean> isRecurrenceEmpty();
    Observable<List<Recurrence>> loadAllRecurrence();
    Observable<Recurrence> getRecurrenceById(long id);
    Observable<Recurrence> getRecurrenceByTitle(String title);
    Observable<Boolean> saveRecurrenceList(List<Recurrence> recurrences);

    // Tag Module
    Observable<Boolean> isTagEmpty();
    Observable<List<Tag>> loadAllTags();
    Observable<Tag> getTagById(long id);
    Observable<Tag> getTagByTitle(String title);
    Observable<Boolean> saveTagList(List<Tag> tags);
    Observable<Long> addTag(Tag tag);
    Observable<Boolean> updateTag(Tag tag);
    Observable<Boolean> deleteTag(Tag tag);
    Observable<List<Tag>> insertTransactionTag(List<String> tagTitles, long transactionId);

    // Transaction Module
    Observable<Transaction> getTransactionById(long id);
    Observable<Long> addTransaction(Transaction transaction);
    Observable<Boolean> addTransactionTag(TransactionTags transactionTags);

    // Home Module
    Observable<Double> getIncomeHeader(long startDate, long endDate);
    Observable<Double> getExpenseHeader(long startDate, long endDate);
//    Observable<Double> getBalanceHeader(long startDate, long endDate);
    Observable<List<TransactionWithCategory>> getAllExpenseTransactionWithCategory(long startDate, long endDate);
    Observable<List<TransactionWithCategory>> getAllIncomeTransactionWithCategory(long startDate, long endDate);

    // Home Detail Module
    Observable<List<Tag>> getTagListByTransactionId(long transactionId);
    Observable<Boolean> deleteTransactionTagById(long transactionId);
    Observable<Boolean> deleteTransactionById(long transactionId);
    Observable<Boolean> updateTransaction(List<String> tagTitles, Transaction transaction);
    Observable<List<Transaction>> getTransactionByCategoryIdAndDate(long categoryId, long startDate, long endDate);

    // History Module
    Observable<List<Transaction>> getAllTransactionUpToCurrentDate(long startDate, long endDate);
    Observable<List<Long>> getSearchTransactionIDs(String searchTag);
    Observable<List<Transaction>> getSearchTransactions(List<Long> ids, long startDate, long endDate);

    // Recurrence Module
    Observable<List<RecurrenceTransactionCategory>> getAllRecurrenceTransactionCategory(long startDate);
}
