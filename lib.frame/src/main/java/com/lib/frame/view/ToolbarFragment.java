package com.lib.frame.view;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.lib.frame.view.toolbar.TitleBarControlCenter;
import com.lib.frame.view.toolbar.ToolbarManager;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * Fragment Toolbar 基类
 * Created by EthanCo on 2016/12/6.
 */
@SuppressWarnings("unchecked")
public abstract class ToolbarFragment extends RxFragment {
    private ToolbarManager toolbarManager;
    private boolean useActivityToolbar = true;
    private String title;
    private Boolean displayHomeEnable;

    /**
     * 设置是否使用Activity的Toolbar，默认为true
     * 如ToolbarFragment需使用自己的Toolbar，需设为false
     *
     * @param useActivityToolbar
     */
    public void setUseActivityToolbar(boolean useActivityToolbar) {
        this.useActivityToolbar = useActivityToolbar;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarManager = new ToolbarManager(getActivity());
    }

    /**
     * 初始化initToolbar (需保证布局中有<include layout="@layout/include_toolbar" />)
     * 或
     * 有id为toolbar的 TitleBar View
     *
     * @param title             标题
     * @param displayHomeEnable 是否有返回按钮
     * @param rootView          fragment rootView
     * @return
     */
    protected View initToolbar(View rootView, String title, boolean displayHomeEnable) {
        setHasOptionsMenu(true); //使 fragment的onOptionsItemSelected 启用
        if (useActivityToolbar) {
            if (getActivity() instanceof ToolbarActivity) {
                this.title = title;
                this.displayHomeEnable = displayHomeEnable;
                ToolbarActivity activity = (ToolbarActivity) getActivity();
                View toolbar = activity.initToolbar(title, displayHomeEnable);
                activity.setActivityHomeButtonEnable(false);
                return toolbar;
            } else {
                //return null;
                throw new IllegalStateException("需使用ToolbarActivity或将useActivityToolbar变量置为false");
            }
        } else {
            return TitleBarControlCenter.initTitle(getActivity(), rootView, title, displayHomeEnable);
        }
    }

    /**
     * 初始化initToolbar (需保证布局中有<include layout="@layout/include_toolbar" />)
     * 或
     * 有id为toolbar的 TitleBar View
     *
     * @param title             标题
     * @param displayHomeEnable 是否有返回按钮
     * @param rootView          fragment rootView
     * @return
     */
    protected View initToolbar(View rootView, @StringRes int title, boolean displayHomeEnable) {
        return initToolbar(rootView, getString(title), displayHomeEnable);
    }

    /**
     * 初始化initToolbar (需保证布局中有<include layout="@layout/include_toolbar" />)
     * 或
     * 有id为toolbar的 TitleBar View
     * <p>
     * 从Fragment中查找titlebar
     *
     * @param title             标题
     * @param displayHomeEnable 是否有返回按钮
     * @return
     */
    protected void initToolbar(final String title, final boolean displayHomeEnable) {
        initToolbar(getView(), title, displayHomeEnable);
    }

    /**
     * 初始化initToolbar (需保证布局中有<include layout="@layout/include_toolbar" />)
     * 或
     * 有id为toolbar的 titleBar View
     * <p>
     * 从Fragment中查找titlebar
     *
     * @param title             标题
     * @param displayHomeEnable 是否有返回按钮
     * @return
     */
    protected void initToolbar(@StringRes final int title, final boolean displayHomeEnable) {
        getActivity().getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                initToolbar(getView(), getString(title), displayHomeEnable);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("ZB-ToolbarFragment", "onOptionsItemSelected:");
        if (item.getItemId() == android.R.id.home) {
            if (onBackPressed()) return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        int backStackEntryCount = count;
        Log.i("ZB-ToolbarFragment", "backStackEntryCount:" + backStackEntryCount);
        if (backStackEntryCount <= 1) {
            ActivityCompat.finishAfterTransition(getActivity());
        } else {
            getFragmentManager().popBackStack();
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TitleBarControlCenter.destroy(getActivity());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        Log.i("Z-onHiddenChanged", "onHiddenChanged:" + hidden + " fragment:" + this);

        if (!hidden && hasTitle()) {
            initToolbar(title, displayHomeEnable);
        }

        if (!hidden) {
            if (getActivity() instanceof BaseFragmentActivity) {
                ((BaseFragmentActivity) (getActivity())).getFragmentSave().setCurrFragmentTag(this);
            }
        }
    }

    private boolean hasTitle() {
        return !TextUtils.isEmpty(title) && displayHomeEnable != null;
    }
}
