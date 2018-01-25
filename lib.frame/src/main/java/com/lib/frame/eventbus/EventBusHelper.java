package com.lib.frame.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus 帮助类
 *
 * @author EthanCo
 * @since 2017/10/26
 */

public class EventBusHelper {

    private boolean enable = false;
    private boolean isRegister = false;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void register(Object subscriber) {
        if (enable && !isRegister) {
            EventBus.getDefault().register(subscriber);
            isRegister = true;
        }
    }

    public void unregister(Object subscriber) {
        if (enable) {
            EventBus.getDefault().unregister(subscriber);
        }
    }
}
