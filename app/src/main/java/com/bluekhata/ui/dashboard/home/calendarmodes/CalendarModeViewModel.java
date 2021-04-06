package com.bluekhata.ui.dashboard.home.calendarmodes;

import androidx.lifecycle.MutableLiveData;

import com.bluekhata.data.DataManager;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

import static com.bluekhata.data.local.prefs.AppPreferencesHelper.PREF_KEY_DAILY_TYPE;
import static com.bluekhata.data.local.prefs.AppPreferencesHelper.PREF_KEY_MONTHLY_TYPE;
import static com.bluekhata.data.local.prefs.AppPreferencesHelper.PREF_KEY_WEEKLY_TYPE;
import static com.bluekhata.data.local.prefs.AppPreferencesHelper.PREF_KEY_YEARLY_TYPE;

public class CalendarModeViewModel extends BaseViewModel {
    public MutableLiveData<Integer> selectedCalendarModeLiveData = new MutableLiveData<>();

    public CalendarModeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getSelectedCalendarMode(){
        String calendarMode = getDataManager().getSelectedCalenderType();
        switch (calendarMode){
            case PREF_KEY_DAILY_TYPE:
                selectedCalendarModeLiveData.setValue(0);
                break;
            case PREF_KEY_WEEKLY_TYPE:
                selectedCalendarModeLiveData.setValue(1);
                break;
            case PREF_KEY_MONTHLY_TYPE:
                selectedCalendarModeLiveData.setValue(2);
                break;
            case PREF_KEY_YEARLY_TYPE:
                selectedCalendarModeLiveData.setValue(3);
                break;
        }
    }

    public void setSelectedCalendarMode(String calendarMode){
        getDataManager().setSelectedCalenderType(calendarMode);
    }
}
