package com.lib.frame.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.annotation.Nullable;

import com.lib.frame.view.encapsulation.brvah.binding.BaseItemBindingAdapter;
import com.lib.frame.view.encapsulation.brvah.binding.BindingViewHolder;
import com.lib.frame.view.encapsulation.brvah.binding.BrvahItemBinding;
import com.lib.frame.view.encapsulation.brvah.binding.BrvahViewBinding;
import com.lib.frame.viewmodel._enum.EmptyViewType;

import java.util.List;

/**
 * 绑定的ViewModel
 *
 * @param <T> BaseView
 * @param <B> Bean对象
 * @author EthanCo
 * @since 2017/11/22
 */
public abstract class BaseBindingViewModel<T, B> extends BaseBrvahViewModel<T, B> {
    //数据集合
    //public ObservableArrayList<B> items;

    //ItemBinding 包括 BR 和 adapter item layout
    public BrvahItemBinding<B> itemBinding;

    //绑定的Adapter
    public BaseItemBindingAdapter<B, BindingViewHolder> bindingAdapter;

    //Empty View Layout Id
    public ObservableInt emptyResId;

    //是否处于刷新状态
    public ObservableBoolean isSwipeRefreshing;

    //HeadView BrvahViewBinding
    public BrvahViewBinding headerBinding;

    //SwipeRefreshLayout 刷新监听回调
    //public SwipeRefreshLayout.OnRefreshListener refreshListener;

    //Empty View 点击回调
    //public View.OnClickListener emptyOnClickListener = getEmptyOnClickListener();

    //protected Disposable disposable;

    protected BrvahViewBinding getHeaderBinding() {
        return null;
    }

    /*protected View.OnClickListener getEmptyOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emptyResId.get() != getEmptyViewRes(EmptyViewType.LOADING)) {
                    reload();
                    emptyResId.set(getEmptyViewRes(EmptyViewType.LOADING));
                }
            }
        };
    }*/

    /*protected SwipeRefreshLayout.OnRefreshListener getOnSwipeRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isSwipeRefreshing.set(true);
                reload();
            }
        };
    }*/

    @Override
    public void attachView(T view) {
        super.attachView(view);

        this.itemBinding = getItemBinding();
        this.bindingAdapter = getBindingAdapter();
        this.emptyResId = new ObservableInt(getEmptyViewRes(EmptyViewType.LOADING));
        this.isSwipeRefreshing = new ObservableBoolean();
        this.headerBinding = getHeaderBinding();
    }

    @Override
    protected void initItems() {
        this.items = new ObservableArrayList();
    }

    /**
     * 重新加载
     */
    /*public void reload() {
        dispose();
        if (isSwipeRefreshing.get()) {
            emptyResId.set(getEmptyViewRes(EmptyViewType.REFRESH));
        } else {
            emptyResId.set(getEmptyViewRes(EmptyViewType.LOADING));
        }
        items.clear();
        load();
    }*/

    /**
     * 加载EmptyView Layout Id
     *
     * @param type 类型
     * @return
     */
    /*protected int getEmptyViewRes(EmptyViewType type) {
        switch (type) {
            case LOADING:
                return R.layout.layout_frame_loading_view;
            case ERROR:
                return R.layout.layout_frame_error_view;
            case REFRESH:
                return R.layout.layout_frame_refresh_view;
            case EMPTY:
            default:
                return R.layout.layout_frame_empty_view;

        }
    }*/

    /**
     * 获取绑定的Adapter
     *
     * @return 为空则为默认Adapter
     */
    @Nullable
    protected BaseItemBindingAdapter<B, BindingViewHolder> getBindingAdapter() {
        return null;
    }

    protected void addItems(List<B> increment) {
        if (increment != null && increment.size() > 0) {
            items.addAll(increment);
        }
    }

    /*protected void load(Flowable<List<B>> flowable) {
        disposable = flowable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<B>>() {
                    @Override
                    public void accept(List<B> result) throws Exception {
                        addItems(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        emptyResId.set(getEmptyViewRes(EmptyViewType.ERROR));
                        isSwipeRefreshing.set(false);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        emptyResId.set(getEmptyViewRes(EmptyViewType.EMPTY));
                        isSwipeRefreshing.set(false);
                    }
                });
    }*/

    @Override
    protected void onSwipeRefreshChange(boolean isRefresh) {
        isSwipeRefreshing.set(isRefresh);
    }

    @Override
    protected void onEmptyViewChange(int emptyViewRes) {
        emptyResId.set(emptyViewRes);
    }

    @Override
    protected boolean getSwipeRefreshing() {
        return isSwipeRefreshing.get();
    }

    @Override
    protected int getEmptyResId() {
        return emptyResId.get();
    }

    /*@Override
    @CallSuper
    public void detachView() {
        super.detachView();
        dispose();
    }*/

    /*private void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }*/

    //============================= 抽象方法 ==============================/

    protected abstract BrvahItemBinding<B> getItemBinding();
}
