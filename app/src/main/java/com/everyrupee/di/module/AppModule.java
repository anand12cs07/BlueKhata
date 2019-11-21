package com.everyrupee.di.module;

import android.app.Application;
import androidx.room.Room;
import android.content.Context;

import com.everyrupee.data.AppDataManager;
import com.everyrupee.data.DataManager;
import com.everyrupee.data.local.db.AppDatabase;
import com.everyrupee.data.local.db.AppDbHelper;
import com.everyrupee.data.local.db.DbHelper;
import com.everyrupee.data.local.prefs.AppPreferencesHelper;
import com.everyrupee.data.local.prefs.PreferencesHelper;
import com.everyrupee.di.DatabaseInfo;
import com.everyrupee.di.PreferenceInfo;
import com.everyrupee.utils.AppConstants;
import com.everyrupee.utils.rx.AppSchedulerProvider;
import com.everyrupee.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).build();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
