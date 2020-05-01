package com.bluekhata.ui.dashboard.budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.bluekhata.BR;
import com.bluekhata.R;
import com.bluekhata.ViewModelProviderFactory;
import com.bluekhata.databinding.ActivityBudgetBinding;
import com.bluekhata.ui.base.BaseActivity;

import javax.inject.Inject;

public class BudgetActivity extends BaseActivity<ActivityBudgetBinding,BudgetViewModel> {

    @Inject
    ViewModelProviderFactory factory;

    private ActivityBudgetBinding budgetBinding;
    private BudgetViewModel viewModel;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, BudgetActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_budget;
    }

    @Override
    public BudgetViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this,factory).get(BudgetViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        budgetBinding = getViewDataBinding();

        setSupportActionBar(budgetBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
