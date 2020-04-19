package com.bluekhata.ui.dashboard.category.expense;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluekhata.ui.dashboard.category.CategoryListAdapter;
import com.bluekhata.di.PerExpenseCategoryFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ExpenseCategoryFragmentModule {

    @Named("ExpenseCategoryAdapter")
    @Provides
    @PerExpenseCategoryFragment
    CategoryListAdapter provideCategoryListAdapter(){
        return new CategoryListAdapter();
    }

    @Named("ExpenseLinearLayoutManager")
    @Provides
    @PerExpenseCategoryFragment
    LinearLayoutManager provideLinearLayoutManager(ExpenseCategoryFragment fragment){
        return new LinearLayoutManager(fragment.getContext());
    }

    @Named("ExpenseDividerItemDecoration")
    @Provides
    @PerExpenseCategoryFragment
    DividerItemDecoration provideDividerItemDecoration(ExpenseCategoryFragment fragment){
        return new DividerItemDecoration(fragment.getContext(),DividerItemDecoration.VERTICAL);
    }
}
