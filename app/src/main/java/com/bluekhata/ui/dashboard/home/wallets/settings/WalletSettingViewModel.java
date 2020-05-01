package com.bluekhata.ui.dashboard.home.wallets.settings;

import androidx.databinding.ObservableField;

import com.bluekhata.data.DataManager;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

public class WalletSettingViewModel extends BaseViewModel {
    private final ObservableField<String> walletTitle = new ObservableField<>("");

    public WalletSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ObservableField<String> getWalletTitle() {
        return walletTitle;
    }
}
