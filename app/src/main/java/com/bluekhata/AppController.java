package com.bluekhata;

import android.app.Activity;

import androidx.multidex.MultiDexApplication;

import com.bluekhata.di.component.DaggerAppComponent;
import com.bluekhata.di.dependency.AppDependency;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


/**
 * Created by aman on 12-10-2018.
 */

public class AppController extends MultiDexApplication implements HasActivityInjector{
    @Inject
    AppDependency appDependency;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

}
