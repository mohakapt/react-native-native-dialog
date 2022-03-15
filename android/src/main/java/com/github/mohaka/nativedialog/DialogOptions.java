package com.github.mohaka.nativedialog;

import static com.github.mohaka.nativedialog.NativeDialogPackage.dialogTheme;
import static com.github.mohaka.nativedialog.NativeDialogPackage.lightDialogTheme;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.facebook.react.bridge.ColorPropConverter;
import com.facebook.react.bridge.ReadableMap;

public class DialogOptions extends DialogFragment {
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

	protected Button btnPositive;
	protected Button btnNegative;
	protected Button btnNeutral;

	public DialogOptions() {
	}

	public DialogOptions(ReadableMap map, Context context) {
		this.populate(map, context);
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

	public void populate(ReadableMap map, Context context) {
		if (map.hasKey("theme")) setTheme(map.getString("theme"));
		if (map.hasKey("title")) setTitle(map.getString("title"));
		if (map.hasKey("message")) setMessage(map.getString("message"));
		if (map.hasKey("positiveButton")) setPositiveButton(map.getString("positiveButton"));
		if (map.hasKey("negativeButton")) setNegativeButton(map.getString("negativeButton"));
		if (map.hasKey("neutralButton")) setNeutralButton(map.getString("neutralButton"));
		if (map.hasKey("cancellable")) setCancellable(map.getBoolean("cancellable"));
		if (map.hasKey("cancelOnTouchOutside")) setCancelOnTouchOutside(map.getBoolean("cancelOnTouchOutside"));

		if (map.hasKey("accentColor")) {
			switch (map.getType("accentColor")) {
				case String:
					setAccentColor(map.getString("accentColor"));
					break;
				case Map:
					setAccentColor(ColorPropConverter.getColor(map.getDynamic("accentColor").asMap(), context));
					break;
			}
		}
	}

	protected AlertDialog.Builder buildDialog() {
		int dialogThemeResId;
		if (getTheme() == THEME_DARK)
			dialogThemeResId = dialogTheme == 0 ? R.style.Theme_AppCompat_Dialog_Alert : dialogTheme;
		else if (getTheme() == THEME_LIGHT)
			dialogThemeResId = lightDialogTheme == 0 ? R.style.Theme_AppCompat_Light_Dialog_Alert : lightDialogTheme;
		else
			dialogThemeResId = dialogTheme == 0 ? R.style.Theme_AppCompat_DayNight_Dialog_Alert : lightDialogTheme;

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), dialogThemeResId);

		builder.setTitle(getTitle());
		builder.setMessage(getMessage());
		builder.setCancelable(isCancellable());

		if (!TextUtils.isEmpty(getPositiveButton()))
			builder.setPositiveButton(getPositiveButton(), getClickListener());

		if (!TextUtils.isEmpty(getNegativeButton()))
			builder.setNegativeButton(getNegativeButton(), getClickListener());

		if (!TextUtils.isEmpty(getNeutralButton()))
			builder.setNeutralButton(getNeutralButton(), getClickListener());

		return builder;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		return buildDialog().create();
	}

	@Override
	public void onDismiss(@NonNull DialogInterface dialog) {
		super.onDismiss(dialog);

		if (getDismissListener() != null)
			getDismissListener().onDismiss(dialog);
	}

	@Override
	public void onResume() {
		super.onResume();

		btnPositive = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);
		btnNegative = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE);
		btnNeutral = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEUTRAL);
	}

	@Override
	public void onPause() {
		super.onPause();

		btnPositive = null;
		btnNegative = null;
		btnNeutral = null;
	}
}
