package com.bluekhata.ui.dashboard.category.expense;

import com.bluekhata.di.PerExpenseCategoryFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ExpenseCategoryFragmentProvider {
    @PerExpenseCategoryFragment
    @ContributesAndroidInjector(modules = ExpenseCategoryFragmentModule.class)
    abstract ExpenseCategoryFragment provideExpenseCategoryFragment();
}
