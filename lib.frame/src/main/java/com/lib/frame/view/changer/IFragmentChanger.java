package com.lib.frame.view.changer;

import android.support.v4.app.Fragment;

/**
 * 改变Fragment接口
 *
 * @author EthanCo
 * @since 2016/11/16
 */

public interface IFragmentChanger {
    void changeFragment(Fragment fragment, int containerViewId);

    void changeFragment(Fragment fragment, int containerViewId, boolean isBackStack, boolean useCache);

    void changeFragment(Fragment fragment, int containerViewId, boolean isBackStack, boolean useCache, boolean useAnimation);

    void changeFragment(Fragment fragment, String fragmentTag, int containerViewId, boolean isBackStack, boolean useCache, boolean useAnimation);

    void replaceFragment(Fragment fragment, int containerViewId);

    //清除Fragment栈
    void clearFragmentBackStack();
}
