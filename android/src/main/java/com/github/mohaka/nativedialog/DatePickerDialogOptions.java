package com.github.mohaka.nativedialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.facebook.react.bridge.ReadableMap;

public class DatePickerDialogOptions extends DialogOptions {

    public DatePickerDialogOptions() {
    }

    public DatePickerDialogOptions(ReadableMap map) {
        this.populate(map);
    }

    @Override
    public void populate(ReadableMap map) {
        super.populate(map);
    }

    @Override
    protected AlertDialog.Builder buildDialog(Activity activity, @StyleRes int dialogTheme, @StyleRes int lightDialogTheme) {
        int dialogThemeResId;
        if (getTheme() == THEME_DARK)
            dialogThemeResId = dialogTheme == 0 ? R.style.Theme_AppCompat_Dialog_Alert : dialogTheme;
        else
            dialogThemeResId = lightDialogTheme == 0 ? R.style.Theme_AppCompat_Light_Dialog_Alert : lightDialogTheme;

        DatePickerDialog dialog = new DatePickerDialog(activity, dialogThemeResId, (view, year, month, dayOfMonth) -> {

        }, 1, 1, 1);
        return null;
    }

    public AlertDialog showDialog(Activity activity, @StyleRes int dialogTheme, @StyleRes int lightDialogTheme) {
        AlertDialog.Builder builder = buildDialog(activity, dialogTheme, lightDialogTheme);

        builder.setView(R.layout.dialog_progress);
        builder.setMessage(null);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(isCancelOnTouchOutside());
        alertDialog.show();

        TextView txtMessage = alertDialog.findViewById(R.id.txtMessage);
        txtMessage.setText(getMessage());

        return alertDialog;
    }
}
