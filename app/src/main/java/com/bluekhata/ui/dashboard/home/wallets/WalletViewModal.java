package com.bluekhata.ui.dashboard.home.wallets;

import com.bluekhata.data.DataManager;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

public class WalletViewModal extends BaseViewModel {
    public WalletViewModal(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
