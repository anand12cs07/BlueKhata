package com.bluekhata.ui.dashboard.home.homedetails;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluekhata.BR;
import com.bluekhata.R;
import com.bluekhata.ViewModelProviderFactory;
import com.bluekhata.data.model.db.custom.TransactionWithTag;
import com.bluekhata.databinding.FragmentTransactionDetailBinding;
import com.bluekhata.ui.base.BaseFragment;
import com.bluekhata.ui.dashboard.DashBoardActivity;
import com.bluekhata.ui.dashboard.transaction.TransactionActivity;
import com.bluekhata.ui.dashboard.RefreshListOnDismiss;
import com.bluekhata.utils.RecyclerViewEmptySupport;
import com.bluekhata.utils.RecyclerViewSwipeHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeDetailFragment extends BaseFragment<FragmentTransactionDetailBinding, HomeDetailViewModel>
        implements RefreshListOnDismiss {

    private static final String SELECTED_START_DATE = "selected start date";
    private static final String SELECTED_END_DATE = "selected end date";
    private static final String SELECTED_CATEGORY_ID = "category id";

    public static final String TAG = "HomeDetailFragment";

    @Inject
    ViewModelProviderFactory factory;

    @Inject
    HomeDetailAdapter adapter;

    @Inject
    LinearLayoutManager layoutManager;

    @Inject
    DividerItemDecoration itemDecoration;

    private HomeDetailViewModel viewModel;

    private FragmentTransactionDetailBinding detailBinding;

    private RefreshListOnDismiss listener;
    private DashBoardActivity dashBoardActivity;
    private RecyclerViewEmptySupport recyclerView;
    private RecyclerViewSwipeHelper swipeHelper;

    public static HomeDetailFragment getInstance(long startDate, long endDate, long categoryId) {
        HomeDetailFragment homeDetailFragment = new HomeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SELECTED_START_DATE, startDate);
        bundle.putSerializable(SELECTED_END_DATE, endDate);
        bundle.putLong(SELECTED_CATEGORY_ID, categoryId);
        homeDetailFragment.setArguments(bundle);
        return homeDetailFragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_transaction_detail;
    }

    @Override
    public HomeDetailViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(HomeDetailViewModel.class);
        return viewModel;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (RefreshListOnDismiss) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement HistoryFilterListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);
        detailBinding = getViewDataBinding();
        dashBoardActivity = ((DashBoardActivity) getActivity());

        setHasOptionsMenu(true);
        recyclerView = detailBinding.recyclerView;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setEmptyView(detailBinding.imgEmpty);
        recyclerView.setAdapter(adapter);
        setUpSwipeButtons();
        observeTitle();
        observeSnackBarMessage();
        observeCategoryDetail();

        viewModel.fetchCategoryDetail(
                getArguments().getLong(SELECTED_CATEGORY_ID),
                getArguments().getLong(SELECTED_START_DATE),
                getArguments().getLong(SELECTED_END_DATE)
        );
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.fetchCategoryTitle(getArguments().getLong(SELECTED_CATEGORY_ID));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onDismiss() {
//        viewModel.fetchCategoryDetail(
//                getArguments().getLong(SELECTED_CATEGORY_ID),
//                getArguments().getLong(SELECTED_START_DATE),
//                getArguments().getLong(SELECTED_END_DATE)
//        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dashBoardActivity.setTitle("");
        listener.onDismiss();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void setUpSwipeButtons() {
        swipeHelper = new RecyclerViewSwipeHelper(getContext(), recyclerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new RecyclerViewSwipeHelper.UnderlayButton(
                        getContext(),
                        "Delete",
                        0,
                        Color.parseColor("#ff0101"),
                        new RecyclerViewSwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                setAlertDialog(pos);
                            }
                        }
                ));

                underlayButtons.add(new RecyclerViewSwipeHelper.UnderlayButton(
                        getContext(),
                        "Edit",
                        0,
                        Color.parseColor("#16bd51"),
                        new RecyclerViewSwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Intent intent = new Intent(getContext(), TransactionActivity.class);
                                TransactionWithTag transactionWithTag = adapter.getList().get(pos);
                                intent.putExtra("transaction", transactionWithTag.getTransaction());
                                intent.putExtra("tags", new ArrayList<>(transactionWithTag.getTags()));
                                intent.putExtra("isToEdit", "update");
                                startActivityForResult(intent,1001);
//                                bottomSheetDialogFragment.setTransactionBottomSheetDismiss(HomeDetailFragment.this);
                            }
                        }
                ));
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            viewModel.fetchCategoryDetail(
                    getArguments().getLong(SELECTED_CATEGORY_ID),
                    getArguments().getLong(SELECTED_START_DATE),
                    getArguments().getLong(SELECTED_END_DATE)
            );
        }
    }

    private void setAlertDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Alert");
        builder.setMessage("Are you sure to delete this item?");

        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TransactionWithTag transactionWithTag = adapter.getList().get(position);
                viewModel.deleteTransaction(transactionWithTag.getTransaction().getTransactionId());
                adapter.deleteTransaction(position);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void observeTitle() {
        viewModel.getCategoryTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                dashBoardActivity.setTitle(s);
            }
        });
    }

    private void observeCategoryDetail() {
        viewModel.getCategoryDetail().observe(this, new Observer<List<TransactionWithTag>>() {
            @Override
            public void onChanged(@Nullable List<TransactionWithTag> list) {
                adapter.refreshList(list);
            }
        });
    }

    private void observeSnackBarMessage() {
        viewModel.getSnackBarMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Snackbar.make(detailBinding.getRoot(), s, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
