package com.bluekhata.ui.dashboard.home.wallets;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class WalletBottomSheetProvider {
    @ContributesAndroidInjector
    abstract WalletBottomSheetDialog provideWalletBottomSheetDialog();
}
