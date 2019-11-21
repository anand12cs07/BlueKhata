package com.everyrupee.ui.setting;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.everyrupee.BR;
import com.everyrupee.R;
import com.everyrupee.ViewModelProviderFactory;
import com.everyrupee.databinding.ActivitySettingsBinding;
import com.everyrupee.ui.base.BaseActivity;

import javax.inject.Inject;

public class SettingsActivity extends BaseActivity<ActivitySettingsBinding, SettingActivityViewModel>
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    @Inject
    ViewModelProviderFactory factory;

    private ActivitySettingsBinding settingsBinding;
    private SettingActivityViewModel viewModel;
    private int REQUEST_CODE_ENABLE = 1;

    public static Intent newIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public SettingActivityViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(SettingActivityViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsBinding = getViewDataBinding();
        setSupportActionBar(settingsBinding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settingsBinding.tvBackUpNow.setOnClickListener(this);
        settingsBinding.tvChangePassCode.setOnClickListener(this);
        settingsBinding.layoutPasscode.setOnClickListener(this);
        settingsBinding.layoutDarkMode.setOnClickListener(this);

//        settingsBinding.switchPasscode.setChecked(prefManager.getPassCodeEnabled());
        settingsBinding.switchDarkMode.setChecked(viewModel.isDarkThemeEnabled());
//        tvChangePassCode.setVisibility(prefManager.getPassCodeEnabled() ? View.VISIBLE : View.GONE);

        settingsBinding.switchPasscode.setOnCheckedChangeListener(this);
        settingsBinding.switchDarkMode.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layoutDarkMode:
                settingsBinding.switchDarkMode.performClick();
                break;
            case R.id.layoutPasscode:
                settingsBinding.switchPasscode.performClick();
                break;
            case R.id.tvBackUpNow:
                break;
            case R.id.tvChangePassCode:
//                Intent intent = new Intent(this, CustomPinActivity.class);
//                intent.putExtra(AppLock.EXTRA_TYPE, AppLock.CHANGE_PIN);
//                startActivityForResult(intent, REQUEST_CODE_ENABLE);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.switchDarkMode:
                viewModel.onSwitchThemeCheckedChange(isChecked);
                finish();
                Intent intent1 = getIntent();
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);
                overridePendingTransition(0, 0);
                break;
            case R.id.switchPasscode:
//                if (isChecked) {
//                    Intent intent = new Intent(this, CustomPinActivity.class);
//                    intent.putExtra(AppLock.EXTRA_TYPE, AppLock.ENABLE_PINLOCK);
//                    startActivityForResult(intent, REQUEST_CODE_ENABLE);
//                    prefManager.setPassCodeEnabled(true);
//                } else {
//                    LockManager lockManager = LockManager.getInstance();
//                    lockManager.getAppLock().disableAndRemoveConfiguration();
//                    lockManager.getAppLock().setLogoId(R.mipmap.ic_launcher);
//                    lockManager.getAppLock().setShouldShowForgot(false);
//                    tvChangePassCode.setVisibility(View.GONE);
//                    prefManager.setPassCodeEnabled(false);
//                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_ENABLE) {
//            tvChangePassCode.setVisibility(View.VISIBLE);
//        }
    }
}
