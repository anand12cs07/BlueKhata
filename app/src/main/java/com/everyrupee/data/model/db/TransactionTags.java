package com.everyrupee.data.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "transaction_tag_table",
        foreignKeys = @ForeignKey(
                entity = Transaction.class,
                parentColumns = "transactionId",
                childColumns = "transactionID",
                onDelete = CASCADE)
)
public class TransactionTags {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @NonNull
    @ColumnInfo(name = "tagId")
    private long tagId;

    @ColumnInfo(name = "transactionID")
    private long transactionID;

    @Ignore
    public TransactionTags(long tagId, long transactionID) {
        this.transactionID = transactionID;
        this.tagId = tagId;
    }

    public TransactionTags(long id, long tagId, long transactionID) {
        this.id = id;
        this.tagId = tagId;
        this.transactionID = transactionID;
    }

    public long getId() {
        return id;
    }

    public long getTagId() {
        return tagId;
    }

    public long getTransactionID() {
        return transactionID;
    }
}
