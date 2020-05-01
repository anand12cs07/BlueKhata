package com.bluekhata.ui.dashboard.home.calendarmodes;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CalendarBottomSheetProvider {
    @ContributesAndroidInjector
    abstract CalendarBottomSheetDialog provideCalendarBottomSheetDialog();
}
