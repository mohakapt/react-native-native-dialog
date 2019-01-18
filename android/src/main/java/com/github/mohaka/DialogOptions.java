package com.github.mohaka;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.facebook.react.bridge.ReadableMap;

public class DialogOptions {
    public final static int THEME_LIGHT = R.style.AlertDialog_Light;
    public final static int THEME_DARK = R.style.AlertDialog_Dark;

    private int theme = THEME_LIGHT;
    private int accentColor;

    private String title;
    private String message;

    private String positiveButton;
    private String negativeButton;
    private String neutralButton;

    private DialogInterface.OnClickListener onButtonClick;

    protected DialogOptions() {
    }

    public int getTheme() {
        return theme;
    }

    public int getAccentColor() {
        return accentColor;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getPositiveButton() {
        return positiveButton;
    }

    public String getNegativeButton() {
        return negativeButton;
    }

    public String getNeutralButton() {
        return neutralButton;
    }

    public DialogInterface.OnClickListener getOnButtonClickListener() {
        return onButtonClick;
    }

    public AlertDialog.Builder buildDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(getTitle());
        builder.setMessage(getMessage());

        if (!TextUtils.isEmpty(getPositiveButton()))
            builder.setPositiveButton(getPositiveButton(), getOnButtonClickListener());

        if (!TextUtils.isEmpty(getNegativeButton()))
            builder.setNegativeButton(getNegativeButton(), getOnButtonClickListener());

        if (!TextUtils.isEmpty(getNeutralButton()))
            builder.setNeutralButton(getNeutralButton(), getOnButtonClickListener());

        return builder;
    }

    public AlertDialog showDialog(Activity activity) {
        return buildDialog(activity).show();
    }

    public static class Builder {
        protected DialogOptions mDialogOptions;

        public Builder() {
            mDialogOptions = new DialogOptions();
        }

        public Builder setTheme(int theme) {
            this.mDialogOptions.theme = theme;
            return this;
        }

        public Builder setTheme(String theme) {
            this.setTheme("dark".equalsIgnoreCase(theme) ? THEME_DARK : THEME_LIGHT);
            return this;
        }

        public Builder setAccentColor(int accentColor) {
            this.mDialogOptions.accentColor = accentColor;
            return this;
        }

        public Builder setAccentColor(String accentColor) {
            this.setAccentColor(Color.parseColor(accentColor));
            return this;
        }

        public Builder setTitle(String title) {
            this.mDialogOptions.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.mDialogOptions.message = message;
            return this;
        }

        public Builder setPositiveButton(String positiveButton) {
            this.mDialogOptions.positiveButton = positiveButton;
            return this;
        }

        public Builder setNegativeButton(String negativeButton) {
            this.mDialogOptions.negativeButton = negativeButton;
            return this;
        }

        public Builder setNeutralButton(String neutralButton) {
            this.mDialogOptions.neutralButton = neutralButton;
            return this;
        }

        public Builder setOnButtonClickListener(DialogInterface.OnClickListener listener) {
            this.mDialogOptions.onButtonClick = listener;
            return this;
        }

        public Builder populate(ReadableMap map) {
            if (map.hasKey("theme")) setTheme(map.getString("theme"));
            if (map.hasKey("accentColor")) setAccentColor(map.getString("accentColor"));
            if (map.hasKey("title")) setTitle(map.getString("title"));
            if (map.hasKey("message")) setMessage(map.getString("message"));
            if (map.hasKey("positiveButton")) setPositiveButton(map.getString("positiveButton"));
            if (map.hasKey("negativeButton")) setNegativeButton(map.getString("negativeButton"));
            if (map.hasKey("neutralButton")) setNeutralButton(map.getString("neutralButton"));

            return this;
        }

        public DialogOptions build() {
            return mDialogOptions;
        }
    }
}
