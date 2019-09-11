package com.phayaotown.travel.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.phayaotown.travel.R;

public class DialogUtil {

    private static DialogUtil instance;

    public static DialogUtil getInstance() {
        if (instance == null)
            instance = new DialogUtil();
        return instance;
    }

    public void showDialog(@NonNull Context context, @StringRes int title, @StringRes int message
            , DialogInterface.OnClickListener callback) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.btn_ok), callback)
                .setNegativeButton(context.getString(R.string.btn_cancel), null)
                .create()
                .show();
    }

    public void showDialogWarning(@NonNull Context context, @StringRes int title, @StringRes int message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.btn_ok), null)
                .create()
                .show();
    }

    public void showDialogSingleChoice(@NonNull Context context, @StringRes int title
            , @NonNull CharSequence[] items, int pos, DialogInterface.OnClickListener callback) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setSingleChoiceItems(items, pos, null)
                .setPositiveButton(R.string.btn_ok, callback)
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }
}
