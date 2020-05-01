package com.bluekhata.ui.dashboard.home.wallets;

import com.bluekhata.data.model.db.Wallet;

public interface IWalletChangeListener {
    public void onWalletModeChange(Wallet wallet);
}
