package com.lib.frame.view.encapsulation.brvah.binding;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;

/**
 * BrvahItemBinding
 *
 * @author EthanCo
 * @since 2017/11/4
 */

public final class BrvahItemBinding<T> {
    private int variableId;
    private int layoutRes;
    private BrvahItemExtra<T> extra = new BrvahItemExtra<>();
    //private BrvahOnItemBind<T> onItemBind;

    public static <T> BrvahItemBinding<T> of(int variableId, @LayoutRes int layoutRes) {
        return new BrvahItemBinding<T>().set(variableId, layoutRes);
    }

    public final BrvahItemBinding<T> set(int variableId, @LayoutRes int layoutRes) {
        this.variableId = variableId;
        this.layoutRes = layoutRes;
        return this;
    }

    public final BrvahItemBinding<T> variableId(int variableId) {
        this.variableId = variableId;
        return this;
    }

    public final BrvahItemBinding<T> layoutRes(@LayoutRes int layoutRes) {
        this.layoutRes = layoutRes;
        return this;
    }

    public final BrvahItemBinding<T> bindExtra(int variableId, Object value) {
        extra.bindExtra(variableId, value);
        return this;
    }

    public final BrvahItemBinding<T> clearExtras() {
        extra.clearExtras();
        return this;
    }

    public BrvahItemBinding<T> removeExtra(int variableId) {
        extra.removeExtra(variableId);
        return this;
    }

    protected boolean bind(ViewDataBinding binding, T item) {
        if (this.variableId == BrvahItemExtra.VAR_NONE) {
            return false;
        } else {
            boolean result = binding.setVariable(this.variableId, item);
            if (!result) {
                BrvahBindingUtil.throwMissingVariable(binding, this.variableId, this.layoutRes);
            }

            extra.bind(binding, item);
            return true;
        }
    }

    public int getVariableId() {
        return variableId;
    }

    public int getLayoutRes() {
        return layoutRes;
    }

   /* public BrvahItemBinding<T> setOnItemBind(BrvahOnItemBind<T> onItemBind) {
        this.onItemBind = onItemBind;
        return this;
    }

    public BrvahItemExtra<T> getExtra() {
        return extra;
    }

    public BrvahOnItemBind<T> getOnItemBind(){
        return onItemBind;
    }*/
}
