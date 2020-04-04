package com.github.mohaka.nativedialog;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = buildDialog();

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
