package com.lib.frame.view.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @Description 进度条 对话框
 * Created by EthanCo on 2016/3/22.
 */
public class ProgressDialog {
    private static Reference<MaterialDialog> dialogRef;

    public static void show(Context context) {
        show(context, "正在下载");
    }

    public static void show(Context context, String title) {
        dismiss();
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(title)
                .cancelable(true)
                .progress(false, 100)
                .show();
        dialogRef = new WeakReference(dialog);
    }

    public static void show(Context context, int title) {
        show(context, context.getString(title));
    }

    public static void setProgress(int progress) {
        if (dialogRef != null) {
            MaterialDialog dialog = dialogRef.get();
            dialog.setProgress(progress);
        }
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
