package com.github.mohaka.nativedialog;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.facebook.react.bridge.ReadableMap;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class TipDialogOptions extends DialogOptions {
    private Drawable drawable;
    private String dialogId;
    private String dontShowAgain;
    private boolean force;

    public TipDialogOptions() {
    }

    public TipDialogOptions(ReadableMap map) {
        this.populate(map);
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public String getDialogId() {
        return dialogId;
    }

    public String getDontShowAgain() {
        return dontShowAgain;
    }

    public boolean isForce() {
        return force;
    }

    public boolean hasDontShowAgain() {
        return !(TextUtils.isEmpty(getDialogId()) && TextUtils.isEmpty(dontShowAgain));
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setDialogId(String id) {
        this.dialogId = id;
    }

    public void setDontShowAgain(String dontShowAgain) {
        this.dontShowAgain = dontShowAgain;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    @Override
    public void populate(ReadableMap map) {
        super.populate(map);

        if (map.hasKey("imageUri")) {
            String imageUri = map.getString("imageUri");

            InputStream stream = null;
            Drawable drawable = null;
            try {
                URL imageUrl = new URL(imageUri);
                stream = imageUrl.openConnection().getInputStream();
                drawable = BitmapDrawable.createFromStream(stream, "image");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (drawable != null) setDrawable(drawable);
        }

        if (map.hasKey("id")) setDialogId(map.getString("id"));
        if (map.hasKey("dontShowAgain")) setDontShowAgain(map.getString("dontShowAgain"));
        if (map.hasKey("force")) setForce(map.getBoolean("force"));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean dontShowAgainValue = preferences.getBoolean("__dialog_" + getDialogId(), false);
        if (!isForce() && hasDontShowAgain()) if (dontShowAgainValue) return null;

        AlertDialog.Builder builder = super.buildDialog();
        builder.setTitle(null);
        builder.setMessage(null);
        builder.setView(R.layout.dialog_tip);

        builder.setOnDismissListener(dialog -> {
            if (hasDontShowAgain()) {
                CheckBox chbDontShowAgain = ((AlertDialog) dialog).findViewById(R.id.chbDontShowAgain);
                preferences.edit().putBoolean("__dialog_" + getDialogId(), chbDontShowAgain.isChecked()).apply();
            }

            if (getDismissListener() != null)
                getDismissListener().onDismiss(dialog);
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(isCancelOnTouchOutside());
        alertDialog.show();

        ImageView imgTipImage = alertDialog.findViewById(R.id.imgTipImage);
        TextView txtTitle = alertDialog.findViewById(R.id.txtTitle);
        TextView txtMessage = alertDialog.findViewById(R.id.txtDescription);
        CheckBox chbDontShowAgain = alertDialog.findViewById(R.id.chbDontShowAgain);

        imgTipImage.setImageDrawable(getDrawable());
        txtTitle.setText(getTitle());
        txtMessage.setText(getMessage());
        chbDontShowAgain.setText(getDontShowAgain());
        chbDontShowAgain.setChecked(dontShowAgainValue);
        chbDontShowAgain.setVisibility(hasDontShowAgain() ? View.VISIBLE : View.GONE);

        return alertDialog;
    }
}
