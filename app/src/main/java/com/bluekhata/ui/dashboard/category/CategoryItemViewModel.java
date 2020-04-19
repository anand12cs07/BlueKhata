package com.bluekhata.ui.dashboard.category;

import androidx.databinding.ObservableField;

import com.bluekhata.data.model.db.Category;

public class CategoryItemViewModel {

    public final ObservableField<String> categoryTitle;

    public final ObservableField<String> categoryIcon;

    public final ObservableField<String> categoryColor;

    public CategoryItemViewModel(Category category){
        categoryTitle = new ObservableField<>(category.getCatTitle());
        categoryIcon = new ObservableField<>(category.getCatIcon());
        categoryColor = new ObservableField<>(category.getCatColor());
    }
}
