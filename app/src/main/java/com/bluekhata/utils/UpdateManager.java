package com.bluekhata.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;

public class UpdateManager extends AsyncTask<Void, String, String> {

    private Context context;
    private String currentVersion = null;
    private String packageName;
    private UpdateListener updateListener;

    public UpdateManager(Context context) {
        this.context = context;
        packageName = "com.whatsapp";
        try {
            currentVersion = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setOnUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    @Override
    protected String doInBackground(Void... voids) {

        String newVersion = null;
        try {
            newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + packageName)
                    .timeout(30000)
//                    .userAgent("Mozilla/5.0 (Linux; Android 8.0.0;) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.92 Mobile Safari/537.36")
//                    .referrer("http://www.google.com")
                    .get()
                    .select("div[itemprop=softwareVersion]")
                    .first()
                    .ownText();
            return newVersion;
        } catch (Exception e) {
            return newVersion;
        }
    }

    @Override
    protected void onPostExecute(String onlineVersion) {
        super.onPostExecute(onlineVersion);
        if (updateListener != null) {
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {
                    updateListener.onUpdateAvailable("Update Available", true);
                }
            } else {
                updateListener.onUpdateAvailable(currentVersion, false);
            }
        }
        Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);
    }

    public interface UpdateListener {
        public void onUpdateAvailable(String version, boolean isUpdateAvailable);
    }
}
