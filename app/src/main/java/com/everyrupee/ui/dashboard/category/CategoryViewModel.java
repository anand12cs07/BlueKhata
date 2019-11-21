package com.everyrupee.ui.dashboard.category;

import com.everyrupee.data.DataManager;
import com.everyrupee.ui.base.BaseViewModel;
import com.everyrupee.utils.rx.SchedulerProvider;

public class CategoryViewModel extends BaseViewModel<ICategoryNavigator> {

    public CategoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
