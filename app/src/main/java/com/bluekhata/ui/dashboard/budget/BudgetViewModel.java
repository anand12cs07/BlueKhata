package com.bluekhata.ui.dashboard.budget;

import com.bluekhata.data.DataManager;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

public class BudgetViewModel extends BaseViewModel {
    public BudgetViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
