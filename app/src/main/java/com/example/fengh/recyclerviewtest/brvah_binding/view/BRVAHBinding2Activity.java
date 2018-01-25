package com.example.fengh.recyclerviewtest.brvah_binding.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fengh.recyclerviewtest.R;
import com.example.fengh.recyclerviewtest.brvah_binding.viewmodel.BrvahViewModel;
import com.example.fengh.recyclerviewtest.databinding.ActivityBrvahBinding2Binding;
import com.lib.frame.view.BaseActivity;

/**
 * TODO
 *
 * @author EthanCo
 * @since 2018/1/17
 */
public class BRVAHBinding2Activity extends BaseActivity<BrvahView, BrvahViewModel> implements BrvahView {
    private ActivityBrvahBinding2Binding binding;

    @Override
    public void initVarAndView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_brvah_binding_2);
        binding.setVm(mViewModel);
    }

    @Override
    protected BrvahViewModel createViewModel() {
        return new BrvahViewModel();
    }

    @Override
    public void initEvent() {
        super.initEvent();
        Button btnAdd = binding.getRoot().findViewById(R.id.btn_add);
        Button btnRemove = binding.getRoot().findViewById(R.id.btn_remove);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BRVAHBinding2Activity.this, "add", Toast.LENGTH_SHORT).show();
                mViewModel.items.add("item New");
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BRVAHBinding2Activity.this, "remove", Toast.LENGTH_SHORT).show();
                mViewModel.items.remove(0);
            }
        });
    }

    @Override
    public void initDoing() {
        super.initDoing();
        mViewModel.load();
    }
}