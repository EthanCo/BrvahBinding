package com.lib.frame.view.encapsulation.brvah.binding;

/**
 * Callback for setting up a {@link BrvahItemBinding} for an item in the collection.
 *
 * @param <T> the item type
 */
public interface BrvahOnItemBind<T> {
    /**
     * Called on each item in the collection, allowing you to modify the given {@link BrvahItemBinding}.
     * Note that you should not do complex processing in this method as it's called many times.
     */
    void onItemBind(BrvahItemExtra<T> extra, int position, T item);
}
