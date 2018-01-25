package com.lib.frame.view.encapsulation.brvah.binding;

import android.databinding.ObservableList;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * Recyclerview 数据绑定监听
 *
 * @author EthanCo
 * @since 2017/11/2
 */

public class BindingListChangedCallBack extends ObservableList.OnListChangedCallback {
    private BaseQuickAdapter adapter;
    public static final String TAG = "Z-BindingListChanged";

    public BindingListChangedCallBack(BaseQuickAdapter adapter) {
        Log.i(TAG, "Z-BindingListChangedCallBack");
        this.adapter = adapter;
    }

    @Override
    public void onChanged(ObservableList sender) {
        Log.i(TAG, "Z-onChanged");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
        Log.i(TAG, "Z-onItemRangeChanged");
        /*compatibilityDataSizeChanged(itemCount);
        int internalPosition = positionStart + adapter.getHeaderLayoutCount();
        if (adapter.getItemCount() == internalPosition) {
            adapter.notifyItemRangeChanged(internalPosition, 1);
        }else{
            adapter.notifyItemRangeChanged(internalPosition, adapter.getItemCount() - internalPosition);
        }*/
        adapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
        Log.i(TAG, "Z-onItemRangeInserted:" + sender.size() + " sender.hashCode:"
                + sender.hashCode() + " positionStart:" + positionStart + " itemCount:" + itemCount + "  real itemCount:" + adapter.getItemCount());
        /*if (adapter.getHeaderLayoutCount() > 0) {
            //adapter.addData();
            //adapter.remove();

        } else {
            if (positionStart == 0) {
                //adapter.notifyItemChanged(0);
            } else {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
            }
        }*/

        //adapter.addData();
        compatibilityDataSizeChanged(itemCount);
        if (positionStart == 0) {

        } else {
            adapter.notifyItemRangeInserted(positionStart + adapter.getHeaderLayoutCount(), itemCount);
        }
    }

    @Override
    public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
        Log.i(TAG, "Z-onItemRangeMoved");
        for (int i = 0; i < itemCount; i++) {
            adapter.notifyItemMoved(fromPosition + i, toPosition + i);
        }
    }

    @Override
    public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
        /*Log.i(TAG, "Z-onItemRangeRemoved positionStart:" + positionStart + " itemCount:"
                + itemCount+"  real itemCount:" + adapter.getItemCount()+" headerCount:"+adapter.getHeaderLayoutCount());
        if (positionStart == 0&&adapter.getItemCount()==adapter.getHeaderLayoutCount()) {
            Log.i(TAG, "Z-onItemRangeRemoved positionStart:---->111");
            //adapter.notifyDataSetChanged();
            adapter.notifyItemRangeRemoved(positionStart+adapter.getHeaderLayoutCount(), itemCount);
        }else{
            //adapter.notifyDataSetChanged();
            adapter.notifyItemRangeRemoved(positionStart, itemCount);
        }*/

        Log.i(TAG, "Z-onItemRangeRemoved positionStart:" + positionStart + " itemCount:"
                + itemCount + "  real itemCount:" + adapter.getItemCount() + " headerCount:" + adapter.getHeaderLayoutCount());
        /*if (positionStart == 0&&adapter.getItemCount()==adapter.getHeaderLayoutCount()) {
            Log.i(TAG, "Z-onItemRangeRemoved positionStart:---->111");
            //adapter.notifyDataSetChanged();
            adapter.notifyItemRangeRemoved(positionStart+adapter.getHeaderLayoutCount(), itemCount);
        }else{
            //adapter.notifyDataSetChanged();
            adapter.notifyItemRangeRemoved(positionStart, itemCount);
        }*/

        //adapter.remove();
        //if (adapter.getHeaderLayoutCount() > 0 ) {//&& positionStart < adapter.getHeaderLayoutCount()
        int internalPosition = positionStart + adapter.getHeaderLayoutCount();
//            positionStart += adapter.getHeaderLayoutCount();
//            itemCount -=adapter.getHeaderLayoutCount();
        compatibilityDataSizeChanged(0);
        if (adapter.getItemCount() == internalPosition) {
            adapter.notifyItemRangeRemoved(internalPosition, 1);
        }else{
            if (adapter.getHeaderLayoutCount() > 0) {
                adapter.notifyItemRangeRemoved(internalPosition, adapter.getItemCount() - internalPosition);
            }else{
                adapter.notifyItemRangeRemoved(internalPosition, itemCount);
            }
        }
    }

    private void compatibilityDataSizeChanged(int size) {
        final int dataSize = adapter.getData() == null ? 0 : adapter.getData().size();
        if (dataSize == size) {
            adapter.notifyDataSetChanged();
        }
    }
}
