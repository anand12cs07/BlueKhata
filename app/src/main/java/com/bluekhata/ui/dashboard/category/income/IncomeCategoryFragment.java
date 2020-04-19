package com.bluekhata.ui.dashboard.category.income;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluekhata.data.model.db.Category;
import com.bluekhata.ui.dashboard.DashBoardActivity;
import com.bluekhata.ui.dashboard.category.CategoryListAdapter;
import com.bluekhata.utils.recyclerviewdrager.OnCustomerListChangedListener;
import com.bluekhata.utils.recyclerviewdrager.SimpleItemTouchHelperCallback;
import com.bluekhata.BR;
import com.bluekhata.R;
import com.bluekhata.ViewModelProviderFactory;
import com.bluekhata.databinding.FragmentCategoryIncomeBinding;
import com.bluekhata.di.dependency.ActivityDependency;
import com.bluekhata.di.dependency.AppDependency;
import com.bluekhata.di.dependency.FragmentDependency;
import com.bluekhata.di.dependency.IncomeCategoryDependency;
import com.bluekhata.ui.addcategory.AddCategoryActivity;
import com.bluekhata.ui.base.BaseFragment;
import com.bluekhata.utils.RecyclerViewEmptySupport;
import com.bluekhata.utils.recyclerviewdrager.OnStartDragListener;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class IncomeCategoryFragment extends BaseFragment<FragmentCategoryIncomeBinding, IncomeCategoryViewModel> implements OnCustomerListChangedListener,
        OnStartDragListener, View.OnClickListener {
    @Inject
    AppDependency appDependency; // same object from App

    @Inject
    ActivityDependency activityDependency; // same object from MainActivity

    @Inject
    FragmentDependency fragmentDependency; // same object from MainFragment

    @Inject
    IncomeCategoryDependency childFragmentDependency;

    @Named("IncomeDividerItemDecoration")
    @Inject
    DividerItemDecoration itemDecoration;
    @Named("IncomeLinearLayoutManager")
    @Inject
    LinearLayoutManager linearLayoutManager;
    @Named("IncomeCategoryAdapter")
    @Inject
    CategoryListAdapter adapter;

    @Inject
    ViewModelProviderFactory factory;

    private FloatingActionButton fab;
    private RecyclerViewEmptySupport recyclerView;
    private ItemTouchHelper mItemTouchHelper;

    private IncomeCategoryViewModel viewModel;
    private FragmentCategoryIncomeBinding categoryIncomeBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category_income;
    }

    @Override
    public IncomeCategoryViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(IncomeCategoryViewModel.class);
        return viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);
        categoryIncomeBinding = getViewDataBinding();

        attachViews();

        setHasOptionsMenu(true);

        setUpRecyclerView();

        setUpFabAnim();

        setUpSnackBarMessage();

        fab.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.fetchIncomeList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onCategoryListChanged(List<Category> transactions) {

    }

    @Override
    public void onCategoryListUpdate(List<Category> categoryList) {

    }

    @Override
    public void onCategoryMove(int fromPosition, int toPosition, long categoryId) {
//        Some issue on updating shortIndex

//        viewModel.updateDragCategoryList(fromPosition, toPosition, categoryId);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onClick(View view) {
//        if (viewModel.getIncomeListSize() >= 2){
//            Toast.makeText(getContext(),"Maximum number of Income created",Toast.LENGTH_LONG).show();
//            return;
//        }
        startActivity(AddCategoryActivity.newIntent(getContext(), "Income", -1));
    }

    private void setUpFabAnim() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.computeVerticalScrollRange() > recyclerView.getHeight()) {
                    if (!recyclerView.canScrollVertically(1)) {
                        ((DashBoardActivity) getActivity()).fabHide(fab);
                    } else {
                        ((DashBoardActivity) getActivity()).fabShow(fab);
                    }
                }
            }
        });
    }

    private void attachViews() {
        recyclerView = categoryIncomeBinding.recyclerView;
        fab = (FloatingActionButton) categoryIncomeBinding.fab;
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setEmptyView(categoryIncomeBinding.imgEmptyIncome);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.setDragStartListener(this);
        adapter.setListChangedListener(this);
        adapter.setTag(CategoryListAdapter.INCOME_CATEGORY);
    }

    private void setUpSnackBarMessage() {
        viewModel.getSnackBarMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Snackbar.make(categoryIncomeBinding.recyclerView, s, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
