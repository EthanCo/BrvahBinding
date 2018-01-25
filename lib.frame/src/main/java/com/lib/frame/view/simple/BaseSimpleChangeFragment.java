package com.lib.frame.view.simple;

import com.lib.frame.view.BaseChangeFragment;
import com.lib.frame.view.abs.ICreate;
import com.lib.frame.viewmodel.BaseViewModel;

/**
 * @Description Fragment 基类，用作给Kotlin继承用
 * Created by EthanCo on 2017/6/2.
 */
public abstract class BaseSimpleChangeFragment extends BaseChangeFragment implements ICreate {

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }
}
