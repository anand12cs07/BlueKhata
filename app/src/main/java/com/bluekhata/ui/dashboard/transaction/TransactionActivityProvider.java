package com.bluekhata.ui.dashboard.transaction;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class TransactionActivityProvider {
    @ContributesAndroidInjector
    abstract TransactionActivity provideTransactionBottomSheet();
}
