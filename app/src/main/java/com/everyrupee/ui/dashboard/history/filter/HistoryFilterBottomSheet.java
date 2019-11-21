package com.everyrupee.ui.dashboard.history.filter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.everyrupee.BR;
import com.everyrupee.R;
import com.everyrupee.ViewModelProviderFactory;
import com.everyrupee.data.model.db.Category;
import com.everyrupee.databinding.BottomsheetHistoryFilterBinding;
import com.everyrupee.ui.base.BaseBottomSheetDialog;
import com.everyrupee.utils.CalendarUtils;
import com.everyrupee.utils.LinearLayoutManagerWrapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by aman on 21-09-2018.
 */

public class HistoryFilterBottomSheet extends BaseBottomSheetDialog<BottomsheetHistoryFilterBinding, HistoryFilterViewModel>
        implements HistoryFilterListener.OnAllCheckedListener, RadioGroup.OnCheckedChangeListener,
        CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    @Inject
    LinearLayoutManagerWrapper layoutManagerWrapper;

    @Inject
    DividerItemDecoration decoration;

    @Inject
    HistoryFilterAdapter adapter;

    @Inject
    ViewModelProviderFactory factory;

    private HistoryFilterViewModel viewModel;
    private BottomsheetHistoryFilterBinding mViewDataBinding;

    private boolean isClear;
    private boolean isStartDateSelected;
    private Calendar startCalendar, endCalendar;
    private DatePickerDialog startDatePickerDialog, endDatePickerDialog;
    private HistoryFilterListener listener;

    private DatePickerDialog.OnDateSetListener startDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            startCalendar = Calendar.getInstance();
            startCalendar.set(i, i1, i2);
            startCalendar.set(Calendar.HOUR_OF_DAY, 0);
            startCalendar.set(Calendar.MINUTE, 0);
            startCalendar.set(Calendar.SECOND, 0);

            isStartDateSelected = true;

            mViewDataBinding.tvFrom.setText(CalendarUtils.getDateInDdMmmYyyy(startCalendar.getTime()));
            startDatePickerDialog.cancel();

        }
    };

    private DatePickerDialog.OnDateSetListener endDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            endCalendar = Calendar.getInstance();
            endCalendar.set(i, i1, i2);

            mViewDataBinding.tvTo.setText(CalendarUtils.getDateInDdMmmYyyy(endCalendar.getTime()));
            endDatePickerDialog.cancel();

        }
    };

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.bottomsheet_history_filter;
    }

    @Override
    public HistoryFilterViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(HistoryFilterViewModel.class);
        return viewModel;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View touchOutsideView = getDialog().getWindow()
                .getDecorView()
                .findViewById(R.id.touch_outside);
        touchOutsideView.setOnClickListener(null);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        try {
            listener = (HistoryFilterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement HistoryFilterListener");
        }
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        mViewDataBinding = getViewDataBinding();

        observeCategoryList();
        setUpCalendar();

        if (getStartDate() != 0){
            isStartDateSelected = true;
            startCalendar.setTimeInMillis(getStartDate());
            endCalendar.setTimeInMillis(getEndDate());

            mViewDataBinding.tvFrom.setText(CalendarUtils.getDateInDdMmmYyyy(new Date(getStartDate())));
            mViewDataBinding.tvTo.setText(CalendarUtils.getDateInDdMmmYyyy(new Date(getEndDate())));
        }

        mViewDataBinding.recyclerView.addItemDecoration(decoration);
        mViewDataBinding.recyclerView.setLayoutManager(layoutManagerWrapper);
        mViewDataBinding.recyclerView.setEmptyView(mViewDataBinding.imgEmptyHistory);
        mViewDataBinding.recyclerView.setAdapter(adapter);
        adapter.setAllCheckedListener(this);

        mViewDataBinding.tvFrom.setOnClickListener(this);
        mViewDataBinding.tvTo.setOnClickListener(this);
        mViewDataBinding.btnOk.setOnClickListener(this);
        mViewDataBinding.btnClear.setOnClickListener(this);
        mViewDataBinding.rdgType.setOnCheckedChangeListener(this);
        mViewDataBinding.chk.setOnCheckedChangeListener(this);
        viewModel.fetchAllCategory();

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rd_all:
                viewModel.fetchAllCategory();
                break;
            case R.id.rd_income:
                viewModel.fetchIncomeList();
                break;
            case R.id.rd_expense:
                viewModel.fetchExpenseList();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
        adapter.setAllCategory(check);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_from:
                startDatePickerDialog.show();
                break;
            case R.id.tv_to:
                endDatePickerDialog.show();
                break;
            case R.id.btn_ok:
                if (adapter.getCategoryIdList().length > 0) {
                    isClear = false;
                    listener.sendFilterData(
                            adapter.getCategoryIdList(),
                            isStartDateSelected ? startCalendar.getTime().getTime() : 0,
                            endCalendar.getTime().getTime());
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Category not selected", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_clear:
                isClear = true;
                startCalendar.setTimeInMillis(0);
                endCalendar.setTimeInMillis(new Date().getTime());

                mViewDataBinding.tvFrom.setText("DD/MMM/YYYY");
                mViewDataBinding.tvTo.setText("DD/MMM/YYYY");

                mViewDataBinding.rdgType.setOnCheckedChangeListener(null);
                mViewDataBinding.rdgType.check(R.id.rd_all);
                mViewDataBinding.rdgType.setOnCheckedChangeListener(this);

                viewModel.fetchAllCategory();
                break;
        }
    }

    @Override
    public void onAllCheckedItem(boolean isAllSelected) {
        mViewDataBinding.chk.setOnCheckedChangeListener(null);
        mViewDataBinding.chk.setChecked(isAllSelected);
        mViewDataBinding.chk.setOnCheckedChangeListener(this);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (adapter.getCategoryIdList().length > 0) {
            super.onDismiss(dialog);
        }else {
            Toast.makeText(getContext(), "Clear filter", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    private void observeCategoryList() {
        viewModel.getCategoryList().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                adapter.refreshList(categories, getCategoryIdList(),isClear);

                mViewDataBinding.chk.setOnCheckedChangeListener(null);
                mViewDataBinding.chk.setChecked(isAllItemSelected(categories));
                mViewDataBinding.chk.setOnCheckedChangeListener(HistoryFilterBottomSheet.this);
            }
        });
    }

    private long[] getCategoryIdList(){
        return getArguments().getLongArray("categoryId");
    }

    private long getStartDate(){
        return getArguments().getLong("startDate");
    }

    private long getEndDate(){
        return getArguments().getLong("endDate");
    }

    private boolean isAllItemSelected(List<Category> categories) {
        if (getCategoryIdList().length == 0) {
            return true;
        }

        int count = 0;

        List<Long> longList = new ArrayList<>();
        for (int i = 0; i < getCategoryIdList().length; i++) {
            longList.add(getCategoryIdList()[i]);
        }

        for (int i = 0; i < categories.size(); i++) {
            if (longList.contains(categories.get(i).getCatId())) {
                count++;
            }
        }
        return count == categories.size();
    }

    private void setUpCalendar() {
        startCalendar = Calendar.getInstance();

        startDatePickerDialog = new DatePickerDialog(getContext(), startDatePickerListener, startCalendar.get(Calendar.YEAR),
                startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));

        startDatePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

        endCalendar = Calendar.getInstance();

        endDatePickerDialog = new DatePickerDialog(getContext(), endDatePickerListener, endCalendar.get(Calendar.YEAR),
                endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH));

        endDatePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

    }
}
