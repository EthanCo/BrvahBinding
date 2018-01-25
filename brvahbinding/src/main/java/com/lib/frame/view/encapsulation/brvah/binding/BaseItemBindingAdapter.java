package com.lib.frame.view.encapsulation.brvah.binding;

import android.support.annotation.CallSuper;
import android.util.Log;

import java.util.List;

/**
 * BaseBrvahBindingAdapter
 *
 * @author EthanCo
 * @since 2017/11/23
 */

public abstract class BaseItemBindingAdapter<T, V extends BindingViewHolder> extends BaseBindingAdapter<T, V> {
    private final BrvahItemBinding<T> itemBinding;

    public BaseItemBindingAdapter(BrvahItemBinding<T> itemBinding, List<T> data) {
        super(itemBinding.getLayoutRes(), data);
        this.itemBinding = itemBinding;
    }

    @Override
    @CallSuper
    protected void convert(V helper, T item) {
        Log.i(TAG, "convert item:" + item + itemBinding.getVariableId() + " layoutId:" + itemBinding.getLayoutRes());
        Log.i("Z-Swipe", "convert getTag:" + helper.getBinding() + " view:" + helper.itemView);
        itemBinding.bind(helper.getBinding(), item);
        //helper.getBinding().executePendingBindings();
    }
}
