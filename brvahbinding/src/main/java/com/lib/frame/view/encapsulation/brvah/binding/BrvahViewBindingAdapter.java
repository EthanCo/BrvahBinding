package com.lib.frame.view.encapsulation.brvah.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.heiko.brvahbinding.R;

import java.util.List;

public class BrvahViewBindingAdapter {
    private static final String TAG = "Z-LocalBindingAdapter";

    /**
     * @param recyclerView
     * @param brvahItemBinding     BR和item layout
     * @param items                数据
     * @param adapter              adapter
     * @param layoutManagerFactory LayoutManager
     * @param headerViewBinding    头部ViewBinding
     * @param footerViewBinding    尾部ViewBinding
     * @param emptyResId           EmptyView id
     * @param dividerFactory       分割线
     * @param loadMoreListener     加载更多监听
     * @param loadMoreView         加载更多View
     * @param <T>                  数据类型
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"brvah_itemBinding", "brvah_items", "brvah_adapter", "brvah_layoutManager",
            "brvah_header_itemBinding", "brvah_footer_itemBinding",
            "brvah_empty", "brvah_empty_clickListener", "brvah_dividerManager", "brvah_loadMoreListener","brvah_load_more_view"}, requireAll = false)
    public static <T> void setBrvahAdapter(RecyclerView recyclerView, final BrvahItemBinding<T> brvahItemBinding,
                                           List<T> items, BaseBindingAdapter adapter, BrvahLayoutManagers.LayoutManagerFactory layoutManagerFactory,
                                           BrvahViewBinding<T> headerViewBinding, BrvahViewBinding<T> footerViewBinding,
                                           Integer emptyResId, View.OnClickListener emptyClickListener,
                                           BrvahDividerManagers.DividerFactory dividerFactory,
                                           BaseQuickAdapter.RequestLoadMoreListener loadMoreListener,LoadMoreView loadMoreView) {
        if (brvahItemBinding == null) {
            throw new IllegalArgumentException("LocalViewBindingAdapter must not be null");
        }

        RecyclerView.Adapter oldAdapter = recyclerView.getAdapter();
        Log.i(TAG, "setBrvahAdapter items.size:" + items.size() + " oldAdapter:"
                + oldAdapter + " brvahItemBinding.getLayoutRes():" + brvahItemBinding.getLayoutRes()
                + " brvahItemBinding.getVariableId():" + brvahItemBinding.getVariableId());
        /*if (items.size() == 0) {
            return;
        }*/
        Log.i(TAG, "setBrvahAdapter items:" + items + " items.hasCode:" + items.hashCode());
        Context context = recyclerView.getContext();

        if (adapter == null) { //items != null && items.size() > 0
            if (oldAdapter == null) {
                final int variableId = brvahItemBinding.getVariableId();
                final int layoutId = brvahItemBinding.getLayoutRes();
                Log.i(TAG, "adapter = new BaseBindingAdapter:" + variableId + " layoutId:" + layoutId);
                adapter = new SimpleItemBindingAdapter(brvahItemBinding, items);
            } else {
                adapter = (BaseBindingAdapter<T, BindingViewHolder>) oldAdapter;
            }
        }
        Log.i(TAG, "---->>>oldAdapter:" + oldAdapter);
        if (adapter != null) {
            Log.i(TAG, "setBrvahAdapter items.size:" + items.size() + " adapter:"
                    + adapter+" items:"+items +" adapter.item:"+adapter.getData()+" size:"+items.size());
        }

        if (oldAdapter == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            if (layoutManagerFactory == null) {
                Log.i(TAG, "setLayoutManager");
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                Log.i(TAG, "setLayoutManager");
                recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
            }
            if (headerViewBinding != null) {
                Log.i(TAG, "setHeaderView");
                //adapter.setHeaderView(bindingView(headerViewBinding, inflater).getRoot());
                T data = headerViewBinding.getData();
                int headerLayoutRes = headerViewBinding.getLayoutRes();
                ViewDataBinding viewBinding = DataBindingUtil.inflate(inflater, headerLayoutRes, null, false);
                Log.i(TAG, "bindingView : "+data+" headerLayoutRes:"+headerLayoutRes+" getVariableId:"+headerViewBinding.getVariableId());
                headerViewBinding.bind(viewBinding);
                adapter.setHeaderView(viewBinding.getRoot());
            }
            if (footerViewBinding != null) {
                Log.i(TAG, "setFooterView");
                adapter.setFooterView(bindingView(footerViewBinding, inflater).getRoot());
            }
            if (dividerFactory != null) {
                RecyclerView.ItemDecoration itemDecoration = dividerFactory.create(recyclerView);
                if (itemDecoration != null) {
                    Log.i(TAG, "addItemDecoration");
                    recyclerView.addItemDecoration(itemDecoration);
                }
            }
            if (loadMoreListener != null) {
                Log.i(TAG, "setOnLoadMoreListener");
                adapter.setOnLoadMoreListener(loadMoreListener, recyclerView);
                if (loadMoreView != null){
                    adapter.setLoadMoreView(loadMoreView);
                }else{
                    adapter.setLoadMoreView(new SimpleLoadMoreView());
                }
            }
            Log.i(TAG, "setAdapter:" + adapter);
            //adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
            recyclerView.setAdapter(adapter);
        }
        if (emptyResId != null) {
            Log.i(TAG, "setEmptyView:" + adapter);
            adapter.setEmptyView(emptyResId, recyclerView);
        }
        if (emptyClickListener != null && adapter.getEmptyView() != null) {
            Log.i(TAG, "setEmptyView OnClick");
            adapter.getEmptyView().setOnClickListener(emptyClickListener);
        }


        /*if (oldAdapter == null) {
            Log.i(TAG, "oldAdapter == null");
            LayoutInflater inflater = LayoutInflater.from(context);
            if (layoutManagerFactory == null) {
                Log.i(TAG, "setLayoutManager");
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                Log.i(TAG, "setLayoutManager");
                recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
            }

            if (emptyResId != null) {
                Log.i(TAG, "setEmptyView:" + adapter);
                adapter.setEmptyView(emptyResId, recyclerView);
            }
            Log.i(TAG, "setAdapter:" + adapter);
            recyclerView.setAdapter(adapter);
        }else{
            Log.i(TAG, "oldAdapter != null");
        }*/
    }

    private static <T> ViewDataBinding bindingView(BrvahViewBinding<T> brvahViewBinding, LayoutInflater inflater) {
        T data = brvahViewBinding.getData();
        int headerLayoutRes = brvahViewBinding.getLayoutRes();
        ViewDataBinding viewBinding = DataBindingUtil.inflate(inflater, headerLayoutRes, null, false);
        Log.i(TAG, "bindingView : "+data);
        brvahViewBinding.bind(viewBinding, data);
        return viewBinding;
    }

    /**
     * @param loadMoreEnd          是否还有数据
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"brvah_load_more_end"})
    public static void onLoadMoreEnd(RecyclerView recyclerView, Boolean loadMoreEnd){
        Log.i(TAG, "onLoadMoreEnd");
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null && adapter instanceof BaseQuickAdapter){
            ((BaseQuickAdapter)adapter).loadMoreEnd(loadMoreEnd);
        }
    }

    /**
     * @param loadMoreEnable       允许加载更多
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"brvah_load_more_enable"})
    public static void onLoadMoreEnable(RecyclerView recyclerView,Boolean loadMoreEnable){
        Log.i(TAG, "onLoadMoreEnable");
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null && adapter instanceof BaseQuickAdapter){
            ((BaseQuickAdapter)adapter).setEnableLoadMore(loadMoreEnable);
        }
    }

    /**
     * @param loadMoreSuccess      是否加载成功
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"brvah_load_more_success"})
    public static void onLoadMoreSuccess(RecyclerView recyclerView,Boolean loadMoreSuccess){
        Log.i(TAG, "onLoadMoreSuccess");
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null && adapter instanceof BaseQuickAdapter){
            if (loadMoreSuccess){
                ((BaseQuickAdapter)adapter).loadMoreComplete();
            }else{
                ((BaseQuickAdapter)adapter).loadMoreFail();
            }
        }
    }

    /**
     * @param swipeRefreshLayout
     * @param onRefreshListener
     * @param isRefreshing
     * @param colorSchemeRes
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"refresh_listener", "is_refreshing", "color_scheme_res"}, requireAll = false)
    public static <T> void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout,
                                                 SwipeRefreshLayout.OnRefreshListener onRefreshListener,
                                                 Boolean isRefreshing, @ColorRes Integer colorSchemeRes) {

        Log.i("Z-Swipe", "setSwipeRefreshLayout isRefreshing:" + isRefreshing);
        if (onRefreshListener != null) {
            swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        }
        if (isRefreshing != null) {
            swipeRefreshLayout.setRefreshing(isRefreshing);
        }
        if (colorSchemeRes == null) {
            swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        } else {
            swipeRefreshLayout.setColorSchemeResources(colorSchemeRes);
        }
    }
}
