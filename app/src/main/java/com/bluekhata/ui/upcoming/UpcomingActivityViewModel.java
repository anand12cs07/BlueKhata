package com.bluekhata.ui.upcoming;

import androidx.lifecycle.MutableLiveData;

import com.bluekhata.data.DataManager;
import com.bluekhata.data.model.db.custom.UpcomingTransaction;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.functions.Consumer;

public class UpcomingActivityViewModel extends BaseViewModel<UpcomingActivityNavigator> {
    private final MutableLiveData<List<UpcomingTransaction>> upcomingTransaction = new MutableLiveData<>();

    public UpcomingActivityViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchUpcomingList(long startDate, long endDate){
        getCompositeDisposable().add(getDataManager()
        .getAllUpcomingTransaction(startDate, endDate)
        .subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribe(new Consumer<List<UpcomingTransaction>>() {
            @Override
            public void accept(List<UpcomingTransaction> upcomingTransactions) throws Exception {
                upcomingTransaction.setValue(upcomingTransactions);
            }
        }));
    }

    public MutableLiveData<List<UpcomingTransaction>> getUpcomingTransaction() {
        return upcomingTransaction;
    }
}
