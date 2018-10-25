package com.lib.frame.view.encapsulation.brvah.binding;

import android.databinding.ViewDataBinding;
import android.util.SparseArray;

/**
 * BrvahItemExtra
 *
 * @author EthanCo
 * @since 2017/11/15
 */

public class BrvahItemExtra<T> {
    public static final int VAR_NONE = 0;
    private static final int VAR_INVALID = -1;
    private static final int LAYOUT_NONE = 0;

    private SparseArray<Object> extraBindings;

    public final BrvahItemExtra<T> bindExtra(int variableId, Object value) {
        if (extraBindings == null) {
            extraBindings = new SparseArray<>(1);
        }
        extraBindings.put(variableId, value);
        return this;
    }

    public final BrvahItemExtra<T> clearExtras() {
        if (extraBindings != null) {
            extraBindings.clear();
        }
        return this;
    }

    public BrvahItemExtra<T> removeExtra(int variableId) {
        if (extraBindings != null) {
            extraBindings.remove(variableId);
        }
        return this;
    }

    public boolean bind(ViewDataBinding binding, T item) {
        if (extraBindings != null) {
            for (int i = 0, size = extraBindings.size(); i < size; i++) {
                int variableId = extraBindings.keyAt(i);
                Object value = extraBindings.valueAt(i);
                if (variableId != VAR_NONE) {
                    binding.setVariable(variableId, value);
                }
            }
        }
        return true;
    }
}
