package com.example.fengh.recyclerviewtest.brvah_binding.model;

import com.lib.frame.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * TODO
 *
 * @author EthanCo
 * @since 2018/1/17
 */

public class BrvahModel implements BaseModel {
    public Flowable<List<String>> load() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 0; i++) {
            list.add("Item" + i);
        }
        return Flowable.just(list);
    }
}
