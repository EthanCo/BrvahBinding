package com.lib.frame.view.encapsulation.brvah.binding.anno;

import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Recyclerview 方向 注解
 *
 * @author EthanCo
 * @since 2017/11/15
 */

@IntDef({LinearLayoutManager.HORIZONTAL, LinearLayoutManager.VERTICAL})
@Retention(RetentionPolicy.SOURCE)
public @interface Orientation {
}
