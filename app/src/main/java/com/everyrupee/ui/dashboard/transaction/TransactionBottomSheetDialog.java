package com.everyrupee.ui.dashboard.transaction;

import android.app.Dialog;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.everyrupee.ui.dashboard.RefreshListOnDismiss;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.everyrupee.ui.dashboard.transaction.datedialog.DateOptionDialog;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.everyrupee.BR;
import com.everyrupee.R;
import com.everyrupee.ViewModelProviderFactory;
import com.everyrupee.data.model.db.Category;
import com.everyrupee.data.model.db.Recurrence;
import com.everyrupee.data.model.db.Reminder;
import com.everyrupee.data.model.db.Tag;
import com.everyrupee.data.model.db.Transaction;
import com.everyrupee.data.model.db.custom.TransactionWithTag;
import com.everyrupee.databinding.BottomsheetAddTransactionBinding;
import com.everyrupee.ui.adapter.CategorySelectionAdapter;
import com.everyrupee.ui.base.BaseBottomSheetDialog;
import com.everyrupee.ui.dashboard.transaction.datedialog.DateOptionListener;
import com.everyrupee.utils.BindingUtils;
import com.everyrupee.utils.CalendarUtils;
import com.everyrupee.utils.RecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by aman on 01-09-2018.
 */

public class TransactionBottomSheetDialog extends BaseBottomSheetDialog<BottomsheetAddTransactionBinding, TransactionViewModel>
        implements View.OnClickListener, NachoTextView.OnChipClickListener, DateOptionListener,
        CategorySelectionAdapter.OnItemClickListener, RadioGroup.OnCheckedChangeListener {

    @Inject
    ViewModelProviderFactory factory;

    private TransactionViewModel viewModel;
    private BottomsheetAddTransactionBinding transactionBinding;

    private Dialog dialog;
    private Date selectedDate;
    private Recurrence selectedRecurrence;
    private Reminder selectedReminder;
    private CategorySelectionAdapter adapter;
    private ArrayAdapter<Tag> tagAdapter;
    private RecyclerViewEmptySupport recyclerViewCategory;

    private boolean isToEdit;
    private boolean isExpenseSelected = true;
    private DateOptionDialog dateOption;
    private Category selectedCategory;
    private Transaction transaction;
    private List<Tag> tagList;
    private RefreshListOnDismiss bottomSheetDismiss;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.bottomsheet_add_transaction;
    }

    @Override
    public TransactionViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(TransactionViewModel.class);
        return viewModel;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        transactionBinding = getViewDataBinding();

        setBackgroundTransparent(transactionBinding.getRoot());
        showExpandedBottomSheet(dialog);

        isToEdit = getTag().equals("update");

        if (getArguments() != null) {
            transaction = (Transaction) getArguments().getSerializable("transaction");
            tagList = (ArrayList<Tag>) getArguments().getSerializable("tags");
        }

        dateOption = new DateOptionDialog();
        dateOption.setDateOptionListener(this);

        setUpCategoryDialog();
        setUpSnackBarMessage();
        setUpRecurrence();
        setUpReminder();
        setUpOnSavedObserver();
        setUpTransactionIdObserver();
        setUpCategoryObserver();
        setUpCategoryListAdapter();

        transactionBinding.tvOne.setOnClickListener(this);
        transactionBinding.tvTwo.setOnClickListener(this);
        transactionBinding.tvThree.setOnClickListener(this);
        transactionBinding.tvFour.setOnClickListener(this);
        transactionBinding.tvFive.setOnClickListener(this);
        transactionBinding.tvSix.setOnClickListener(this);
        transactionBinding.tvSeven.setOnClickListener(this);
        transactionBinding.tvEight.setOnClickListener(this);
        transactionBinding.tvNine.setOnClickListener(this);
        transactionBinding.tvZero.setOnClickListener(this);
        transactionBinding.tvDot.setOnClickListener(this);
        transactionBinding.tvClr.setOnClickListener(this);

        transactionBinding.tvMul.setOnClickListener(this);
        transactionBinding.tvDiv.setOnClickListener(this);
        transactionBinding.tvAdd.setOnClickListener(this);
        transactionBinding.tvMinus.setOnClickListener(this);

        transactionBinding.imgCross.setOnClickListener(this);
        transactionBinding.imgOk.setOnClickListener(this);
        transactionBinding.imgCal.setOnClickListener(this);

        transactionBinding.fabCategory.setOnClickListener(this);
        transactionBinding.toggleCategory.setOnCheckedChangeListener(this);

        attachData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        transactionBinding.nachoNotes.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
//        notes.enableEditChipOnTouch(true, true);
        transactionBinding.nachoNotes.setOnChipClickListener(this);

        setUpTagListObserver();
        viewModel.fetchTagList();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_category:
                dialog.show();
                break;
            case R.id.tv_add:
                viewModel.setOperator("+");
                break;
            case R.id.tv_minus:
                viewModel.setOperator("-");
                break;
            case R.id.tv_mul:
                viewModel.setOperator("X");
                break;
            case R.id.tv_div:
                viewModel.setOperator("/");
                break;
            case R.id.tv_clr:
                viewModel.clearCurrency();
                break;
            case R.id.img_cross:
                viewModel.removeOneDigit();
                break;
            case R.id.img_cal:
                dateOption.show(getChildFragmentManager());
                dateOption.setData(selectedDate, selectedRecurrence, selectedReminder);
                break;
            case R.id.img_ok:
                viewModel.onSave(selectedCategory, selectedDate,
                        selectedReminder, selectedRecurrence, transaction, isToEdit,
                        transactionBinding.nachoNotes.getChipValues());
                break;
            case R.id.tv_dot:
                viewModel.setDot();
                break;
            default:
                AppCompatTextView tv = (AppCompatTextView) view;
                viewModel.setDigits(tv.getText().toString());
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.expense:
                isExpenseSelected = true;
                break;
            case R.id.income:
                isExpenseSelected = false;
                break;
        }
        viewModel.setCategoryList(isExpenseSelected);
    }

    @Override
    public void onDateOptionDismiss(boolean isDateChange, Date date, Reminder reminder, Recurrence recurrence) {
        selectedDate = !isDateChange ? selectedDate : date;
        selectedReminder = reminder == null ? selectedReminder : reminder;
        selectedRecurrence = recurrence == null ? selectedRecurrence : recurrence;

        transactionBinding.tvBottomDate.setText(CalendarUtils.getDateInDdMmmYyyy(selectedDate));
    }

    @Override
    public void onItemClick(Category category) {
        selectedCategory = category;
        setCategoryData(category);
        dialog.cancel();
    }

    @Override
    public void onChipClick(Chip chip, MotionEvent event) {
//        Toast.makeText(getContext(), transactionBinding.nachoNotes.getChipValues().size() + "", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        bottomSheetDismiss.onDismiss();
    }

    public void setTransactionBottomSheetDismiss(RefreshListOnDismiss bottomSheetDismiss) {
        this.bottomSheetDismiss = bottomSheetDismiss;
    }

    private void attachData() {
        if (isToEdit) {
            viewModel.fetchReminder(transaction);
            viewModel.fetchCategory(transaction);
            viewModel.fetchRecurrence(transaction);
            transactionBinding.nachoNotes.setText(TransactionWithTag.convertTagToTitle(tagList));
            viewModel.setAmount(transaction.getTransactionAmount());

            selectedDate = transaction.getTransactionDate();
        } else {
            viewModel.fetchRecurrence("None");
            viewModel.fetchReminders("None");
            viewModel.setCategoryList(true);
            selectedDate = Calendar.getInstance().getTime();
        }
        transactionBinding.tvBottomDate.setText(CalendarUtils.getDateInDdMmmYyyy(selectedDate));
    }

    private void setUpCategoryDialog() {
        dialog = new Dialog(getContext(), R.style.Theme_Dialog);
        dialog.setContentView(R.layout.dialog_category);
        dialog.setCanceledOnTouchOutside(false);

        recyclerViewCategory = dialog.findViewById(R.id.recyclerView);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCategory.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new CategorySelectionAdapter(this);
        recyclerViewCategory.setEmptyView(dialog.findViewById(R.id.empty));
        recyclerViewCategory.setAdapter(adapter);
    }

    private void setUpSnackBarMessage() {
        viewModel.getSnackBarMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Snackbar.make(transactionBinding.edtCurrency, s, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecurrence() {
        viewModel.getRecurrenceMutableLiveData().observe(this, new Observer<Recurrence>() {
            @Override
            public void onChanged(@Nullable Recurrence recurrence) {
                selectedRecurrence = recurrence;
            }
        });
    }

    private void setUpReminder() {
        viewModel.getReminderMutableLiveData().observe(this, new Observer<Reminder>() {
            @Override
            public void onChanged(@Nullable Reminder reminder) {
                selectedReminder = reminder;
            }
        });
    }

    private void setUpOnSavedObserver() {
        viewModel.getIsTransactionSaved().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean && !viewModel.getEqual().get()) {
                    dismiss();
                }
            }
        });
    }

    private void setUpCategoryListAdapter() {
        viewModel.getCategoryList().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                adapter.updateList(categories);
            }
        });
    }

    private void setUpCategoryObserver() {
        viewModel.getCategoryMutableLiveData().observe(this, new Observer<Category>() {
            @Override
            public void onChanged(@Nullable Category category) {
                selectedCategory = category;
                setCategoryData(selectedCategory);

                transactionBinding.toggleCategory.setOnCheckedChangeListener(null);
                transactionBinding.toggleCategory.check(
                        selectedCategory.getCatType() == 0 ? R.id.expense : R.id.income);
                transactionBinding.toggleCategory.setOnCheckedChangeListener(TransactionBottomSheetDialog.this);

            }
        });
    }

    private void setUpTagListObserver() {
        viewModel.getTagList().observe(this, new Observer<List<Tag>>() {
            @Override
            public void onChanged(@Nullable List<Tag> tags) {
                tagAdapter = new ArrayAdapter<Tag>(getActivity(), android.R.layout.simple_dropdown_item_1line, tags);
                transactionBinding.nachoNotes.setAdapter(tagAdapter);
            }
        });
    }

    private void setUpTransactionIdObserver() {
        viewModel.getTransactionId().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long aLong) {
                List<String> chips = transactionBinding.nachoNotes.getChipValues();
                viewModel.saveTransactionTags(chips, aLong);
            }
        });
    }

    private void setCategoryData(Category category) {
        BindingUtils.setCategoryDrawable(transactionBinding.fabCategory, category.getCatIcon());
        BindingUtils.setCategoryColor(transactionBinding.fabCategory, category.getCatColor());
        transactionBinding.categoryTitle.setText(category.getCatTitle());
    }

    private void setBackgroundTransparent(View contentView) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent())
                .getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void showExpandedBottomSheet(Dialog dialog) {
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);

                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }
}
