package com.everyrupee.ui.dashboard.transaction.datedialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DateOptionProvider {
    @ContributesAndroidInjector
    abstract DateOptionDialog provideDateOptionDialog();
}
