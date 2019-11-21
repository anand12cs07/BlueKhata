package com.everyrupee.data.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "tag_table",
        indices = {@Index(value = {"tagTitle"}, unique = true)})
public class Tag implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "tagId")
    private long tagId;

    @ColumnInfo(name = "tagTitle")
    private String tagTitle;

    @Ignore
    public Tag(String tagTitle) {
        this.tagTitle = tagTitle;
    }

    public Tag(long tagId, String tagTitle) {
        this.tagId = tagId;
        this.tagTitle = tagTitle;
    }

    public long getTagId() {
        return tagId;
    }

    public String getTagTitle() {
        return tagTitle;
    }

    @Override
    public String toString() {
        return tagTitle;
    }

    public static ArrayList<Tag> getDefaultTags() {
        ArrayList<Tag> tags = new ArrayList<>();
        // Groceries

        // Transportation
        tags.add(new Tag("Diesel"));
        tags.add(new Tag("Petrol"));
        tags.add(new Tag("Cab"));
        tags.add(new Tag("Bicycle"));
        tags.add(new Tag("Taxi"));
        tags.add(new Tag("Metro"));
        tags.add(new Tag("Train"));
        tags.add(new Tag("Bus"));
        tags.add(new Tag("Car"));
        tags.add(new Tag("Parking"));

        // Health
        tags.add(new Tag("Gym"));
        tags.add(new Tag("Medicine"));

        // Kitchen
        tags.add(new Tag("Gas"));
        tags.add(new Tag("Oil"));
        tags.add(new Tag("Potato"));
        tags.add(new Tag("Tomato"));
        tags.add(new Tag("Onion"));
        tags.add(new Tag("Vegetable"));
        tags.add(new Tag("Chicken"));
        tags.add(new Tag("Rice"));
        tags.add(new Tag("Oats"));
        tags.add(new Tag("Flour"));

        // Investment
        tags.add(new Tag("Maid Salary"));
        tags.add(new Tag("Rent"));
        tags.add(new Tag("SIP"));
        tags.add(new Tag("Mutual Fund"));
        tags.add(new Tag("Taxes"));
        tags.add(new Tag("Gifts"));
        tags.add(new Tag("Salary"));

        // Entertainment
        tags.add(new Tag("Movies"));
        tags.add(new Tag("Musics"));
        tags.add(new Tag("Games"));
        tags.add(new Tag("Sports"));

        // Others
        tags.add(new Tag("Dry Cleaning"));
        tags.add(new Tag("Dinner"));

        return tags;
    }
}
