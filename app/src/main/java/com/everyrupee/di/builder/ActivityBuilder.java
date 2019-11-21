package com.everyrupee.di.builder;

import com.everyrupee.di.PerActivity;
import com.everyrupee.ui.addcategory.AddCategoryActivity;
import com.everyrupee.ui.addcategory.AddCategoryModule;
import com.everyrupee.ui.dashboard.DashBoardActivity;
import com.everyrupee.ui.dashboard.category.CategoryFragmentProvider;
import com.everyrupee.ui.dashboard.history.HistoryFragmentProvider;
import com.everyrupee.ui.dashboard.history.filter.HistoryBottomSheetProvider;
import com.everyrupee.ui.dashboard.home.HomeFragmentProvider;
import com.everyrupee.ui.dashboard.home.homedetails.HomeDetailFragmentProvider;
import com.everyrupee.ui.dashboard.transaction.TransactionBottomSheetProvider;
import com.everyrupee.ui.dashboard.transaction.datedialog.DateOptionProvider;
import com.everyrupee.ui.recursive.RecursiveModule;
import com.everyrupee.ui.recursive.RecursiveTransactionActivity;
import com.everyrupee.ui.setting.SettingsActivity;
import com.everyrupee.ui.splash.SplashActivity;
import com.everyrupee.ui.upcoming.UpcomingModule;
import com.everyrupee.ui.tags.TagActivity;
import com.everyrupee.ui.tags.TagModule;
import com.everyrupee.ui.upcoming.UpcomingTransactionActivity;

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
