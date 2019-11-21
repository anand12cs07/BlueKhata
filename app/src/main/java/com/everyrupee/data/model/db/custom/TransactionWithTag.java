package com.everyrupee.data.model.db.custom;

import android.text.TextUtils;

import com.everyrupee.data.model.db.Tag;
import com.everyrupee.data.model.db.Transaction;
import com.everyrupee.utils.CalendarUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TransactionWithTag implements Serializable {
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
