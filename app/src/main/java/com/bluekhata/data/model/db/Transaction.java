package com.bluekhata.data.model.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "transaction_table")
public class Transaction implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long transactionId;

    @NonNull
    private double transactionAmount;

    @NonNull
    private Date transactionDate;

    private long categoryId;

    private long recurrenceId;

    private long reminderId;

    @Ignore
    public Transaction(double transactionAmount, Date transactionDate, long categoryId, long recurrenceId, long reminderId){
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.categoryId = categoryId;
        this.recurrenceId = recurrenceId;
        this.reminderId = reminderId;
    }

    public Transaction(long transactionId, double transactionAmount, Date transactionDate, long categoryId, long recurrenceId, long reminderId){
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.categoryId = categoryId;
        this.recurrenceId = recurrenceId;
        this.reminderId = reminderId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    @NonNull
    public Date getTransactionDate() {
        return transactionDate;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public long getRecurrenceId() {
        return recurrenceId;
    }

    public long getReminderId() {
        return reminderId;
    }
}
