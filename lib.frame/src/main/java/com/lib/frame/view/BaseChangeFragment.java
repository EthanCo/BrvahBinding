package com.lib.frame.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

import com.lib.frame.view.changer.FragmentChanger;
import com.lib.frame.view.changer.IFragmentChanger;
import com.lib.frame.view.changer.IFragmentSave;
import com.lib.frame.view.changer.transform.ChangeConfig;
import com.lib.frame.view.changer.transform.IFragmentChangeSign;
import com.lib.frame.viewmodel.BaseViewModel;

/**
 * 使用切换Fragment基础类，对于嵌套fragment，提供便捷的方法
 */
public abstract class BaseChangeFragment<V, T extends BaseViewModel<V>> extends BaseFragment<V, T> implements IFragmentChanger, IFragmentChangeSign {
    private IFragmentChanger fragmentChanger;
    private IFragmentSave fragmentSave;

    public void setFragmentSave(IFragmentSave fragmentSave) {
        this.fragmentSave = fragmentSave;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initFragmentChanger();
        super.onCreate(savedInstanceState);

        if (getActivity() instanceof BaseFragmentActivity) {
            BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
            int minStackCount = activity.getMinBackStackEntryCount();
            activity.setMinBackStackEntryCount(++minStackCount);
        }
        fragmentSave.restoreInstanceState(savedInstanceState);
    }

    private void initFragmentChanger() {
        FragmentChanger changerImpl = new FragmentChanger(getChildFragmentManager());
        fragmentChanger = changerImpl;
        fragmentSave = changerImpl;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentSave.saveInstanceState(outState);
    }

    @Override
    public void execChangeFragment(Fragment fragment, @Nullable ChangeConfig config) {
        if (getActivity() instanceof IFragmentChangeSign) {
            ((IFragmentChangeSign) (getActivity())).execChangeFragment(fragment, config);
        }
    }

    /**
     * 改变Fragment，不回退,缓存Fragment
     *
     * @param fragment        Fragment
     * @param containerViewId containerView id
     */
    @Override
    public void changeFragment(Fragment fragment, int containerViewId) {
        fragmentChanger.changeFragment(fragment, containerViewId);
    }


    @Override
    public void changeFragment(Fragment fragment, int containerViewId, boolean isBackStack, boolean useCache) {
        fragmentChanger.changeFragment(fragment, containerViewId, isBackStack, useCache);
    }

    /**
     * 改变Fragment
     *
     * @param fragment        Fragment
     * @param containerViewId containerView id
     * @param isBackStack     是否回退
     * @param useCache        是否使用缓存 true使用add/hide false使用replace
     */
    @Override
    public void changeFragment(Fragment fragment, int containerViewId, boolean isBackStack, boolean useCache, boolean useAnimation) {
        fragmentChanger.changeFragment(fragment, containerViewId, isBackStack, useCache, useAnimation);
    }

    /**
     * 改变Fragment
     *
     * @param fragment        Fragment
     * @param fragmentTag     识别fragment的唯一标识
     * @param containerViewId containerView id
     * @param isBackStack     是否回退
     * @param useCache        是否使用缓存 true使用add/hide false使用replace
     */
    @Override
    public void changeFragment(Fragment fragment, String fragmentTag, int containerViewId, boolean isBackStack, boolean useCache, boolean useAnimation) {
        fragmentChanger.changeFragment(fragment, fragmentTag, containerViewId, isBackStack, useCache, useAnimation);
    }

    /**
     * 替换Fragment
     *
     * @param fragment
     * @param containerViewId
     */
    @Override
    public void replaceFragment(Fragment fragment, int containerViewId) {
        fragmentChanger.replaceFragment(fragment, containerViewId);
    }

    @Override
    public void clearFragmentBackStack() {
        fragmentChanger.clearFragmentBackStack();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        Log.i("Z-onHiddenChanged","onHiddenChanged:"+hidden+" fragment:"+this);
        if (!hidden) {
            fragmentSave.setCurrFragmentTag(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("ZB-BaseChangeFragment", "onOptionsItemSelected");
        if (item.getItemId() == android.R.id.home) {
            if (onBackPressed()) return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        int childCount = getChildFragmentManager().getBackStackEntryCount();
        int backStackEntryCount = count;
        Log.i("ZB-BaseChangeFragment", "backStackEntryCount:" + backStackEntryCount + " childCount:" + childCount);
        if (backStackEntryCount <= 1) {
            ActivityCompat.finishAfterTransition(getActivity());
        }else{
            getFragmentManager().popBackStack();
        }
        return true;
    }
}
