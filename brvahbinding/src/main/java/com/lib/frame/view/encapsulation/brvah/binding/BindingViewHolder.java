package com.lib.frame.view.encapsulation.brvah.binding;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.heiko.brvahbinding.R;

public class BindingViewHolder extends BaseViewHolder {

        public BindingViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }