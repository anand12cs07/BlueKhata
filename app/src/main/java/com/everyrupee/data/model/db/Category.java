package com.everyrupee.data.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "category_table")
public class Category {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "catId")
    private long catId;

    @ColumnInfo(name = "catTitle")
    private String catTitle;

    @ColumnInfo(name = "catColor")
    private String catColor;

    @ColumnInfo(name = "catIcon")
    private String catIcon;

    @ColumnInfo(name = "catType")
    private int catType;

    @ColumnInfo(name = "catShortIndex")
    private int catShortIndex;

    @Ignore
    public Category(String catTitle, String catColor, String catIcon, int catType, int catShortIndex) {
        this.catTitle = catTitle;
        this.catColor = catColor;
        this.catIcon = catIcon;
        this.catType = catType;
        this.catShortIndex = catShortIndex;
    }

    public Category(long catId, String catTitle, String catColor, String catIcon, int catType, int catShortIndex) {
        this.catId = catId;
        this.catTitle = catTitle;
        this.catColor = catColor;
        this.catIcon = catIcon;
        this.catType = catType;
        this.catShortIndex = catShortIndex;
    }

    public long getCatId() {
        return catId;
    }

    public String getCatTitle() {
        return catTitle;
    }

    public String getCatColor() {
        return catColor;
    }

    public String getCatIcon() {
        return catIcon;
    }

    public int getCatType() {
        return catType;
    }

    public int getCatShortIndex() {
        return catShortIndex;
    }

    public static List<Category> getCategoryList(){
        List<Category> categoryList = new ArrayList<>();

        // Expense List
        categoryList.add(new Category("Grocery","#1A8FE3","ic_grocery",0,1));
        categoryList.add(new Category("Electronics","#f6bd3a","ic_electronic",0,2));
        categoryList.add(new Category("Food","#e19c83","ic_foods",0,3));
        categoryList.add(new Category("Bills","#63b48c","ic_bill",0,4));
        categoryList.add(new Category("Transportation","#8EE3EF","ic_metro",0,5));
        categoryList.add(new Category("Home","#eb4132","ic_home",0,6));
        categoryList.add(new Category("Kitchen","#31e5b6","ic_kitchen",0,7));

        categoryList.add(new Category("Car","#A1887F","ic_car",0,8));
        categoryList.add(new Category("Entertainment","#ff8159","ic_leisure",0,9));
        categoryList.add(new Category("Clothing","#D7B14F","ic_cloth",0,10));
        categoryList.add(new Category("Tax","#78909C","ic_receipt",0,11));
        categoryList.add(new Category("Shopping","#CBFF4D","ic_shop",0,12));
        categoryList.add(new Category("Recharge","#08BDBD","ic_router",0,13));
        categoryList.add(new Category("Health","#6dc260","ic_health",0,14));

        categoryList.add(new Category("Gym","#1D70A2","ic_gym",0,15));
        categoryList.add(new Category("Pet","#18FFFF","ic_pet",0,16));
        categoryList.add(new Category("Vegetable","#f5e57a","ic_vegetable",0,17));
        categoryList.add(new Category("Travel","#d3ed38","ic_flight",0,18));
        categoryList.add(new Category("Education","#6170c4","ic_school",0,19));
        categoryList.add(new Category("Office","#e25988","ic_business",0,20));


        // Income List

        categoryList.add(new Category("Salary","#C2B97F","ic_money",1,1));
        categoryList.add(new Category("Gifts","#03dac5","ic_gift",1,2));
        categoryList.add(new Category("Awards","#f56156","ic_child_care",1,3));
        categoryList.add(new Category("Rental","#4FC3F7","ic_rental",1,4));
        categoryList.add(new Category("Investment","#ed9292","ic_budget",1,5));
        return categoryList;
    }
}
