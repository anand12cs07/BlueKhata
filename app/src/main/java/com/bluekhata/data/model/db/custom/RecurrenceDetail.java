package com.bluekhata.data.model.db.custom;

import android.text.TextUtils;

import com.bluekhata.data.model.db.Category;
import com.bluekhata.data.model.db.Recurrence;
import com.bluekhata.data.model.db.Tag;
import com.bluekhata.data.model.db.Transaction;

import java.util.ArrayList;
import java.util.List;

public class RecurrenceDetail {

    private Transaction transaction;

    private Category category;

    private Recurrence recurrence;

    private List<Tag> tagList;

    public RecurrenceDetail(){
        tagList = new ArrayList<>();
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public String getTagString(){
        return TextUtils.join(", ",tagList);
    }
}
