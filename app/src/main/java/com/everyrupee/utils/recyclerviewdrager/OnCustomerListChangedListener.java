package com.everyrupee.utils.recyclerviewdrager;

import com.everyrupee.data.model.db.Category;

import java.util.List;

/**
 * Created by aman on 04-09-2018.
 */

public interface OnCustomerListChangedListener {
    void onCategoryListChanged(List<Category> transactions);
    void onCategoryMove(int fromPosition, int toPosition, long categoryId);
}
