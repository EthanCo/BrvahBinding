package com.lib.frame.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.lib.frame.view.changer.FragmentChanger;
import com.lib.frame.view.changer.IFragmentChanger;
import com.lib.frame.view.changer.IFragmentSave;
import com.lib.frame.view.changer.transform.ChangeConfig;
import com.lib.frame.view.changer.transform.IFragmentChangeSign;
import com.lib.frame.viewmodel.BaseViewModel;

import java.util.List;


/**
 * @Description 使用Fragment的Activity基础该类
 * Created by EthanCo on 2016/6/14.
 */
public abstract class BaseFragmentActivity<V, T extends BaseViewModel<V>> extends BaseActivity<V, T> implements IFragmentChanger, IFragmentChangeSign {
    private IFragmentChanger fragmentChanger;
    private IFragmentSave fragmentSave;
    //最小回退数标记
    private int minBackStackEntryCount = 0;

    public IFragmentSave getFragmentSave() {
        return fragmentSave;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initFragmentChanger();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected T createViewModel() {
        return null;
    }

    private void initFragmentChanger() {
        FragmentChanger changerImpl = new FragmentChanger(getSupportFragmentManager());
        fragmentChanger = changerImpl;
        fragmentSave = changerImpl;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fragmentSave.restoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentSave.saveInstanceState(outState);
    }

    @Override
    public void execChangeFragment(Fragment fragment, @Nullable ChangeConfig config) {
        if (fragment == null) return;
        if (config == null) {
            changeFragment(fragment, getDefaultFragmentContainerViewId());
        } else {
            if (TextUtils.isEmpty(config.getFragmentTag())) {
                changeFragment(fragment, getDefaultFragmentContainerViewId(),
                        config.isBackStack(), config.isUseCache(), config.isUseAnimation());
            } else {
                changeFragment(fragment, config.getFragmentTag(), getDefaultFragmentContainerViewId(),
                        config.isBackStack(), config.isUseCache(), config.isUseAnimation());
            }
        }
    }

    //默认的ContainerViewId
    protected int getDefaultFragmentContainerViewId() {
        throw new IllegalStateException("要调用execChangeFragment，请先实现getDefaultFragmentContainerViewId方法");
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
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof ToolbarFragment) {
                if (fragment != null && ((ToolbarFragment) fragment).onBackPressed()) {
                    return;
                }
            }
        }
        super.onBackPressed();
    }

    public int getMinBackStackEntryCount() {
        return minBackStackEntryCount;
    }

    public void setMinBackStackEntryCount(int minBackStackEntryCount) {
        this.minBackStackEntryCount = minBackStackEntryCount;
    }
}
