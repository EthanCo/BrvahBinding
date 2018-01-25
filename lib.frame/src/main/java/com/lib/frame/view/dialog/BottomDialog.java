package com.lib.frame.view.dialog;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

/**
 * 底部对话框
 *
 * @author EthanCo
 * @since 2017/11/28
 */

public class BottomDialog {
    public static BottomSheetDialog show(Context context, View contentView) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(contentView);
        dialog.show();
        return dialog;
    }
}
