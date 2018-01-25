package com.example.fengh.recyclerviewtest.brvah_binding.viewmodel;

import com.example.fengh.recyclerviewtest.BR;
import com.example.fengh.recyclerviewtest.R;
import com.example.fengh.recyclerviewtest.brvah_binding.model.BrvahModel;
import com.example.fengh.recyclerviewtest.brvah_binding.view.BrvahView;
import com.lib.frame.view.encapsulation.brvah.binding.BrvahItemBinding;
import com.lib.frame.viewmodel.BaseBindingViewModel;

/**
 * BrvahViewModel
 *
 * @author EthanCo
 * @since 2018/1/17
 */
public class BrvahViewModel extends BaseBindingViewModel<BrvahView,String> {
    private BrvahModel model = new BrvahModel();

    @Override
    public void load() {
        load(model.load());
    }

    @Override
    protected BrvahItemBinding getItemBinding() {
        return BrvahItemBinding.of(BR.name,R.layout.item_text);
    }
}
