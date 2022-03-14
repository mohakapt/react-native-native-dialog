package com.github.mohaka.nativedialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

public class InputDialogOptions extends DialogOptions {
	private String value;
	private String placeholder;
	private String keyboardType;
	private Integer maxLength;
	private Boolean autoFocus;
	private Boolean autoCorrect;
	private String autoCapitalize;
	private Boolean secureTextEntry;
	private Boolean selectTextOnFocus;

	public InputDialogOptions() {
	}

	public InputDialogOptions(ReadableMap map) {
		this.populate(map);
	}

	public String getValue() {
		return value;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public String getKeyboardType() {
		return keyboardType;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public Boolean getAutoFocus() {
		return autoFocus;
	}

	public Boolean getAutoCorrect() {
		return autoCorrect;
	}

	public String getAutoCapitalize() {
		return autoCapitalize;
	}

	public Boolean getSecureTextEntry() {
		return secureTextEntry;
	}

	public Boolean getSelectTextOnFocus() {
		return selectTextOnFocus;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public void setKeyboardType(String keyboardType) {
		this.keyboardType = keyboardType;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public void setAutoFocus(Boolean autoFocus) {
		this.autoFocus = autoFocus;
	}

	public void setAutoCorrect(Boolean autoCorrect) {
		this.autoCorrect = autoCorrect;
	}

	public void setAutoCapitalize(String autoCapitalize) {
		this.autoCapitalize = autoCapitalize;
	}

	public void setSecureTextEntry(Boolean secureTextEntry) {
		this.secureTextEntry = secureTextEntry;
	}

	public void setSelectTextOnFocus(Boolean selectTextOnFocus) {
		this.selectTextOnFocus = selectTextOnFocus;
	}

	@Override
	public void populate(ReadableMap map) {
		super.populate(map);

		if (map.hasKey("value")) {
			if (map.getType("value") == ReadableType.Number)
				setValue(String.valueOf(map.getInt("value")));
			else
				setValue(map.getString("value"));
		}
		if (map.hasKey("placeholder")) setPlaceholder(map.getString("placeholder"));
		if (map.hasKey("keyboardType")) setKeyboardType(map.getString("keyboardType"));
		if (map.hasKey("maxLength")) setMaxLength(map.getInt("maxLength"));
		if (map.hasKey("autoFocus")) setAutoFocus(map.getBoolean("autoFocus"));
		if (map.hasKey("autoCorrect")) setAutoCorrect(map.getBoolean("autoCorrect"));
		if (map.hasKey("autoCapitalize")) setAutoCapitalize(map.getString("autoCapitalize"));
		if (map.hasKey("secureTextEntry"))
			setSecureTextEntry(map.getBoolean("secureTextEntry"));
		if (map.hasKey("selectTextOnFocus"))
			setSelectTextOnFocus(map.getBoolean("selectTextOnFocus"));
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		AlertDialog.Builder builder = super.buildDialog();
		builder.setView(R.layout.dialog_input);

		AlertDialog alertDialog = builder.create();

		if (getAutoFocus())
			alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		alertDialog.setCanceledOnTouchOutside(isCancelOnTouchOutside());
		alertDialog.show();

		EditText txtInput = alertDialog.findViewById(R.id.txtInput);

		int inputType;
		switch (getKeyboardType()) {
			case "number-pad":
				inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED;
				break;
			case "decimal-pad":
				inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL;
				break;
			case "numeric":
				inputType = InputType.TYPE_CLASS_NUMBER;
				break;
			case "email-address":
				inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
				break;
			case "phone-pad":
				inputType = InputType.TYPE_CLASS_PHONE;
				break;
			default:
				inputType = InputType.TYPE_CLASS_TEXT;
				break;
		}
		if (!getAutoCorrect())
			inputType = inputType | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
		switch (getAutoCapitalize()) {
			case "characters":
				inputType = inputType | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS;
				break;
			case "words":
				inputType = inputType | InputType.TYPE_TEXT_FLAG_CAP_WORDS;
				break;
			case "sentences":
				inputType = inputType | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES;
				break;
		}
		if (getSecureTextEntry())
			inputType = inputType | InputType.TYPE_TEXT_VARIATION_PASSWORD;
		txtInput.setInputType(inputType);

		if (getMaxLength() != null)
			txtInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getMaxLength())});

		txtInput.setSelectAllOnFocus(getSelectTextOnFocus());
		txtInput.setHint(getPlaceholder());
		txtInput.setText(getValue());


		if (getAutoFocus())
			txtInput.requestFocus();

		return alertDialog;
	}
}
