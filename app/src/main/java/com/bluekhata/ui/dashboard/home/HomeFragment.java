package com.bluekhata.ui.dashboard.home;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bluekhata.BR;
import com.bluekhata.R;
import com.bluekhata.ViewModelProviderFactory;
import com.bluekhata.data.model.db.Category;
import com.bluekhata.data.model.db.custom.TransactionWithCategory;
import com.bluekhata.data.model.other.Product;
import com.bluekhata.databinding.FragmentHomeBinding;
import com.bluekhata.ui.base.BaseFragment;
import com.bluekhata.ui.category.CategoryAdapter;
import com.bluekhata.ui.dashboard.DashBoardActivity;
import com.bluekhata.ui.dashboard.RefreshListOnDismiss;
import com.bluekhata.ui.dashboard.home.calendarmodes.CalendarBottomSheetDialog;
import com.bluekhata.ui.dashboard.home.calendarmodes.ICalendarModeChangeListener;
import com.bluekhata.ui.dashboard.transaction.TransactionActivity;
import com.bluekhata.utils.CommonUtils;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.multicalenderview.HorizontalCalendar;
import com.multicalenderview.HorizontalCalendarListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static com.bluekhata.data.local.prefs.AppPreferencesHelper.PREF_KEY_MONTHLY_TYPE;
import static com.bluekhata.data.local.prefs.AppPreferencesHelper.PREF_KEY_WEEKLY_TYPE;
import static com.bluekhata.data.local.prefs.AppPreferencesHelper.PREF_KEY_YEARLY_TYPE;


public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel>
        implements View.OnClickListener, RefreshListOnDismiss, ICalendarModeChangeListener{

    public static final String TAG = "HomeFragment";

    @Inject
    ViewModelProviderFactory factory;

    @Inject
    DividerItemDecoration itemDecoration;

    @Inject
    HomeAdapter expenseAdapter, incomeAdapter;

    @Inject
    CategoryAdapter categoryAdapter;

    @Inject
    RecommendedProductAdapter recommendedProductAdapter;

    private HomeViewModel viewModel;

    private FragmentHomeBinding homeBinding;
    private DashBoardActivity dashBoardActivity;
    private CircularProgressBar circularProgressBar;

    private HorizontalCalendar multiHorizontalCalendar;
    private HorizontalCalendar.Builder builderMultiCalendar;

    private double expenses, incomes;
    private Calendar startDate, endDate;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);
        return viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);
        homeBinding = getViewDataBinding();

        circularProgressBar = homeBinding.progressBar;

        setHasOptionsMenu(true);

        dashBoardActivity = ((DashBoardActivity) getActivity());

        builderMultiCalendar = new HorizontalCalendar.Builder(view, R.id.multiCalendarView);

        setUpHeader();
        setUpRecyclerView();
        setUpExpenseListObserver();
        setUpIncomeListObserver();
        seUpRecommendedCategories();

        viewModel.fetchRecommendedCategories();
        homeBinding.fab.setOnClickListener(this);

        setUpFabAnim();
        setUpCalender();
        setUpRecommendedProducts();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dashBoardActivity.setTitle("");
        dashBoardActivity.selectBottomNavigationMenu(R.id.bnv_home);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_calendar:
                CalendarBottomSheetDialog dialog = new CalendarBottomSheetDialog();
                dialog.setModeChangeListener(this);
                dialog.show(getChildFragmentManager(), CalendarBottomSheetDialog.TAG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                showBottomSheet();
                break;
        }

    }

    @Override
    public void onCalendarModeChange(String mode) {
        multiHorizontalCalendar.refresh(mode, startDate.getTime(), endDate.getTime());
    }

    @Override
    public void onDismiss() {
        // on Transaction Bottom Sheet Dialog dismiss

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            Date date = multiHorizontalCalendar.getSelectedDate();
            onDateCalenderSelectListener(
                    multiHorizontalCalendar.getStartDate(date),
                    multiHorizontalCalendar.getEndDate(date)
            );
        }
    }

    private void setUpHeader() {
        viewModel.getHeaderExpense().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double aDouble) {
                expenses = aDouble;
                showProgress(0);
                homeBinding.tvExpense.setText(CommonUtils.getFormattedAmount(aDouble));
                homeBinding.tvBalance.setText(CommonUtils.getFormattedAmount(incomes - expenses));
            }
        });
        viewModel.getHeaderIncome().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double aDouble) {
                incomes = aDouble;
                showProgress(0);
                homeBinding.tvIncome.setText(CommonUtils.getFormattedAmount(aDouble));
                homeBinding.tvBalance.setText(CommonUtils.getFormattedAmount(incomes - expenses));
            }
        });
    }

    private void setUpExpenseListObserver() {
        viewModel.getExpenseWithCategory().observe(this, new Observer<List<TransactionWithCategory>>() {
            @Override
            public void onChanged(@Nullable List<TransactionWithCategory> transactionWithCategories) {
                expenseAdapter.refreshList(transactionWithCategories);
            }
        });
    }

    private void setUpIncomeListObserver() {
        viewModel.getIncomeWithCategory().observe(this, new Observer<List<TransactionWithCategory>>() {
            @Override
            public void onChanged(@Nullable List<TransactionWithCategory> transactionWithCategories) {
                incomeAdapter.refreshList(transactionWithCategories);
            }
        });
    }

    private void seUpRecommendedCategories() {
        viewModel.getCategoryMutableLiveData().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                categoryAdapter.refreshList(categories);
            }
        });
    }

    private void showBottomSheet() {
        Intent intent = new Intent(getContext(), TransactionActivity.class);
        intent.putExtra("isToEdit","add");
        startActivityForResult(intent,1001);
    }

    private void setUpCalender() {
        multiHorizontalCalendar = getMultiCalendarView(builderMultiCalendar);

        multiHorizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date startDate, Date endDate, int position) {
                onDateCalenderSelectListener(startDate, endDate);
            }
        });
    }

    private String getSelectedSavedMode(String mode) {
        switch (mode) {
            case PREF_KEY_WEEKLY_TYPE: return HorizontalCalendar.MODE_WEEKLY;
            case PREF_KEY_MONTHLY_TYPE: return HorizontalCalendar.MODE_MONTHLY;
            case PREF_KEY_YEARLY_TYPE: return HorizontalCalendar.MODE_YEARLY;
            default: return HorizontalCalendar.MODE_DAILY;
        }
    }

    private void onDateCalenderSelectListener(Date startDate, Date endDate) {
        Long[] date1 = new Long[]{startDate.getTime(), endDate.getTime()};

        viewModel.fetchExpenseList(date1[0], date1[1]);
        viewModel.fetchIncomeList(date1[0], date1[1]);
        viewModel.fetchHeaderExpenses(date1[0], date1[1]);
        viewModel.fetchHeaderIncome(date1[0], date1[1]);

        expenseAdapter.setSelectedDate(date1[0], date1[1]);
        incomeAdapter.setSelectedDate(date1[0], date1[1]);
    }

    private HorizontalCalendar getMultiCalendarView(HorizontalCalendar.Builder builder) {
        startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -10);
        endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        multiHorizontalCalendar = builderMultiCalendar
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .yearFormat("yyyy")
                .textSize(12f, 18f, 12.0f)
                .showDayName(false)
                .showMonthName(true)
                .showYearAndMonth(false)
                .setCalendarMode(HorizontalCalendar.MODE_DAILY)
                .build();

        return multiHorizontalCalendar;
    }

    private void setUpRecyclerView() {
        homeBinding.recyclerViewExpense.setLayoutManager(new LinearLayoutManager(getContext()));
        homeBinding.recyclerViewExpense.setEmptyView(homeBinding.imgEmptyExpense);
        homeBinding.recyclerViewExpense.addItemDecoration(itemDecoration);
        expenseAdapter = new HomeAdapter(getActivity());
        homeBinding.recyclerViewExpense.setAdapter(expenseAdapter);
        homeBinding.recyclerViewExpense.setNestedScrollingEnabled(false);

        homeBinding.recyclerViewIncome.setLayoutManager(new LinearLayoutManager(getContext()));
        homeBinding.recyclerViewIncome.setEmptyView(homeBinding.imgEmptyIncome);
        homeBinding.recyclerViewIncome.addItemDecoration(itemDecoration);
        incomeAdapter = new HomeAdapter(getActivity());
        homeBinding.recyclerViewIncome.setAdapter(incomeAdapter);
        homeBinding.recyclerViewIncome.setNestedScrollingEnabled(false);

        homeBinding.recyclerViewRecommendedCategory.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false)
        );
        homeBinding.recyclerViewRecommendedCategory.setAdapter(categoryAdapter);
        homeBinding.recyclerViewRecommendedCategory.setNestedScrollingEnabled(false);

        homeBinding.recyclerViewRecommendedProducts.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false)
        );
        homeBinding.recyclerViewRecommendedProducts.setAdapter(recommendedProductAdapter);
        homeBinding.recyclerViewRecommendedProducts.setNestedScrollingEnabled(false);
    }

    private void setUpFabAnim() {
        homeBinding.homeScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    Log.i(TAG, "BOTTOM SCROLL");
                    ((DashBoardActivity) getActivity()).fabHide(homeBinding.fab);
                } else {
                    ((DashBoardActivity) getActivity()).fabShow(homeBinding.fab);
                }
            }
        });
    }

    public void showProgress(float progress) {
        if (incomes != 0) {
            progress = (float) (((incomes - expenses) * 100.0) / incomes);
        }
//        circularProgressBar.setColor(incomes - expenses > 0 ? R.color.white : R.color.colorAccent);
        ObjectAnimator progressAnimator = ObjectAnimator.ofFloat(
                circularProgressBar, "progress", 0.0f, progress);

        progressAnimator.setDuration(1500);
        progressAnimator.start();
    }

    private void setUpRecommendedProducts(){
        List<Product> products = Product.getInitialProducts();
        recommendedProductAdapter.refreshList(products);
    }
}
