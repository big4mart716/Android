package com.example.personalcard.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import com.example.personalcard.R;

public class DialogUtils {

    public static void showConfirmDialog(Activity activity, String title, String message,
                                         Runnable onConfirm, Runnable onCancel) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.btn_confirm, (dialog, which) -> {
                    if (onConfirm != null) {
                        onConfirm.run();
                    }
                })
                .setNegativeButton(R.string.btn_cancel, (dialog, which) -> {
                    if (onCancel != null) {
                        onCancel.run();
                    }
                })
                .show();
    }

    public static void showChoiceDialog(Activity activity, String title, String[] items,
                                        OnChoiceListener listener) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setItems(items, (dialog, which) -> {
                    if (listener != null) {
                        listener.onChoice(which);
                    }
                })
                .show();
    }

    public static ProgressDialog showProgressDialog(Activity activity, String message) {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public interface OnChoiceListener {
        void onChoice(int which);
    }
}
