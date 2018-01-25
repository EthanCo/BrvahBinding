package com.lib.frame.viewmodel;

import android.databinding.ObservableBoolean;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.lib.frame.viewmodel._enum.EmptyViewType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 绑定的ViewModel
 *
 * @param <T> BaseView
 * @param <B> Bean对象
 * @author EthanCo
 * @since 2017/11/22
 */
public abstract class LoadMoreBindingViewModel<T, B> extends BaseBindingViewModel<T, B> {
    protected Map<String, Object> params = new HashMap<>();
    public LoadMoreView loadMoreView;
    public ObservableBoolean loadMoreEnd;
    public ObservableBoolean loadMoreEnable;
    public ObservableBoolean loadMoreSuccess;
    public BaseQuickAdapter.RequestLoadMoreListener loadMoreListener;
    protected int mPage;
    public static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public void attachView(T view) {
        super.attachView(view);
        this.loadMoreView = getLoadMoreView();
        this.loadMoreEnd = new ObservableBoolean();
        this.loadMoreEnable = new ObservableBoolean();
        this.loadMoreSuccess = new ObservableBoolean();
        this.loadMoreListener = getLoadMoreListener();
    }

    protected LoadMoreView getLoadMoreView() {
        return null;
    }

    protected BaseQuickAdapter.RequestLoadMoreListener getLoadMoreListener() {
        return new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        };
    }

    @Override
    public void load() {
        //mPage = 0;
        //loadMoreEnable.set(false);
        load(mPage);
    }

    @Override
    public void reload() {
        mPage = 0;
        //loadMoreEnable.set(false);
        super.reload();
    }

    @Override
    protected void load(Flowable<List<B>> flowable) {
        loadMoreEnable.set(false);
        disposable = flowable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<B>>() {
                    @Override
                    public void accept(List<B> result) throws Exception {
                        setData(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (isSwipeRefreshing.get() || mPage == 0) {
                            emptyResId.set(getEmptyViewRes(EmptyViewType.ERROR));
                            loadMoreEnable.set(true);
                        } else {
                            loadMoreSuccess.set(false);
                            loadMoreSuccess.notifyChange();
                        }
                        isSwipeRefreshing.set(false);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        loadMoreEnable.set(true);
                        emptyResId.set(getEmptyViewRes(EmptyViewType.EMPTY));
                        isSwipeRefreshing.set(false);
                        mPage++;
                    }
                });
    }

    private void setData(List<B> result) {
        final int size = result == null ? 0 : result.size();
        addItems(result);
        if (size < getPageSize()) {
            //第一页如果不够一页就不显示没有更多数据布局
            loadMoreEnd.set(mPage == 0);
            loadMoreEnd.notifyChange();
        } else {
            loadMoreSuccess.set(true);
            loadMoreSuccess.notifyChange();
        }
    }

    private void loadMore() {
        load();
    }

    public abstract void load(int mPage);

    protected static int getPageSize() {
        return DEFAULT_PAGE_SIZE;
    }
}
