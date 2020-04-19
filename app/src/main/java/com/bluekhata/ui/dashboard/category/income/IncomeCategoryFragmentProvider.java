package com.bluekhata.ui.dashboard.category.income;

import com.bluekhata.di.PerIncomeCategoryFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class IncomeCategoryFragmentProvider {

    @PerIncomeCategoryFragment
    @ContributesAndroidInjector(modules = IncomeCategoryFragmentModule.class)
    abstract IncomeCategoryFragment provideIncomeCategoryFragment();
}
