package com.everyrupee.ui.dashboard.history.filter;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class HistoryBottomSheetProvider {
    @ContributesAndroidInjector(modules = HistoryFilterModule.class)
    abstract HistoryFilterBottomSheet provideHistoryBottomSheet();
}
