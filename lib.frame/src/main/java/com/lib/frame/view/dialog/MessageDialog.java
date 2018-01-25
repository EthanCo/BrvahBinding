package com.lib.frame.view.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @Description 消息提示对话框
 * Created by EthanCo on 2016/3/22.
 */
public class MessageDialog {
    private static Reference<MaterialDialog> dialogRef;

    public static void show(Context context) {
        show(context, "正在加载", "请稍等...");
    }

    public static void show(Context context, String title, String content) {
        dismiss();
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText("确定")
                .negativeText("取消")
                .show();
        dialogRef = new WeakReference(dialog);
    }

    public static void show(Context context, int title, int content) {
        show(context, context.getString(title), context.getString(content));
    }

    public static void dismiss() {
        if (dialogRef != null) {
            MaterialDialog dialog = dialogRef.get();
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
