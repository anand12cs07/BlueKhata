package com.everyrupee.ui.splash;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;

import com.everyrupee.ViewModelProviderFactory;
import com.everyrupee.ui.dashboard.DashBoardActivity;
import com.everyrupee.BR;
import com.everyrupee.R;
import com.everyrupee.databinding.ActivitySplashBinding;
import com.everyrupee.ui.base.BaseActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity<ActivitySplashBinding,SplashViewModel> {

    @Inject
    ViewModelProviderFactory factory;

    private SplashViewModel viewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this,factory).get(SplashViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel.startSeeding();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(DashBoardActivity.newIntent(SplashActivity.this));
                SplashActivity.this.finish();
            }
        },2000);
    }
}
