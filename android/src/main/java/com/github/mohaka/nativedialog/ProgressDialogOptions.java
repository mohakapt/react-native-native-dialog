package com.github.mohaka.nativedialog;

import android.app.Activity;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.facebook.react.bridge.ReadableMap;

public class ProgressDialogOptions extends DialogOptions {
    public final static int SIZE_LARGE = 100;
    public final static int SIZE_SMALL = 101;

    private int progressSize;

    public ProgressDialogOptions() {
    }

    public ProgressDialogOptions(ReadableMap map) {
        this.populate(map);
    }

    public int getProgressSize() {
        return progressSize;
    }

    public void setProgressSize(int progressSize) {
        this.progressSize = progressSize;
    }

    public void setProgressSize(String size) {
        this.setTheme("small".equalsIgnoreCase(size) ? SIZE_SMALL : SIZE_LARGE);
    }

    @Override
    public void populate(ReadableMap map) {
        super.populate(map);
        if (map.hasKey("size")) setTheme(map.getString("size"));
    }

    public AlertDialog showDialog(Activity activity, @StyleRes int lightDialogTheme, @StyleRes int darkDialogTheme) {
        AlertDialog.Builder builder = buildDialog(activity, lightDialogTheme, darkDialogTheme);

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
