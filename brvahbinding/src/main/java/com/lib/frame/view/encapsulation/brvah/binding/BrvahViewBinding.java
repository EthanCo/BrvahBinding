package com.lib.frame.view.encapsulation.brvah.binding;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.util.Log;

/**
 * BrvahItemBinding
 *
 * @author EthanCo
 * @since 2017/11/4
 */

public final class BrvahViewBinding<T> {
    private int variableId;
    private int layoutRes;
    private T data;
    private BrvahItemExtra<T> extra = new BrvahItemExtra<>();
    //private BrvahOnItemBind<T> onItemBind;

    public static BrvahViewBinding<Object> of(int layoutRes) {
        return new BrvahViewBinding<>().set(null, BrvahItemExtra.VAR_NONE, layoutRes);
    }

    public static <T> BrvahViewBinding<T> of(T data, int variableId, @LayoutRes int layoutRes) {
        return new BrvahViewBinding<T>().set(data, variableId, layoutRes);
    }

    public final BrvahViewBinding<T> set(T data, int variableId, @LayoutRes int layoutRes) {
        this.variableId = variableId;
        this.layoutRes = layoutRes;
        this.data = data;
        Log.i("Z----", "data:" + data);
        return this;
    }

    public final BrvahViewBinding<T> variableId(int variableId) {
        this.variableId = variableId;
        return this;
    }

    public final BrvahViewBinding<T> layoutRes(@LayoutRes int layoutRes) {
        this.layoutRes = layoutRes;
        return this;
    }

    public final BrvahViewBinding<T> bindExtra(int variableId, Object value) {
        extra.bindExtra(variableId, value);
        return this;
    }

    public final BrvahViewBinding<T> clearExtras() {
        extra.clearExtras();
        return this;
    }

    public BrvahViewBinding<T> removeExtra(int variableId) {
        extra.removeExtra(variableId);
        return this;
    }

    protected boolean bind(ViewDataBinding binding, T item) {
        if (this.variableId != BrvahItemExtra.VAR_NONE) {
            binding.setVariable(this.variableId, item);
        }

        extra.bind(binding, item);
        return true;
    }

    protected boolean bind(ViewDataBinding binding) {
        if (this.variableId != BrvahItemExtra.VAR_NONE) {
            Log.i("Z----", "variableId:"+variableId+" data:" + data);
            binding.setVariable(this.variableId, data);
        }

        extra.bind(binding, data);
        return true;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getVariableId() {
        return variableId;
    }

    public int getLayoutRes() {
        return layoutRes;
    }
}
