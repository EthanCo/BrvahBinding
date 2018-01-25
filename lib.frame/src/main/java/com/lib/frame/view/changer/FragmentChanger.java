package com.lib.frame.view.changer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;

import com.lib.frame.R;

import java.util.ArrayList;

/**
 * 改变Fragment
 *
 * @author EthanCo
 * @since 2016/11/16
 */

public class FragmentChanger implements IFragmentChanger, IFragmentSave {
    public static final String KEY_FRAGMENT_TAG_LIST = "fragment_tag_list";

    private String currFragmentTag;
    private static ArrayList<String> tagList;
    private FragmentManager fragmentManager;

    public FragmentChanger(final FragmentManager fragmentManager) {
        this.tagList = new ArrayList<>();
        this.fragmentManager = fragmentManager;
        this.fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

            }
        });
    }

    @Override
    public void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            resetTagList(savedInstanceState);
            resetFragmentUI();
        }
    }

    @Override
    public void saveInstanceState(Bundle outState) {
        outState.putStringArrayList(KEY_FRAGMENT_TAG_LIST, tagList);
    }

    /**
     * 改变Fragment，不回退,缓存Fragment
     *
     * @param fragment        Fragment
     * @param containerViewId containerView id
     */
    @Override
    public void changeFragment(Fragment fragment, int containerViewId) {
        changeFragment(fragment, getFragmentTag(fragment), containerViewId, false, true, false);
    }

    @Override
    public void changeFragment(Fragment fragment, int containerViewId, boolean isBackStack, boolean useCache) {
        changeFragment(fragment, containerViewId, isBackStack, useCache, false);
    }

    public static final String TAG = "ZB-FragmentBack";

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
        Log.i(TAG, "changeFragment " + fragment.getClass().getSimpleName() + "isBackStack:" + isBackStack + " useCache:" + useCache + " count:" + fragmentManager.getBackStackEntryCount());
        changeFragment(fragment, getFragmentTag(fragment), containerViewId, isBackStack, useCache, useAnimation);
        Log.i(TAG, "count:" + fragmentManager.getBackStackEntryCount());
    }

    /**
     * 替换Fragment
     *
     * @param fragment
     * @param containerViewId
     */
    @Override
    public void replaceFragment(Fragment fragment, int containerViewId) {
        changeFragment(fragment, "", containerViewId, false, false, false);
    }

    @Override
    public void clearFragmentBackStack() {
        fragmentManager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @NonNull
    private String getFragmentTag(Fragment fragment) {
        return fragment.getClass().getName();
    }

    //============================= Z-细节方法 ==============================/

    private void resetFragmentUI() {
        FragmentTransaction transformation = fragmentManager.beginTransaction();
        for (String tag : tagList) {
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if (fragment == null) {
                continue;
            }

            if (tag.equals(currFragmentTag)) {
                transformation.show(fragment);
            } else {
                transformation.hide(fragment);
            }
        }
        transformation.commit();
    }

    private void resetTagList(Bundle savedInstanceState) {
        ArrayList<String> saveList = savedInstanceState.getStringArrayList(KEY_FRAGMENT_TAG_LIST);
        if (saveList != null && saveList.size() > 0) {
            this.tagList = saveList;
        }
    }

    /**
     * 改变Fragment
     *
     * @param fragment        Fragment
     * @param fragmentTag     用于识别Fragment 的唯一标识
     * @param containerViewId containerView id
     * @param isBackStack     是否回退
     * @param useCache        是否使用缓存 hide-add
     */
    @Override
    public void changeFragment(Fragment fragment, String fragmentTag, int containerViewId, boolean isBackStack, boolean useCache, boolean useAnimation) {
        FragmentTransaction transformation = fragmentManager.beginTransaction();
        if (useAnimation) {
            transformation.setCustomAnimations(
                    R.anim.slide_left_in, R.anim.slide_left_out,
                    R.anim.slide_right_in, R.anim.slide_right_out);
        }
        if (useCache) {
            changeFragmentByCache(fragment, fragmentTag, containerViewId, fragmentManager, transformation);
        } else {
            replaceFragment(fragment, containerViewId, transformation);
        }
        if (!isBackStack) {
            transformation.disallowAddToBackStack();
        } else {
            transformation.addToBackStack(fragmentTag);
        }
        transformation.commit();
    }

    private void changeFragmentByCache(Fragment fragment, String fragmentTag, int containerViewId, FragmentManager fragmentManager, FragmentTransaction transformation) {
        if (!TextUtils.isEmpty(currFragmentTag)) {
            Fragment currFragment = fragmentManager.findFragmentByTag(currFragmentTag);
            if (null != currFragment) {
                transformation.hide(currFragment);
            }
        }
        /*for (Fragment mFragment : fragmentManager.getFragments()) {
            if (mFragment != null && mFragment.isVisible()) {
                transformation.hide(mFragment);
            }
        }*/
        Fragment newFragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (null == newFragment) {
            transformation.add(containerViewId, fragment, fragmentTag);
        } else {
            if (newFragment.isAdded()) {
                newFragment.setArguments(fragment.getArguments());
                transformation.show(newFragment);
            } else {
                transformation.add(containerViewId, fragment, fragmentTag);
            }
        }
        setCurrFragmentTag(fragmentTag);
    }

    private void replaceFragment(Fragment fragment, int containerViewId, FragmentTransaction transformation) {
        transformation.replace(containerViewId, fragment);
    }

    @Override
    public void setCurrFragmentTag(Fragment fragment) {
        String fragmentTag = getFragmentTag(fragment);
        setCurrFragmentTag(fragmentTag);
    }

    private void setCurrFragmentTag(String fragmentTag) {
        this.currFragmentTag = fragmentTag;
        if (!tagList.contains(fragmentTag)) {
            tagList.add(fragmentTag);
        }
    }
}
