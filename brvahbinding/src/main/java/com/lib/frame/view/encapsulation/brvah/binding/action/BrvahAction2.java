package com.lib.frame.view.encapsulation.brvah.binding.action;

/**
 * onBrvahItemClickListener
 *
 * @author EthanCo
 * @since 2017/11/23
 */

public interface BrvahAction2<T, T2> {
    void call(T param1, T2 param2);
}
