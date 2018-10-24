package com.example.fengh.recyclerviewtest;

import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

/**
 * 绑定
 *
 * @author EthanCo
 * @since 2017/11/2
 */

public class BindingListChangedCallBack extends ObservableList.OnListChangedCallback {
    private RecyclerView.Adapter adapter;

    public BindingListChangedCallBack(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onChanged(ObservableList sender) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
        adapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
        adapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
        adapter.notifyItemRangeRemoved(toPosition, itemCount);
    }

    @Override
    public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
        adapter.notifyItemRangeRemoved(positionStart, itemCount);
    }
}
