package com.everyrupee.data.model.db.custom;

import androidx.room.Embedded;

import com.everyrupee.data.model.db.Category;
import com.everyrupee.data.model.db.Transaction;

public class RecurrenceTransactionCategory {

    @Embedded
    Transaction transaction;

    @Embedded
    Category category;

    public RecurrenceTransactionCategory(Transaction transaction, Category category){
        this.transaction = transaction;
        this.category = category;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Category getCategory() {
        return category;
    }
}
