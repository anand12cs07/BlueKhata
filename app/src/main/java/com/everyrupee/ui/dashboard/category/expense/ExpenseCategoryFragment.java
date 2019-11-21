package com.everyrupee.ui.dashboard.category.expense;


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

import com.everyrupee.data.model.db.Category;
import com.everyrupee.ui.dashboard.DashBoardActivity;
import com.everyrupee.ui.dashboard.category.CategoryListAdapter;
import com.everyrupee.utils.recyclerviewdrager.OnCustomerListChangedListener;
import com.everyrupee.utils.recyclerviewdrager.OnStartDragListener;
import com.everyrupee.utils.recyclerviewdrager.SimpleItemTouchHelperCallback;
import com.everyrupee.BR;
import com.everyrupee.R;
import com.everyrupee.ViewModelProviderFactory;
import com.everyrupee.databinding.FragmentCategoryExpenseBinding;
import com.everyrupee.di.dependency.ActivityDependency;
import com.everyrupee.di.dependency.AppDependency;
import com.everyrupee.di.dependency.ExpenseCategoryDependency;
import com.everyrupee.di.dependency.FragmentDependency;
import com.everyrupee.ui.addcategory.AddCategoryActivity;
import com.everyrupee.ui.base.BaseFragment;
import com.everyrupee.utils.RecyclerViewEmptySupport;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class ExpenseCategoryFragment extends BaseFragment<FragmentCategoryExpenseBinding, ExpenseCategoryViewModel> implements OnCustomerListChangedListener,
        OnStartDragListener, View.OnClickListener {
    @Inject
    AppDependency appDependency; // same object from App

    @Inject
    ActivityDependency activityDependency; // same object from MainActivity

    @Inject
    FragmentDependency fragmentDependency; // same object from MainFragment

    @Inject
    ExpenseCategoryDependency expenseCategoryDependency;

    @Inject
    ViewModelProviderFactory factory;

    @Named("ExpenseDividerItemDecoration")
    @Inject
    DividerItemDecoration itemDecoration;
    @Named("ExpenseLinearLayoutManager")
    @Inject
    LinearLayoutManager linearLayoutManager;
    @Named("ExpenseCategoryAdapter")
    @Inject
    CategoryListAdapter adapter;

    private FloatingActionButton fab;
    private RecyclerViewEmptySupport recyclerView;
    private ItemTouchHelper mItemTouchHelper;

    private ExpenseCategoryViewModel viewModel;
    private FragmentCategoryExpenseBinding categoryExpenseBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category_expense;
    }

    @Override
    public ExpenseCategoryViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(ExpenseCategoryViewModel.class);
        return viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);
        categoryExpenseBinding = getViewDataBinding();

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
        viewModel.fetchExpenseList();
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
//        if (viewModel.getExpenseListSize() >= 6){
//            Toast.makeText(getContext(),"Maximum number of Expenses created",Toast.LENGTH_LONG).show();
//            return;
//        }
        startActivity(AddCategoryActivity.newIntent(getContext(), "Expense", -1));
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
        recyclerView = categoryExpenseBinding.recyclerView;
        fab = (FloatingActionButton) categoryExpenseBinding.fab;
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setEmptyView(categoryExpenseBinding.imgEmptyExpense);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.setDragStartListener(this);
        adapter.setListChangedListener(this);
        adapter.setTag(CategoryListAdapter.EXPENSE_CATEGORY);
    }

    private void setUpSnackBarMessage() {
        viewModel.getSnackBarMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Snackbar.make(categoryExpenseBinding.recyclerView, s, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
