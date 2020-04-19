package com.bluekhata.ui.recursive;

import androidx.lifecycle.MutableLiveData;

import com.bluekhata.data.DataManager;
import com.bluekhata.data.model.db.custom.RecurrenceDetail;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.functions.Consumer;

public class RecursiveActivityViewModel extends BaseViewModel<RecursiveActivityNavigator> {
    private final MutableLiveData<List<RecurrenceDetail>> recurrenceList = new MutableLiveData<>();

    public RecursiveActivityViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchRecurrenceDetail(long startDate){
        getCompositeDisposable().add(getDataManager()
        .getAllRecurrence(startDate)
        .subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribe(new Consumer<List<RecurrenceDetail>>() {
            @Override
            public void accept(List<RecurrenceDetail> recurrenceDetails) throws Exception {
                recurrenceList.setValue(recurrenceDetails);
            }
        }));
    }

    public MutableLiveData<List<RecurrenceDetail>> getRecurrenceList() {
        return recurrenceList;
    }
}
