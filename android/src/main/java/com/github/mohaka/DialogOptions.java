package com.github.mohaka;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.facebook.react.bridge.ReadableMap;

public class DialogOptions {
    public final static int THEME_LIGHT = 100;
    public final static int THEME_DARK = 101;

    private int theme;
    private int accentColor;

    private boolean cancellable;
    private boolean cancelOnTouchOutside;

    private String title;
    private String message;

    private String positiveButton;
    private String negativeButton;
    private String neutralButton;

    private DialogInterface.OnClickListener clickListener;
    private DialogInterface.OnDismissListener dismissListener;

    public DialogOptions() {
    }

    public DialogOptions(ReadableMap map) {
        this.populate(map);
    }

    public int getTheme() {
        return theme;
    }

    public int getAccentColor() {
        return accentColor;
    }

    public boolean isCancellable() {
        return cancellable;
    }

    public boolean isCancelOnTouchOutside() {
        return cancelOnTouchOutside;
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

    public DialogInterface.OnClickListener getClickListener() {
        return clickListener;
    }

    public DialogInterface.OnDismissListener getDismissListener() {
        return dismissListener;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public void setTheme(String theme) {
        this.setTheme("dark".equalsIgnoreCase(theme) ? THEME_DARK : THEME_LIGHT);
    }

    public void setAccentColor(int accentColor) {
        this.accentColor = accentColor;
    }

    public void setAccentColor(String accentColor) {
        this.setAccentColor(Color.parseColor(accentColor));
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    public void setCancelOnTouchOutside(boolean cancelOnTouchOutside) {
        this.cancelOnTouchOutside = cancelOnTouchOutside;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPositiveButton(String positiveButton) {
        this.positiveButton = positiveButton;
    }

    public void setNegativeButton(String negativeButton) {
        this.negativeButton = negativeButton;
    }

    public void setNeutralButton(String neutralButton) {
        this.neutralButton = neutralButton;
    }

    public void setClickListener(DialogInterface.OnClickListener listener) {
        this.clickListener = listener;
    }

    public void setDismissListener(DialogInterface.OnDismissListener listener) {
        this.dismissListener = listener;
    }

    public void populate(ReadableMap map) {
        if (map.hasKey("theme")) setTheme(map.getString("theme"));
        if (map.hasKey("accentColor")) setAccentColor(map.getString("accentColor"));
        if (map.hasKey("title")) setTitle(map.getString("title"));
        if (map.hasKey("message")) setMessage(map.getString("message"));
        if (map.hasKey("positiveButton")) setPositiveButton(map.getString("positiveButton"));
        if (map.hasKey("negativeButton")) setNegativeButton(map.getString("negativeButton"));
        if (map.hasKey("neutralButton")) setNeutralButton(map.getString("neutralButton"));
        if (map.hasKey("cancellable")) setCancellable(map.getBoolean("cancellable"));
        if (map.hasKey("cancelOnTouchOutside"))
            setCancelOnTouchOutside(map.getBoolean("cancelOnTouchOutside"));
    }

    protected AlertDialog.Builder buildDialog(Activity activity, @StyleRes int dialogTheme) {
        if (dialogTheme == 0)
            dialogTheme = getTheme() == THEME_DARK
                    ? R.style.Theme_AppCompat_Dialog_Alert
                    : R.style.Theme_AppCompat_Light_Dialog_Alert;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, dialogTheme);

        builder.setTitle(getTitle());
        builder.setMessage(getMessage());
        builder.setCancelable(isCancellable());
        builder.setOnDismissListener(getDismissListener());

        if (!TextUtils.isEmpty(getPositiveButton()))
            builder.setPositiveButton(getPositiveButton(), getClickListener());

        if (!TextUtils.isEmpty(getNegativeButton()))
            builder.setNegativeButton(getNegativeButton(), getClickListener());

        if (!TextUtils.isEmpty(getNeutralButton()))
            builder.setNeutralButton(getNeutralButton(), getClickListener());

        return builder;
    }

    public AlertDialog showDialog(Activity activity, @StyleRes int dialogTheme) {
        AlertDialog alertDialog = buildDialog(activity, dialogTheme).create();
        alertDialog.setCanceledOnTouchOutside(isCancelOnTouchOutside());
        alertDialog.show();
        return alertDialog;
    }
}
