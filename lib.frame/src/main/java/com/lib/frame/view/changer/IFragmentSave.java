package com.lib.frame.view.changer;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Fragment状态保存
 *
 * @author EthanCo
 * @since 2016/11/16
 */

public interface IFragmentSave  {
    void restoreInstanceState(Bundle savedInstanceState);

    void saveInstanceState(Bundle outState);

    void setCurrFragmentTag(Fragment fragment);
}
