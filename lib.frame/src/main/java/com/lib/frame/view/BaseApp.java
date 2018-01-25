package com.lib.frame.view;

import android.app.Application;

import com.lib.utils.app.CustomActivityManager;

/**
 * Created by wang4 on 2017/12/7.
 */

public class BaseApp extends Application {
    private CustomActivityManager activityManager = null;

    public CustomActivityManager getActivityManager() {
        return activityManager;
    }

    public void setManager(CustomActivityManager activityManager) {
        this.activityManager = activityManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//初始化activityManager
        activityManager = CustomActivityManager.getScreenManager();
    }
}
