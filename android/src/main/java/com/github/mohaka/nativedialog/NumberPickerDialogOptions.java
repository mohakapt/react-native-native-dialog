package com.github.mohaka.nativedialog;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.facebook.react.bridge.ReadableMap;

public class NumberPickerDialogOptions extends DialogOptions {
	private int value;
	private int minValue;
	private int maxValue;

	public NumberPickerDialogOptions() {
	}

	public NumberPickerDialogOptions(ReadableMap map) {
		this.populate(map);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public void populate(ReadableMap map) {
		super.populate(map);
		if (map.hasKey("value")) setValue(map.getInt("value"));
		if (map.hasKey("minValue")) setMinValue(map.getInt("minValue"));
		if (map.hasKey("maxValue")) setMaxValue(map.getInt("maxValue"));
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		AlertDialog.Builder builder = buildDialog();

		builder.setView(R.layout.dialog_number_picker);

		AlertDialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(isCancelOnTouchOutside());
		alertDialog.show();

		NumberPicker picNumber = alertDialog.findViewById(R.id.picNumber);
		picNumber.setMinValue(getMinValue());
		picNumber.setMaxValue(getMaxValue());
		picNumber.setValue(getValue());

		return alertDialog;
	}
}
