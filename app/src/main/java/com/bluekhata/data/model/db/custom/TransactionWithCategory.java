package com.bluekhata.data.model.db.custom;

import androidx.room.Embedded;

import com.bluekhata.data.model.db.Category;
import com.bluekhata.data.model.db.Transaction;

public class TransactionWithCategory {
    @Embedded
    Transaction transaction;

    @Embedded
    Category category;

    private int transactionCount;

    private double sumOfAmount;

    public TransactionWithCategory(Transaction transaction, Category category, int transactionCount, double sumOfAmount){
        this.transaction = transaction;
        this.category = category;
        this.transactionCount = transactionCount;
        this.sumOfAmount = sumOfAmount;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Category getCategory() {
        return category;
    }

    public double getSumOfAmount() {
        return sumOfAmount;
    }

    public int getTransactionCount() {
        return transactionCount;
    }
}
