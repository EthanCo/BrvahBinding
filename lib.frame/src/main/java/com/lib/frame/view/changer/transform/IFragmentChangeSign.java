package com.lib.frame.view.changer.transform;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Fragment改变接口
 *
 * @author EthanCo
 * @since 2017/10/25
 */

public interface IFragmentChangeSign {
    void execChangeFragment(Fragment fragment, @Nullable ChangeConfig config);
}
