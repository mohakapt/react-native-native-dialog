package com.github.mohaka.nativedialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
//import com.github.mohaka.ratingstar.RatingStar;

public class RatingDialogOptions extends DialogOptions {
	public final static int MODE_ROSE = 100;
	public final static int MODE_BAR = 101;

	private int mode;
	private int value;

	public RatingDialogOptions() {
	}

	public RatingDialogOptions(ReadableMap map) {
		this.populate(map);
	}

	public int getMode() {
		return mode;
	}

	public int getValue() {
		return value;
	}

	public void setMode(int value) {
		this.mode = value;
	}

	public void setMode(String mode) {
		switch (mode.toLowerCase()) {
			case "default":
			default:
				setMode(MODE_ROSE);
				break;
			case "bar":
				setMode(MODE_BAR);
				break;
		}
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public void populate(ReadableMap map) {
		super.populate(map);

		if (map.hasKey("mode")) setMode(map.getString("mode"));

		if (map.hasKey("value")) {
			if (map.getType("value") == ReadableType.Number)
				setValue(map.getInt("value"));
			else
				setValue(Integer.parseInt(map.getString("value")));
		}
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		AlertDialog.Builder builder = super.buildDialog();
		builder.setView(R.layout.dialog_rating);

		AlertDialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(isCancelOnTouchOutside());
		alertDialog.show();

//        RatingStar ratingStar = alertDialog.findViewById(R.id.ratingStar);
		RatingBar ratingBar = alertDialog.findViewById(R.id.ratingBar);

//        ratingStar.setRating(getValue());
		ratingBar.setRating(getValue());

		if (getMode() == MODE_BAR) {
//            ratingStar.setVisibility(View.GONE);
			ratingBar.setVisibility(View.VISIBLE);
		} else {
//            ratingStar.setVisibility(View.VISIBLE);
			ratingBar.setVisibility(View.GONE);
		}

		return alertDialog;
	}
}
