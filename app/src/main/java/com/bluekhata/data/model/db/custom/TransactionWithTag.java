package com.bluekhata.data.model.db.custom;

import android.text.TextUtils;

import com.bluekhata.data.model.db.Tag;
import com.bluekhata.data.model.db.Transaction;
import com.bluekhata.utils.CalendarUtils;
import com.bluekhata.utils.datasource.GenericDataSource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TransactionWithTag implements Serializable, GenericDataSource.AbstractValue {
    private Transaction transaction;
    private List<Tag> tagList;
    private boolean isHeader = false;

    public TransactionWithTag(List<Tag> tagList, Transaction transaction) {
        this.tagList = tagList;
        this.transaction = transaction;
    }

    public String getTagList() {
        return TextUtils.join(", ", tagList);
    }

    public List<Tag> getTags() {
        return tagList;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public boolean isEqualDate(Transaction compareTransaction) {
        return CalendarUtils.getDateInDdMmmYyyy(transaction.getTransactionDate())
                .equals(CalendarUtils.getDateInDdMmmYyyy(compareTransaction.getTransactionDate()));
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public static List<String> convertTagToTitle(List<Tag> tagList) {
        List<String> title = new ArrayList<>(tagList.size());
        for (Tag tag : tagList) {
            title.add(tag.getTagTitle());
        }
        return title;
    }
}
