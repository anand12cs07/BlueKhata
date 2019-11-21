package com.everyrupee.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.everyrupee.data.model.db.Category;

import java.util.List;

@Dao
public abstract class CategoryDao {

    @Query("SELECT * FROM category_table WHERE catType = 0 ORDER BY catShortIndex")
    public abstract List<Category> getExpenses();

    @Query("SELECT * FROM category_table WHERE catType = 1 ORDER BY catShortIndex")
    public abstract List<Category> getIncomes();

    @Query("SELECT * FROM category_table")
    public abstract List<Category> getAllCategory();

    @Query("SELECT * FROM category_table WHERE catType = 0 AND catId = :catId")
    public abstract Category getExpenseById(long catId);

    @Query("SELECT * FROM category_table WHERE catType = 1 AND catId = :catId")
    public abstract Category getIncomeById(long catId);

    @Query("SELECT * FROM category_table WHERE catId = :catId")
    public abstract Category getCategoryById(long catId);

    @Query("SELECT COALESCE(MAX(catShortIndex),0) FROM category_table WHERE catType = 0")
    public abstract Integer getMaxExpenseShortIndex();

    @Query("SELECT COALESCE(MAX(catShortIndex),0) FROM category_table WHERE catType = 1")
    public abstract Integer getMaxIncomeShortIndex();

    @Query("UPDATE category_table SET catShortIndex = catShortIndex + 1 WHERE catShortIndex >= :toIndex AND catShortIndex < :fromIndex")
    public abstract void moveUpCategoryList(int fromIndex, int toIndex);

    @Query("UPDATE category_table SET catShortIndex = catShortIndex - 1 WHERE catShortIndex >= :toIndex AND catShortIndex > :fromIndex")
    public abstract void moveDownCategoryList(int fromIndex, int toIndex);

    @Query("UPDATE category_table SET catShortIndex = :toIndex WHERE catId = :id")
    public abstract void updateCategoryListIndex(int toIndex, long id);

    @Transaction
    public boolean dragCategoryList(int fromIndex, int toIndex, long id) {
        if (toIndex < fromIndex) {
            moveUpCategoryList(fromIndex, toIndex);
        } else {
            moveDownCategoryList(fromIndex, toIndex);
        }
        updateCategoryListIndex(toIndex, id);
        return true;
    }

    @Query("SELECT * FROM category_table")
    public abstract List<Category> getAllCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCategory(Category categoryItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCategoryList(List<Category> categoryList);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract int updateCategory(Category categoryItem);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateCategoryList(List<Category> categoryList);

    @Query("DELETE FROM category_table WHERE catId = :catId")
    public abstract void deleteCategoryById(long catId);

}
