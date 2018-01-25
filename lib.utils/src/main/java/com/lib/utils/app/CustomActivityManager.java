package com.lib.utils.app;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by wang4 on 2017/12/7.
 */

public class CustomActivityManager {

    private static Stack<Activity> activityStack;
    private static CustomActivityManager instance;

    private CustomActivityManager() {
    }

    public static CustomActivityManager getScreenManager() {
        if (instance == null) {
            instance = new CustomActivityManager();
        }
        return instance;
    }


    //获得当前栈顶Activity
    public Activity currentActivity() {
        Activity activity = null;
        if (!activityStack.empty())
            activity = activityStack.lastElement();
        return activity;
    }

    //将当前Activity推入栈中
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 结束指定的Activity
     */
    public  void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

}
