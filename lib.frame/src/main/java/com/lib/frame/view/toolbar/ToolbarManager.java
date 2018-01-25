package com.lib.frame.view.toolbar;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

import java.lang.ref.WeakReference;

/**
 * Toolbar 管理类
 *
 * @author EthanCo
 * @since 2016/12/6
 */

public class ToolbarManager {
    private WeakReference<Activity> contextRef;

    public ToolbarManager(Activity context) {
        this.contextRef = new WeakReference(context);
    }

    /**
     * 初始化initToolbar (需保证布局中有<include layout="@layout/include_toolbar" />)
     *
     * @param title             标题
     * @param displayHomeEnable 是否有返回按钮
     * @param toolbar           Toolbar或其子类
     * @return
     */
    public Toolbar initToolbar(Toolbar toolbar, String title, boolean displayHomeEnable) {
        if (toolbar != null) {
            Activity context = contextRef.get();
            if (context == null) return null;

            ImmersionBar.with(context).titleBar(toolbar).init();

            setTitle(toolbar, title, displayHomeEnable);
        }
        return toolbar;
    }

    public Toolbar setTitle(Toolbar toolbar, String title, boolean displayHomeEnable) {
        if (toolbar != null) {
            TextView tvTitle = toolbar.findViewById(com.lib.frame.R.id.title);
            if (tvTitle != null) {
                tvTitle.setText(title);
            }

            Activity context = contextRef.get();
            if (context == null) return null;

            ((AppCompatActivity) context).setSupportActionBar(toolbar);

            ActionBar actionbar = ((AppCompatActivity) context).getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(displayHomeEnable);
            actionbar.setDisplayShowHomeEnabled(displayHomeEnable);
            actionbar.setDisplayShowTitleEnabled(true);
        }
        return toolbar;
    }
}
