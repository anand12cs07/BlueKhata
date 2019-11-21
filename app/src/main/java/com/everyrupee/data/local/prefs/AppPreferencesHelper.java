package com.everyrupee.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.everyrupee.di.PreferenceInfo;

import javax.inject.Inject;

public class AppPreferencesHelper implements PreferencesHelper {

    public static final String PREF_KEY_LIGHT_THEME = "PREF_KEY_LIGHT_THEME";
    public static final String PREF_KEY_DARK_THEME = "PREF_KEY_DARK_THEME";

    private static final String PREF_KEY_THEME_CHANGE = "PREF_KEY_THEME_CHANGE";
    private static final String PREF_KEY_THEME_TYPE = "PREF_KEY_THEME_TYPE";

    private final SharedPreferences mPrefs;
    private final SharedPreferences.Editor editor;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        editor = mPrefs.edit();
    }

    @Override
    public void setAppTheme(String appTheme) {
        editor.putString(PREF_KEY_THEME_TYPE,appTheme).commit();
    }

    @Override
    public String getAppTheme() {
        return mPrefs.getString(PREF_KEY_THEME_TYPE,PREF_KEY_LIGHT_THEME);
    }

    @Override
    public void setAppThemeChange(boolean isAppThemeChange) {
        editor.putBoolean(PREF_KEY_THEME_CHANGE, isAppThemeChange).commit();
    }

    @Override
    public boolean isAppThemeChange() {
        return mPrefs.getBoolean(PREF_KEY_THEME_CHANGE, false);
    }
}
