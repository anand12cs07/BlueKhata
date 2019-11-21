package com.everyrupee.ui.setting;

import com.everyrupee.data.DataManager;
import com.everyrupee.ui.base.BaseViewModel;
import com.everyrupee.utils.rx.SchedulerProvider;

import static com.everyrupee.data.local.prefs.AppPreferencesHelper.PREF_KEY_DARK_THEME;
import static com.everyrupee.data.local.prefs.AppPreferencesHelper.PREF_KEY_LIGHT_THEME;

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
