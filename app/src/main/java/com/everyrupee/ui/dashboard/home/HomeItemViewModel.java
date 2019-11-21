package com.everyrupee.ui.dashboard.home;

import androidx.databinding.ObservableField;

import com.everyrupee.data.model.db.custom.TransactionWithCategory;
import com.everyrupee.utils.CommonUtils;

public class HomeItemViewModel {

    public final ObservableField<String> transactionCategory;

    public final ObservableField<String> transactionCategoryIcon;

    public final ObservableField<String> transactionCategoryColor;

    public final ObservableField<String> transactionAmount;

    public final ObservableField<String> transactionNumber;

    public HomeItemViewModel(TransactionWithCategory transaction){
        transactionCategory = new ObservableField<>(transaction.getCategory().getCatTitle());
        transactionCategoryIcon = new ObservableField<>(transaction.getCategory().getCatIcon());
        transactionCategoryColor = new ObservableField<>(transaction.getCategory().getCatColor());
        transactionAmount = new ObservableField<>(CommonUtils.getFormattedAmount(transaction.getSumOfAmount()));
        transactionNumber = new ObservableField<>(CommonUtils.getFormattedTransactionNumber(transaction.getTransactionCount()));
    }
}
