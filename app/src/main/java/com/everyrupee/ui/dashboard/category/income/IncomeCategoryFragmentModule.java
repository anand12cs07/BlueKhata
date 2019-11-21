package com.everyrupee.ui.dashboard.category.income;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.everyrupee.ui.dashboard.category.CategoryListAdapter;
import com.everyrupee.di.PerIncomeCategoryFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class IncomeCategoryFragmentModule {

    @Named("IncomeCategoryAdapter")
    @Provides
    @PerIncomeCategoryFragment
    CategoryListAdapter provideCategoryListAdapter(){
        return new CategoryListAdapter();
    }

    @Named("IncomeLinearLayoutManager")
    @Provides
    @PerIncomeCategoryFragment
    LinearLayoutManager provideLinearLayoutManager(IncomeCategoryFragment fragment){
        return new LinearLayoutManager(fragment.getContext());
    }

    @Named("IncomeDividerItemDecoration")
    @Provides
    @PerIncomeCategoryFragment
    DividerItemDecoration provideDividerItemDecoration(IncomeCategoryFragment fragment){
        return new DividerItemDecoration(fragment.getContext(),DividerItemDecoration.VERTICAL);
    }
}
