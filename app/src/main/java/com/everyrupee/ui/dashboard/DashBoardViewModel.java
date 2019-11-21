package com.everyrupee.ui.dashboard;

import androidx.databinding.ObservableField;

import com.everyrupee.data.DataManager;
import com.everyrupee.ui.base.BaseViewModel;
import com.everyrupee.utils.rx.SchedulerProvider;

public class DashBoardViewModel extends BaseViewModel<DashBoardNavigator> {

    private final ObservableField<String> appTitle = new ObservableField<>();

    public DashBoardViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ObservableField<String> getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String title){
        appTitle.set(title);
    }
}
