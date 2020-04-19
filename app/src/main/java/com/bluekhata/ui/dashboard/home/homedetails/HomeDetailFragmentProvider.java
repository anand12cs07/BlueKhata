package com.bluekhata.ui.dashboard.home.homedetails;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeDetailFragmentProvider {
    @ContributesAndroidInjector(modules = HomeDetailFragmentModule.class)
    abstract HomeDetailFragment provideHomeDetailFactory();
}
