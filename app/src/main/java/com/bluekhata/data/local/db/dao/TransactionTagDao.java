package com.bluekhata.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.bluekhata.data.model.db.Tag;
import com.bluekhata.data.model.db.TransactionTags;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class TransactionTagDao {

    @Query("Select * from tag_table WHERE tagId IN (Select tagId from transaction_tag_table WHERE transactionID = :transactionId)")
    public abstract List<Tag> getTagIdList(long transactionId);

    @Query("SELECT transactionId FROM transaction_tag_table WHERE tagId IN (Select tagId from tag_table WHERE tagTitle LIKE :searchTag)")
    public abstract List<Long> getSearchTransactionId(String searchTag);

    @Query("SELECT * FROM tag_table WHERE tagTitle = :tagTitle")
    public abstract Tag getTagByTitle(String tagTitle);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public abstract long insetTag(Tag tag);

    // Not in use
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addTransactionTag(TransactionTags transactionTags);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract int updateTransaction(com.bluekhata.data.model.db.Transaction transaction);

    @Query("DELETE FROM transaction_tag_table WHERE transactionID = :transactionId")
    public abstract void deleteTransactionTagById(long transactionId);

    @Transaction
    public void updateTransaction(List<String> tagTitles, com.bluekhata.data.model.db.Transaction transaction){
        deleteTransactionTagById(transaction.getTransactionId());

        List<Tag> list = new ArrayList<>();
        for (String tagTitle : tagTitles) {
            Tag newTag = getTagByTitle(tagTitle);
            if (newTag == null) {
                newTag = new Tag(tagTitle);
                insetTag(newTag);
            }
            newTag = getTagByTitle(tagTitle);
            list.add(newTag);
            addTransactionTag(new TransactionTags(newTag.getTagId(),transaction.getTransactionId()));
        }

        updateTransaction(transaction);
    }

}
