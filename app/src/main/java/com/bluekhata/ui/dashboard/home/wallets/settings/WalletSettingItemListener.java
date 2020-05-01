package com.bluekhata.ui.dashboard.home.wallets.settings;

import com.bluekhata.data.model.db.Tag;
import com.bluekhata.data.model.db.Wallet;

public interface WalletSettingItemListener {
    void onEdit(Wallet wallet);
    void onDelete(Wallet wallet);
}
