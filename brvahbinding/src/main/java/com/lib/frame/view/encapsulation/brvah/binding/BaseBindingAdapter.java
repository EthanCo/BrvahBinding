package com.lib.frame.view.encapsulation.brvah.binding;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.heiko.brvahbinding.R;

import java.util.List;

/**
 * 基础绑定 BindingAdapter
 *
 * @author EthanCo
 * @since 2017/10/31
 */

public abstract class BaseBindingAdapter<T, K extends BindingViewHolder> extends BaseQuickAdapter<T, K> {
    public static final String TAG = "Z-BaseBindingAdapter";
    private BindingListChangedCallBack bindingListChangedCallBack;

    public BaseBindingAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        Log.i(TAG, "BaseBindingAdapter:" + data + " \ndata.hasCode:" + data.hashCode() + " mData:" + mData+" \nmData.hasCode:"+mData.hashCode());
        bindingData();
    }

    public BaseBindingAdapter(@Nullable List<T> data) {
        super(data);
        bindingData();
    }

    public BaseBindingAdapter(int layoutResId) {
        super(layoutResId, new ObservableArrayList<T>());
        bindingData();
    }

    private void bindingData() {
        Log.i(TAG, "bindingData:" + mData);
        if (mData instanceof ObservableList) {
            Log.i(TAG, "ObservableList:" + mData+ " \nmData.hasCode:" + mData.hashCode());
            bindingListChangedCallBack = new BindingListChangedCallBack(this);
            ((ObservableList) mData).addOnListChangedCallback(bindingListChangedCallBack);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        unboundData();
    }

    private void unboundData() {
        if (mData instanceof ObservableList) {
            if (bindingListChangedCallBack != null) {
                ((ObservableList) mData).removeOnListChangedCallback(bindingListChangedCallBack);
            }
        }
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        Log.i("Z-Swipe", "setTag:" + binding + " view:" + view);
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }
}
