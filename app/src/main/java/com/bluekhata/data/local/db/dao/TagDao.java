package com.bluekhata.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
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
public abstract class TagDao {

    @Query("SELECT * FROM tag_table ORDER BY tagTitle")
    public abstract List<Tag> getTagList();

    @Query("SELECT * FROM tag_table WHERE tagId = :tagId")
    public abstract Tag getTagById(long tagId);

    @Query("SELECT * FROM tag_table WHERE tagTitle = :tagTitle")
    public abstract Tag getTagByTitle(String tagTitle);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public abstract long insetTag(Tag tag);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insetTagList(List<Tag> tags);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateTag(Tag tag);

    @Delete
    public abstract void deleteTag(Tag tag);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long addTransactionTag(TransactionTags transactionTags);

    @Transaction
    public List<Tag> checkAndInsertTags(List<String> tagTitles, long transactionId) {
        List<Tag> list = new ArrayList<>();
        for (String tagTitle : tagTitles) {
            Tag newTag = getTagByTitle(tagTitle);
            if (newTag == null) {
                newTag = new Tag(tagTitle);
                insetTag(newTag);
            }
            newTag = getTagByTitle(tagTitle);
            list.add(newTag);
            addTransactionTag(new TransactionTags(newTag.getTagId(),transactionId));
        }
        return list;
    }



}
