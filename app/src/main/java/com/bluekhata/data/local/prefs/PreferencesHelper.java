package com.bluekhata.data.local.prefs;

public interface PreferencesHelper {

    void setAppTheme(String appTheme);

    String getAppTheme();

    void setAppThemeChange(boolean isAppThemeChange);

    boolean isAppThemeChange();

    String getSelectedCalenderType();

    void setSelectedCalenderType(String calenderType);
}
