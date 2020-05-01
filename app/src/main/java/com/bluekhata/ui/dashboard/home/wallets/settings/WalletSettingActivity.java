package com.bluekhata.ui.dashboard.home.wallets.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;

import com.bluekhata.BR;
import com.bluekhata.R;
import com.bluekhata.ViewModelProviderFactory;
import com.bluekhata.databinding.ActivityWalletSettingBinding;
import com.bluekhata.ui.base.BaseActivity;

import javax.inject.Inject;

public class WalletSettingActivity extends BaseActivity<ActivityWalletSettingBinding,WalletSettingViewModel> {

    @Inject
    ViewModelProviderFactory factory;

    private ActivityWalletSettingBinding walletSettingBinding;
    private WalletSettingViewModel walletSettingViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_setting;
    }

    @Override
    public WalletSettingViewModel getViewModel() {
        walletSettingViewModel = ViewModelProviders.of(this,factory).get(WalletSettingViewModel.class);
        return walletSettingViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        walletSettingBinding = getViewDataBinding();

        setSupportActionBar(walletSettingBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
