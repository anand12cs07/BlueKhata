package com.everyrupee.ui.dashboard.history;

import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.everyrupee.ui.dashboard.DashBoardNavigator;
import com.everyrupee.data.DataManager;
import com.everyrupee.data.model.db.custom.TransactionWithTagAndCategory;
import com.everyrupee.ui.base.BaseViewModel;
import com.everyrupee.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class HistoryViewModel extends BaseViewModel<DashBoardNavigator> {

    private final MutableLiveData<List<TransactionWithTagAndCategory>> historyList = new MutableLiveData<>();

    public HistoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchHistoryList(List<Long> categoryIds, long startDate, long endDate) {
        getCompositeDisposable().add(getDataManager()
                .getAllTransactionWithTag(categoryIds, startDate, endDate)
                .flatMap(new Function<List<TransactionWithTagAndCategory>, ObservableSource<List<TransactionWithTagAndCategory>>>() {
                    @Override
                    public ObservableSource<List<TransactionWithTagAndCategory>> apply(List<TransactionWithTagAndCategory> transactionWithTagAndCategories) throws Exception {
                        return Observable.just(getDateIndexTransactionList(transactionWithTagAndCategories));
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<TransactionWithTagAndCategory>>() {
                    @Override
                    public void accept(List<TransactionWithTagAndCategory> transactionWithTagAndCategory) throws Exception {
                        historyList.setValue(transactionWithTagAndCategory);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("TAG>", throwable.getMessage());
                    }
                }));
    }

    public void fetchSearchHistoryList(List<Long> categoryIds, String searchTag, final long startDate, final long endDate) {
        getCompositeDisposable().add(getDataManager()
                .getAllTransactionWithTagAndCategory(categoryIds, searchTag.concat("%"), startDate, endDate)
                .flatMap(new Function<List<TransactionWithTagAndCategory>, ObservableSource<List<TransactionWithTagAndCategory>>>() {
                    @Override
                    public ObservableSource<List<TransactionWithTagAndCategory>> apply(List<TransactionWithTagAndCategory> transactionWithTagAndCategories) throws Exception {
                        return Observable.just(getDateIndexTransactionList(transactionWithTagAndCategories));
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<TransactionWithTagAndCategory>>() {
                    @Override
                    public void accept(List<TransactionWithTagAndCategory> list) throws Exception {
                        historyList.setValue(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("TAG>", throwable.getMessage());
                    }
                }));
    }

    public MutableLiveData<List<TransactionWithTagAndCategory>> getHistoryList() {
        return historyList;
    }

    private List<TransactionWithTagAndCategory> getDateIndexTransactionList(List<TransactionWithTagAndCategory> list) {
        List<TransactionWithTagAndCategory> finalList = new ArrayList<>();
        int size = list.size();
        if (size >= 1) {
            TransactionWithTagAndCategory header = new TransactionWithTagAndCategory(list.get(0));
            header.getTransactionWithTag().setHeader(true);
            finalList.add(header);
            finalList.add(list.get(0));
        }
        for (int i = 1; i < size; i++) {
            if (!list.get(i - 1).getTransactionWithTag().isEqualDate(list.get(i).getTransactionWithTag().getTransaction())) {
                TransactionWithTagAndCategory header = new TransactionWithTagAndCategory(list.get(i));
                header.getTransactionWithTag().setHeader(true);
                finalList.add(header);
            }
            finalList.add(list.get(i));
        }

        return finalList;
    }
}
