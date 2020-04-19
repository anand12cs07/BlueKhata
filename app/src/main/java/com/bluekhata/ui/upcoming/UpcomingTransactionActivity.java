package com.bluekhata.ui.upcoming;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.MenuItem;

import com.bluekhata.ViewModelProviderFactory;
import com.bluekhata.data.model.db.custom.UpcomingTransaction;
import com.bluekhata.ui.base.BaseActivity;
import com.bluekhata.BR;
import com.bluekhata.R;
import com.bluekhata.databinding.ActivityUpcomingTransactionBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class UpcomingTransactionActivity extends BaseActivity<ActivityUpcomingTransactionBinding, UpcomingActivityViewModel> {

    @Inject
    DividerItemDecoration dividerItemDecoration;
    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    UpcomingTransactionAdapter transactionAdapter;
    @Inject
    ViewModelProviderFactory factory;

    private UpcomingActivityViewModel viewModel;
    private ActivityUpcomingTransactionBinding transactionBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, UpcomingTransactionActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_upcoming_transaction;
    }

    @Override
    public UpcomingActivityViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(UpcomingActivityViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transactionBinding = getViewDataBinding();

        transactionBinding.recyclerView.setEmptyView(transactionBinding.imgEmpty);
        transactionBinding.recyclerView.setLayoutManager(layoutManager);
        transactionBinding.recyclerView.addItemDecoration(dividerItemDecoration);
        transactionBinding.recyclerView.setAdapter(transactionAdapter);

        setSupportActionBar(transactionBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        observeUpcomingTransaction();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);

        viewModel.fetchUpcomingList(new Date().getTime(), calendar.getTime().getTime());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void observeUpcomingTransaction() {
        viewModel.getUpcomingTransaction().observe(this, new Observer<List<UpcomingTransaction>>() {
            @Override
            public void onChanged(@Nullable List<UpcomingTransaction> upcomingTransactions) {
                transactionAdapter.refreshList(upcomingTransactions);
            }
        });
    }
}
