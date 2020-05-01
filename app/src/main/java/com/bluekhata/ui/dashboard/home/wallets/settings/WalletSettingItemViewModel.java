package com.bluekhata.ui.dashboard.home.wallets.settings;

import androidx.databinding.ObservableField;

import com.bluekhata.data.model.db.Wallet;

public class WalletSettingItemViewModel {
    private ObservableField<Boolean> isWalletEditable = new ObservableField<>(false);
    private ObservableField<Boolean> isWalletEditable1 = new ObservableField<>(false);
    private ObservableField<String> walletTitle;

    public WalletSettingItemViewModel(Wallet wallet){
        walletTitle = new ObservableField<>(wallet.getTitle());
    }

    public void onEditClick() {
        isWalletEditable1.set(!isWalletEditable1.get());
    }

    public void onDeleteClick(){

    }

    public ObservableField<String> getWalletTitle() {
        return walletTitle;
    }

    public void setIsWAlletEditable(Boolean isTagEditable) {
        this.isWalletEditable.set(isTagEditable);
    }

    public ObservableField<Boolean> getIsWalletEditable() {
        return isWalletEditable;
    }

    public void setIsWalletEditable1(Boolean isTagEditable1) {
        this.isWalletEditable1.set(isTagEditable1);
    }

    public ObservableField<Boolean> getIsWalletEditable1() {
        return isWalletEditable1;
    }
}
