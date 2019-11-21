package com.everyrupee.data.model.db.custom;

import com.everyrupee.data.model.db.Category;

public class TransactionWithTagAndCategory {

    private TransactionWithTag transactionWithTag;
    private Category category;

    public TransactionWithTagAndCategory(TransactionWithTag transactionWithTag, Category category){
        this.transactionWithTag = transactionWithTag;
        this.category = category;
    }

    public TransactionWithTagAndCategory(TransactionWithTagAndCategory transactionWithTagAndCategory){
        TransactionWithTag transactionWithTag = new TransactionWithTag(
                transactionWithTagAndCategory.transactionWithTag.getTags(),
                transactionWithTagAndCategory.transactionWithTag.getTransaction()
        );
        this.transactionWithTag = transactionWithTag;
        this.category = transactionWithTagAndCategory.category;
    }

    public void setTransactionWithTag(TransactionWithTag transactionWithTag) {
        this.transactionWithTag = transactionWithTag;
    }

    public TransactionWithTag getTransactionWithTag() {
        return transactionWithTag;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }
}
