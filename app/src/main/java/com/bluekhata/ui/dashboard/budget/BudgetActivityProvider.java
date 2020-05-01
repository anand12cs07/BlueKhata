package com.bluekhata.ui.dashboard.budget;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BudgetActivityProvider {
    @ContributesAndroidInjector
    abstract BudgetActivity provideBudgetActivity();
}
