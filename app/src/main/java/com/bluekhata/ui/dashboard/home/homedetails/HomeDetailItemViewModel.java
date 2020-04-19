package com.bluekhata.ui.dashboard.home.homedetails;

import androidx.databinding.ObservableField;

import com.bluekhata.data.model.db.custom.TransactionWithTag;
import com.bluekhata.utils.CommonUtils;

public class HomeDetailItemViewModel {
    private final ObservableField<String> tagList;
    private final ObservableField<String> amount;
    private final ObservableField<String> transactionStatus;

    public HomeDetailItemViewModel(TransactionWithTag transactionWithTag) {
        tagList = new ObservableField<>(transactionWithTag.getTagList());
        amount = new ObservableField<>(CommonUtils.getFormattedAmount(
                transactionWithTag.getTransaction().getTransactionAmount()));

        transactionStatus = new ObservableField<>(CommonUtils.getFormattedTransactionStatus(
                transactionWithTag.getTransaction().getTransactionDate()));
    }

    public ObservableField<String> getTagList() {
        return tagList;
    }

    public ObservableField<String> getAmount() {
        return amount;
    }

    public ObservableField<String> getTransactionStatus() {
        return transactionStatus;
    }
}
