package com.bluekhata.ui.dashboard.history;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bluekhata.ui.dashboard.DashBoardActivity;
import com.bluekhata.ui.dashboard.history.filter.HistoryFilterBottomSheet;
import com.bluekhata.BR;
import com.bluekhata.R;
import com.bluekhata.ViewModelProviderFactory;
import com.bluekhata.data.model.db.custom.TransactionWithTagAndCategory;
import com.bluekhata.databinding.FragmentHistoryBinding;
import com.bluekhata.ui.base.BaseFragment;
import com.bluekhata.utils.LinearLayoutManagerWrapper;
import com.bluekhata.utils.PaginationScrollListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class HistoryFragment extends BaseFragment<FragmentHistoryBinding, HistoryViewModel> implements SearchView.OnQueryTextListener {
    public static final String TAG = HistoryFragment.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;

    @Inject
    LinearLayoutManagerWrapper layoutManager;

    @Inject
    DividerItemDecoration decoration;

    @Inject
    HistoryAdapter adapter;


    private FragmentHistoryBinding binding;
    private HistoryViewModel viewModel;

    private SearchView searchView;
    private long startDate;
    private long endDate;
    private List<Long> categoryIds;

    private boolean isFilterApplicable;
    private DashBoardActivity dashBoardActivity;
    private PaginationScrollListener paginationScrollListener;
    private HistoryFilterBottomSheet bottomSheetDialogFragment;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 3;
    private int PAGE_SIZE = 15;
    private int currentPage = PAGE_START;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public HistoryViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(HistoryViewModel.class);
        return viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();

        dashBoardActivity = ((DashBoardActivity) getActivity());
        dashBoardActivity.setTitle("History");

        setHasOptionsMenu(true);
        observeList();
        startDate = 0;
        endDate = new Date().getTime();
        categoryIds = new ArrayList<>();

        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setEmptyView(binding.imgEmptyHistory);
        binding.recyclerView.setAdapter(adapter);

        paginationScrollListener = getPaginationScrollListener();
        binding.recyclerView.addOnScrollListener(getPaginationScrollListener());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initScrollValues();
        viewModel.fetchHistoryList(categoryIds, startDate, endDate);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((DashBoardActivity) getActivity()).selectBottomNavigationMenu(R.id.bnv_history);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_history, menu);
        MenuItem searchViewItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setQueryHint("Search by Tag");
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                break;
            case R.id.filter:
                showBottomSheet();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showBottomSheet() {
        bottomSheetDialogFragment = new HistoryFilterBottomSheet();

        final long[] arr = new long[categoryIds.size()];
        int index = 0;
        for (final Long value : categoryIds) {
            arr[index++] = value;
        }
        Bundle bundle = new Bundle();
        bundle.putLongArray("categoryId", arr);
        bundle.putLong("startDate", startDate);
        bundle.putLong("endDate", endDate);
        bottomSheetDialogFragment.setArguments(bundle);
        bottomSheetDialogFragment.show(getChildFragmentManager(), "filter history");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()) {
            viewModel.fetchHistoryList(categoryIds, startDate, endDate);
        } else {
            viewModel.fetchSearchHistoryList(categoryIds, newText, startDate, endDate);
        }
        return false;
    }

    public void sendFilterData(long[] categoryIds, long startDate, long endDate) {
        this.categoryIds.clear();
        this.startDate = startDate;
        this.endDate = endDate;

        for (int i = 0; i < categoryIds.length; i++) {
            this.categoryIds.add(categoryIds[i]);
        }
        viewModel.fetchHistoryList(this.categoryIds, startDate, endDate);
    }

    private void observeList() {
        viewModel.getHistoryList().observe(this, new Observer<List<TransactionWithTagAndCategory>>() {
            @Override
            public void onChanged(@Nullable List<TransactionWithTagAndCategory> data) {
                TOTAL_PAGES = (int) Math.ceil((double) data.size() / PAGE_SIZE);
                adapter.setList(data);
                initScrollValues();
                adapter.setPageList();
                loadFirstPage();
            }
        });
    }

    private void initScrollValues() {
        isLoading = false;
        isLastPage = false;
        PAGE_SIZE = 15;
        currentPage = PAGE_START;
    }

    private PaginationScrollListener getPaginationScrollListener() {
        return paginationScrollListener = new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isSearchApplied() {
                return true;
            }
        };
    }

    private void loadFirstPage() {
        int size = getStartIndex();
        int index = getListLastIndex();
        List<TransactionWithTagAndCategory> firstList = adapter.getList().subList(size, index);
        adapter.addSubList(firstList);

        if (TOTAL_PAGES != 1 && adapter.getList().size() > 0) {
            adapter.addLoadingFooter();
        } else isLastPage = true;

    }

    private void loadNextPage() {
        int size = getStartIndex();
        int index = getListLastIndex();
        List<TransactionWithTagAndCategory> firstList = adapter.getList().subList(size, index);

        adapter.removeLoadingFooter();
        isLoading = false;

        adapter.addSubList(firstList);

        if (currentPage != TOTAL_PAGES - 1) {
            adapter.addLoadingFooter();
        } else isLastPage = true;
    }

    private int getStartIndex() {
        return currentPage == 0 ? 0 : PAGE_SIZE * currentPage;
    }

    private int getListLastIndex() {
        int value = PAGE_SIZE * (currentPage + 1);
        return adapter.getList().size() - 1 > value ? value : adapter.getList().size();
    }
}
