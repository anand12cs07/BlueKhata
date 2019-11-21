package com.everyrupee.data.local.prefs;

public interface PreferencesHelper {

    void setAppTheme(String appTheme);

    String getAppTheme();

    void setAppThemeChange(boolean isAppThemeChange);

    boolean isAppThemeChange();
}
