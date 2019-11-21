package com.everyrupee.ui.dashboard.transaction.datedialog;

import androidx.lifecycle.MutableLiveData;
import androidx.databinding.ObservableField;

import com.everyrupee.data.DataManager;
import com.everyrupee.data.model.db.Recurrence;
import com.everyrupee.data.model.db.Reminder;
import com.everyrupee.ui.base.BaseViewModel;
import com.everyrupee.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.functions.Consumer;

public class DateOptionViewModel extends BaseViewModel {
    private ObservableField<String> recurrenceType = new ObservableField<>();
    private ObservableField<String> reminderType = new ObservableField<>();

    private final MutableLiveData<Reminder> reminderMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Recurrence> recurrenceMutableLiveData = new MutableLiveData<>();

    public DateOptionViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchReminders(final int index) {
        getCompositeDisposable().add(getDataManager()
                .loadAllReminders()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Reminder>>() {
                               @Override
                               public void accept(List<Reminder> reminders) throws Exception {
                                   reminderMutableLiveData.setValue(reminders.get(index));
                               }
                           }
                ));
    }



    public void fetchRecurrence(final int index) {
        getCompositeDisposable().add(getDataManager()
                .loadAllRecurrence()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Recurrence>>() {
                               @Override
                               public void accept(List<Recurrence> recurrences) throws Exception {
                                   recurrenceMutableLiveData.setValue(recurrences.get(index));
                               }
                           }
                ));
    }



    public void setRecurrenceType(ObservableField<String> recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    public ObservableField<String> getRecurrenceType() {
        return recurrenceType;
    }

    public void setReminderType(ObservableField<String> reminderType) {
        this.reminderType = reminderType;
    }

    public ObservableField<String> getReminderType() {
        return reminderType;
    }

    public MutableLiveData<Recurrence> getRecurrenceMutableLiveData() {
        return recurrenceMutableLiveData;
    }

    public MutableLiveData<Reminder> getReminderMutableLiveData() {
        return reminderMutableLiveData;
    }
}
