package com.everyrupee.ui.recursive;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;

import com.everyrupee.data.model.db.custom.RecurrenceDetail;
import com.everyrupee.ui.dashboard.transaction.TransactionBottomSheetDialog;
import com.everyrupee.ui.dashboard.RefreshListOnDismiss;
import com.everyrupee.utils.RecyclerViewSwipeHelper;
import com.everyrupee.BR;
import com.everyrupee.R;
import com.everyrupee.ViewModelProviderFactory;
import com.everyrupee.databinding.ActivityRecursiveTransactionBinding;
import com.everyrupee.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RecursiveTransactionActivity extends BaseActivity<ActivityRecursiveTransactionBinding, RecursiveActivityViewModel>
        implements RefreshListOnDismiss, HasSupportFragmentInjector {
    @Inject
    DividerItemDecoration dividerItemDecoration;
    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    RecursiveTransactionAdapter transactionAdapter;
    @Inject
    ViewModelProviderFactory factory;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private RecursiveActivityViewModel viewModel;
    private ActivityRecursiveTransactionBinding transactionBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, RecursiveTransactionActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recursive_transaction;
    }

    @Override
    public RecursiveActivityViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(RecursiveActivityViewModel.class);
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

        observeRecurrenceList();
        setUpSwipeButtons();

        viewModel.fetchRecurrenceDetail(new Date().getTime());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDismiss() {
        viewModel.fetchRecurrenceDetail(new Date().getTime());
    }

    private void setUpSwipeButtons() {
        new RecyclerViewSwipeHelper(this, transactionBinding.recyclerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new RecyclerViewSwipeHelper.UnderlayButton(
                        RecursiveTransactionActivity.this,
                        "Edit",
                        0,
                        Color.parseColor("#16bd51"),
                        new RecyclerViewSwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {

                                TransactionBottomSheetDialog bottomSheetDialogFragment =
                                        new TransactionBottomSheetDialog();

                                RecurrenceDetail recurrenceDetail = transactionAdapter.getList().get(pos);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("transaction", recurrenceDetail.getTransaction());
                                bundle.putSerializable("tags", new ArrayList<>(recurrenceDetail.getTagList()));
                                bottomSheetDialogFragment.setArguments(bundle);
                                bottomSheetDialogFragment.show(getSupportFragmentManager(), "update");
                                bottomSheetDialogFragment.setTransactionBottomSheetDismiss(RecursiveTransactionActivity.this);
                            }
                        }
                ));
            }
        };
    }

    private void observeRecurrenceList() {
        viewModel.getRecurrenceList().observe(this, new Observer<List<RecurrenceDetail>>() {
            @Override
            public void onChanged(@Nullable List<RecurrenceDetail> recurrenceDetails) {
                transactionAdapter.refreshList(recurrenceDetails);
            }
        });
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
