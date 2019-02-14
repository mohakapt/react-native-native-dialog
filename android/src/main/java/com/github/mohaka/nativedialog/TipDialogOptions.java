package com.github.mohaka.nativedialog;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.react.bridge.ReadableMap;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class TipDialogOptions extends DialogOptions {
    private Drawable drawable;

    public TipDialogOptions() {
    }

    public TipDialogOptions(ReadableMap map) {
        this.populate(map);
    }


    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
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

            if (drawable != null)
                setDrawable(drawable);
        }
    }

    @Override
    public AlertDialog showDialog(Activity activity, int dialogTheme) {
        AlertDialog.Builder builder = super.buildDialog(activity, dialogTheme);
        builder.setTitle(null);
        builder.setMessage(null);
        builder.setView(R.layout.dialog_tip);

        AlertDialog alertDialog = builder.create();

        alertDialog.setCanceledOnTouchOutside(isCancelOnTouchOutside());
        alertDialog.show();

        ImageView imgTipImage = alertDialog.findViewById(R.id.imgTipImage);
        TextView txtTitle = alertDialog.findViewById(R.id.txtTitle);
        TextView txtMessage = alertDialog.findViewById(R.id.txtDescription);

        imgTipImage.setImageDrawable(getDrawable());
        txtTitle.setText(getTitle());
        txtMessage.setText(getMessage());

        return alertDialog;
    }
}
