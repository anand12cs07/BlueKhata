package com.bluekhata.di.builder;

import com.bluekhata.di.PerActivity;
import com.bluekhata.ui.addcategory.AddCategoryActivity;
import com.bluekhata.ui.addcategory.AddCategoryModule;
import com.bluekhata.ui.dashboard.DashBoardActivity;
import com.bluekhata.ui.dashboard.category.CategoryFragmentProvider;
import com.bluekhata.ui.dashboard.history.HistoryFragmentProvider;
import com.bluekhata.ui.dashboard.history.filter.HistoryBottomSheetProvider;
import com.bluekhata.ui.dashboard.home.HomeFragmentProvider;
import com.bluekhata.ui.dashboard.home.calendarmodes.CalendarBottomSheetProvider;
import com.bluekhata.ui.dashboard.home.homedetails.HomeDetailFragmentProvider;
import com.bluekhata.ui.dashboard.transaction.TransactionBottomSheetProvider;
import com.bluekhata.ui.dashboard.transaction.datedialog.DateOptionProvider;
import com.bluekhata.ui.recursive.RecursiveModule;
import com.bluekhata.ui.recursive.RecursiveTransactionActivity;
import com.bluekhata.ui.setting.SettingsActivity;
import com.bluekhata.ui.splash.SplashActivity;
import com.bluekhata.ui.tags.TagActivity;
import com.bluekhata.ui.tags.TagModule;
import com.bluekhata.ui.upcoming.UpcomingModule;
import com.bluekhata.ui.upcoming.UpcomingTransactionActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {
            CategoryFragmentProvider.class,
            HomeFragmentProvider.class,
            HomeDetailFragmentProvider.class,
            TransactionBottomSheetProvider.class,
            CalendarBottomSheetProvider.class,
            HistoryBottomSheetProvider.class,
            DateOptionProvider.class,
            HistoryFragmentProvider.class})
    abstract DashBoardActivity bindDashBoardActivity();

    @ContributesAndroidInjector(modules = AddCategoryModule.class)
    abstract AddCategoryActivity bindAddCategoryActivity();

    @ContributesAndroidInjector
    abstract SettingsActivity bindSettingActivity();

    @ContributesAndroidInjector(modules = TagModule.class)
    abstract TagActivity bindTagActivity();

    @ContributesAndroidInjector(modules = UpcomingModule.class)
    abstract UpcomingTransactionActivity bindUpcomingTransactionActivity();

    @ContributesAndroidInjector(modules = {
            TransactionBottomSheetProvider.class,
            DateOptionProvider.class,
            RecursiveModule.class})
    abstract RecursiveTransactionActivity bindRecursiveTransactionActivity();
}
