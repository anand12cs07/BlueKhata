package com.everyrupee.ui.dashboard.transaction;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class TransactionBottomSheetProvider {
    @ContributesAndroidInjector
    abstract TransactionBottomSheetDialog provideTransactionBottomSheet();
}
