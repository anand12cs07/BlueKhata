package com.everyrupee.ui.recursive;

import androidx.lifecycle.MutableLiveData;

import com.everyrupee.data.DataManager;
import com.everyrupee.data.model.db.custom.RecurrenceDetail;
import com.everyrupee.ui.base.BaseViewModel;
import com.everyrupee.utils.rx.SchedulerProvider;

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
