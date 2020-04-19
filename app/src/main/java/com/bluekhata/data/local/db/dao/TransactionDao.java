package com.bluekhata.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bluekhata.data.model.db.Transaction;
import com.bluekhata.data.model.db.custom.RecurrenceTransactionCategory;
import com.bluekhata.data.model.db.custom.TransactionWithCategory;

import java.util.List;

@Dao
public interface TransactionDao {

    @Query("SELECT * FROM transaction_table WHERE transactionId = :transactionId")
    Transaction getTransactionById(long transactionId);

    @Query("Select transaction_table.*, category_table.*, COUNT(*) AS transactionCount, " +
            "COALESCE(SUM(transactionAmount),0) AS sumOfAmount FROM transaction_table INNER JOIN category_table " +
            " ON catId = categoryId WHERE catType = 0 AND transactionDate BETWEEN :startDate AND :endDate GROUP BY " +
            "catId ORDER BY SUM(transactionAmount) DESC")
    List<TransactionWithCategory> getExpenseTransactionWithCategory(long startDate, long endDate);

    @Query("Select transaction_table.*, category_table.*, COUNT(*) AS transactionCount, " +
            "COALESCE(SUM(transactionAmount),0) AS sumOfAmount FROM transaction_table INNER JOIN category_table " +
            " ON catId = categoryId WHERE catType = 1 AND transactionDate BETWEEN :startDate AND :endDate GROUP BY " +
            "catId ORDER BY SUM(transactionAmount) DESC")
    List<TransactionWithCategory> getIncomeTransactionWithCategory(long startDate, long endDate);

    @Query("Select COALESCE(SUM(transactionAmount),0) FROM transaction_table INNER JOIN category_table" +
            " ON catId = categoryId WHERE catType = 0 AND transactionDate BETWEEN :startDate AND :endDate")
    Double getExpensesByDate(long startDate, long endDate);

    @Query("Select COALESCE(SUM(transactionAmount),0) FROM transaction_table INNER JOIN category_table" +
            " ON catId = categoryId WHERE catType = 1 AND transactionDate BETWEEN :startDate AND :endDate")
    Double getIncomeByDate(long startDate, long endDate);

    @Query("Select * FROM transaction_table WHERE categoryId = :categoryId AND transactionDate BETWEEN :startDate AND :endDate " +
            " ORDER BY transactionAmount DESC")
    List<Transaction> getTransactionByDateAndCategoryId(long categoryId, long startDate, long endDate);

    @Query("Select * FROM transaction_table WHERE transactionDate BETWEEN :startDate AND :endDate " +
            " ORDER BY transactionDate DESC")
    List<Transaction> getAllTransactionUpToCurrentDate(long startDate, long endDate);

    @Query("Select * FROM transaction_table WHERE transactionId IN (:transactionIds) AND " +
            "transactionDate BETWEEN :startDate AND :endDate ORDER BY transactionDate DESC")
    List<Transaction> getSearchTransaction(List<Long> transactionIds, long startDate, long endDate);

    @Query("SELECT transaction_table.*, category_table.*  FROM transaction_table INNER JOIN " +
            "category_table ON categoryId = catId WHERE transactionDate >= :startDate")
    List<RecurrenceTransactionCategory> getRecurrenceTransactionCategory(long startDate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTransaction(Transaction transaction);

    @Query("DELETE FROM transaction_table WHERE transactionId = :transactionID")
    void deleteTransactionById(long transactionID);

    @Query("DELETE FROM transaction_table WHERE categoryId = :categoryID")
    void deleteTransactionByCategoryId(long categoryID);
}
