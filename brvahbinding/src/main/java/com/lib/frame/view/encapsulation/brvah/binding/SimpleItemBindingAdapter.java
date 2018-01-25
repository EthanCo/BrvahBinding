package com.lib.frame.view.encapsulation.brvah.binding;

import java.util.List;

/**
 * 内部使用的BrvahBindingAdapter
 *
 * @author EthanCo
 * @since 2017/11/23
 */

public class SimpleItemBindingAdapter<T> extends BaseItemBindingAdapter<T, BindingViewHolder> {

    public SimpleItemBindingAdapter(BrvahItemBinding<T> itemBinding, List<T> data) {
        super(itemBinding, data);
    }
}
