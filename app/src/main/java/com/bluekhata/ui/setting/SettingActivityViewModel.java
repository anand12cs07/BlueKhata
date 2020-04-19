package com.bluekhata.ui.setting;

import com.bluekhata.data.DataManager;
import com.bluekhata.ui.base.BaseViewModel;
import com.bluekhata.utils.rx.SchedulerProvider;

import static com.bluekhata.data.local.prefs.AppPreferencesHelper.PREF_KEY_DARK_THEME;
import static com.bluekhata.data.local.prefs.AppPreferencesHelper.PREF_KEY_LIGHT_THEME;

public class SettingActivityViewModel extends BaseViewModel<SettingActivityNavigator> {
    public SettingActivityViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public boolean isDarkThemeEnabled() {
        return getDataManager().getAppTheme().equals(PREF_KEY_DARK_THEME);
    }

    public void onSwitchThemeCheckedChange(boolean themeChange) {
        if (themeChange) {
            getDataManager().setAppTheme(PREF_KEY_DARK_THEME);
        } else {
            getDataManager().setAppTheme(PREF_KEY_LIGHT_THEME);
        }
        getDataManager().setAppThemeChange(true);
    }
}
