package com.everyrupee.ui.dashboard.home;


import android.animation.ObjectAnimator;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.everyrupee.ui.dashboard.RefreshListOnDismiss;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.everyrupee.BR;
import com.everyrupee.R;
import com.everyrupee.ViewModelProviderFactory;
import com.everyrupee.data.model.db.custom.TransactionWithCategory;
import com.everyrupee.databinding.FragmentHomeBinding;
import com.everyrupee.ui.base.BaseFragment;
import com.everyrupee.ui.dashboard.DashBoardActivity;
import com.everyrupee.ui.dashboard.transaction.TransactionBottomSheetDialog;
import com.everyrupee.utils.CalendarUtils;
import com.everyrupee.utils.CommonUtils;
import com.everyrupee.utils.horizontalcalendar.HorizontalCalendar;
import com.everyrupee.utils.horizontalcalendar.HorizontalCalendarListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;


public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel>
        implements View.OnClickListener, RefreshListOnDismiss {

    public static final String TAG = "HomeFragment";

    @Inject
    ViewModelProviderFactory factory;

    @Inject
    DividerItemDecoration itemDecoration;

    @Inject
    HomeAdapter expenseAdapter, incomeAdapter;

    private HomeViewModel viewModel;

    private boolean isScreenFirstLaunch = true;
    private FragmentHomeBinding homeBinding;
    private AppCompatSpinner spinner;
    private DashBoardActivity dashBoardActivity;
    private CircularProgressBar circularProgressBar;

    private HorizontalCalendar horizontalCalendarDate, horizontalCalendarMonth;
    private HorizontalCalendar.Builder builderDate, builderMonth;
    private TransactionBottomSheetDialog bottomSheetDialogFragment;

    private boolean monthCalenderStatus;
    private double expenses, incomes;

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
        spinner = dashBoardActivity.getSpinner();

        builderDate = new HorizontalCalendar.Builder(view, R.id.calendarViewDate);
        builderMonth = new HorizontalCalendar.Builder(view, R.id.calendarViewMonth);

        setUpHeader();
        setUpRecyclerView();
        setUpExpenseListObserver();
        setUpIncomeListObserver();

        homeBinding.fab.setOnClickListener(this);

        setUpFabAnim();
        setSpinnerData();
        setUpCalender();

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
    }

    @Override
    public void onClick(View view) {
        showBottomSheet();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        horizontalCalendarDate.setCalendarListener(null);
        horizontalCalendarMonth.setCalendarListener(null);
    }

    @Override
    public void onDismiss() {
        // on Transaction Bottom Sheet Dialog dismiss
        if (horizontalCalendarDate.isVisible()) {
            onDateCalenderSelectListener(horizontalCalendarDate.getSelectedDate(), isScreenFirstLaunch);
        } else {
            onMonthCalenderSelectListener(horizontalCalendarMonth.getSelectedDate(), isScreenFirstLaunch);
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

    private void showBottomSheet() {
        bottomSheetDialogFragment = new TransactionBottomSheetDialog();
        bottomSheetDialogFragment.show(getChildFragmentManager(), "add");
        bottomSheetDialogFragment.setTransactionBottomSheetDismiss(this);
    }

    private void setUpCalender() {
        horizontalCalendarDate = getCalenderView(builderDate, false);
        horizontalCalendarMonth = getCalenderView(builderMonth, true);

        horizontalCalendarDate.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                if (monthCalenderStatus) {
                    return;
                }
                onDateCalenderSelectListener(date, isScreenFirstLaunch);
            }

        });
        horizontalCalendarMonth.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                if (!monthCalenderStatus) {
                    return;
                }
                onMonthCalenderSelectListener(date, isScreenFirstLaunch);
            }

        });
    }

    private void setSpinnerData() {
        String[] data = {"By Date", "By Month"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_selected_item, data);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    monthCalenderStatus = false;
                    horizontalCalendarDate.show();
                    horizontalCalendarMonth.hide();
                    onDateCalenderSelectListener(Calendar.getInstance().getTime(), isScreenFirstLaunch);
                } else {
                    monthCalenderStatus = true;
                    horizontalCalendarDate.hide();
                    horizontalCalendarMonth.show();
                    onMonthCalenderSelectListener(Calendar.getInstance().getTime(), isScreenFirstLaunch);
                }
                isScreenFirstLaunch = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void onDateCalenderSelectListener(Date date, boolean isFirstLaunch) {
        if (isFirstLaunch) {
            return;
        }
        if (monthCalenderStatus) {
            horizontalCalendarDate.hide();
            horizontalCalendarMonth.show();
        } else {
            horizontalCalendarDate.show();
            horizontalCalendarMonth.hide();
        }

        long[] date1 = CalendarUtils.getStartAndEndTimeOfDay(date);
        viewModel.fetchExpenseList(date1[0], date1[1]);
        viewModel.fetchIncomeList(date1[0], date1[1]);
        viewModel.fetchHeaderExpenses(date1[0], date1[1]);
        viewModel.fetchHeaderIncome(date1[0], date1[1]);

        expenseAdapter.setSelectedDate(date1[0], date1[1]);
        incomeAdapter.setSelectedDate(date1[0], date1[1]);
    }

    private void onMonthCalenderSelectListener(Date date, boolean isFirstLaunch) {
        if (isFirstLaunch) {
            return;
        }
        if (monthCalenderStatus) {
            horizontalCalendarDate.hide();
            horizontalCalendarMonth.show();
        } else {
            horizontalCalendarDate.show();
            horizontalCalendarMonth.hide();
        }

        long[] date1 = CalendarUtils.getStartAndEndTimeOfMonth(date);
        viewModel.fetchExpenseList(date1[0], date1[1]);
        viewModel.fetchIncomeList(date1[0], date1[1]);
        viewModel.fetchHeaderExpenses(date1[0], date1[1]);
        viewModel.fetchHeaderIncome(date1[0], date1[1]);

        expenseAdapter.setSelectedDate(date1[0], date1[1]);
        incomeAdapter.setSelectedDate(date1[0], date1[1]);
    }

    private HorizontalCalendar getCalenderView(HorizontalCalendar.Builder builder, boolean isYearEnable) {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -10);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = builder
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .yearFormat("yyyy")
                .textSize(12f, 18f, 0.1f)
                .showDayName(true)
                .showMonthName(true)
                .showYearAndMonth(isYearEnable)
                .build();

        return horizontalCalendar;
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
}
