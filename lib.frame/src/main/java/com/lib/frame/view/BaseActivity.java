package com.lib.frame.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.transition.Slide;
import android.transition.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lib.frame.eventbus.EventBusHelper;
import com.lib.frame.view.abs.ICreate;
import com.lib.frame.viewmodel.BaseViewModel;


/**
 * @param <V> View
 * @param <T> ViewModel
 * @Description Created by EthanCo on 2016/6/13.
 */
@SuppressWarnings("unchecked")
public abstract class BaseActivity<V, T extends BaseViewModel<V>> extends ToolbarActivity implements BaseView,ICreate {

    private static final String TAG = "Z-BaseActivity";
    protected T mViewModel; //ViewModel对象
    private static final boolean IS_PRINT_LIFECYCLE = true; //是否打印Activity生命周期

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printLifeCycle("onCreate : ");
        if (getApplication() instanceof BaseApp) {
            ((BaseApp) getApplication()).getActivityManager().pushActivity(this);
        }
        mViewModel = createViewModel(); //创建ViewModel
        if (mViewModel != null) {
            mViewModel.attachView((V) this); //View与ViewModel建立关系
        }

        initVarAndView(getLayoutInflater(), null, savedInstanceState);
        initTransition();
        midfield();
        initEvent();
        initDoing();
    }

    private void initTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setEnterTransition(new Slide(Gravity.RIGHT));
//            getWindow().setExitTransition(new Slide(Gravity.RIGHT));

            Slide slideIn = new Slide();
            slideIn.setDuration(200);
            slideIn.setMode(Visibility.MODE_IN);
            slideIn.setSlideEdge(android.view.Gravity.RIGHT);
            getWindow().setEnterTransition(slideIn);

            Slide slideReturn = new Slide();
            slideReturn.setDuration(200);
            slideReturn.setSlideEdge(android.view.Gravity.RIGHT);
            slideReturn.setMode(Visibility.MODE_OUT);
            getWindow().setReturnTransition(slideReturn);
        }
    }

    //创建ViewModel
    protected abstract T createViewModel();

    @Override
    public final View initVarAndView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initVarAndView(savedInstanceState);
        return null;
    }

    //初始化变量和界面
    public abstract void initVarAndView(Bundle savedInstanceState);

    //用于继承自BaseActivity的基类 进行一些初始化，一般情况下，不用重写
    public void midfield() {
    }

    //初始化事件
    public void initEvent() {

    }

    //开始执行
    public void initDoing() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        printLifeCycle("onStart : ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        printLifeCycle("onResume : ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        printLifeCycle("onPause : ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        printLifeCycle("onStop : ");
    }

    @Override
    protected void onDestroy() {
        printLifeCycle("onDestroy : ");
        if (eventBusHelper != null) {
            eventBusHelper.unregister(this);
        }
        if (mViewModel != null) {
            mViewModel.detachView();
        }
        if (getApplication() instanceof BaseApp) {
            ((BaseApp) getApplication()).getActivityManager().finishActivity(this);
        }
        super.onDestroy();
    }

    private void printLifeCycle(String msg) {
        if (IS_PRINT_LIFECYCLE) {
            Log.i(TAG, getClass().getSimpleName() + " - " + msg);
        }
    }

    protected Pair<View, String> getSharedElement1() {
        return null;
    }

    protected Pair<View, String> getSharedElement2() {
        return null;
    }

    protected Pair<View, String> getSharedElement3() {
        return null;
    }

    public ActivityOptionsCompat getActivityOptionsCompat() {
        if (getSharedElement1() != null && getSharedElement2() != null && getSharedElement3() != null) {
            return ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    getSharedElement1(), getSharedElement2(), getSharedElement3());
        } else if (getSharedElement1() != null && getSharedElement2() != null) {
            return ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    getSharedElement1(), getSharedElement2());
        } else if (getSharedElement1() != null) {
            return ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    getSharedElement1());
        } else {
            return null;
        }
    }

    private EventBusHelper eventBusHelper;

    public void initEventBus() {
        if (eventBusHelper == null) {
            eventBusHelper = new EventBusHelper();
        }
        eventBusHelper.setEnable(true);
        eventBusHelper.register(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("wangjianping", " baseactivity onBackPressed ");
        ((BaseApp) getApplication()).getActivityManager().finishActivity(this);
    }
}
