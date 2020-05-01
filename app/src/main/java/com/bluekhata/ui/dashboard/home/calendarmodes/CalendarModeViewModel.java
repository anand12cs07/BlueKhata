package com.bluekhata.ui.dashboard.home.calendarmodes;

import com.bluekhata.data.DataManager;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

public class CalendarModeViewModel extends BaseViewModel {
    public CalendarModeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
