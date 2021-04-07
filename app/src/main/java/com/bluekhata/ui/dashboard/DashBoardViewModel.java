package com.bluekhata.ui.dashboard;

import androidx.databinding.ObservableField;

import com.bluekhata.data.DataManager;
import com.bluekhata.data.local.prefs.AppPreferencesHelper;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

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
