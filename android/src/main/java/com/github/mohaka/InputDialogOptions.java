package com.github.mohaka;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

public class InputDialogOptions extends DialogOptions {
    private String value;
    private String placeholder;
    private String keyboardType = "default";
    private Integer maxLength;
    private Boolean autoFocus = false;
    private Boolean autoCorrect = true;
    private String autoCapitalize = "sentences";
    private Boolean secureTextEntry = false;
    private Boolean selectTextOnFocus = false;

    protected InputDialogOptions() {
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

    public AlertDialog showDialog(Activity activity) {
        AlertDialog.Builder builder = super.buildDialog(activity);
        builder.setView(R.layout.dialog_input);

        AlertDialog alertDialog = builder.create();

        if (getAutoFocus())
            alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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

        return alertDialog;
    }

    public static class Builder extends DialogOptions.Builder {

        public Builder() {
            this.mDialogOptions = new InputDialogOptions();
        }

        public Builder setValue(String value) {
            ((InputDialogOptions) this.mDialogOptions).value = value;
            return this;
        }

        public Builder setPlaceholder(String placeholder) {
            ((InputDialogOptions) this.mDialogOptions).placeholder = placeholder;
            return this;
        }

        public Builder setKeyboardType(String keyboardType) {
            ((InputDialogOptions) this.mDialogOptions).keyboardType = keyboardType;
            return this;
        }

        public Builder setMaxLength(Integer maxLength) {
            ((InputDialogOptions) this.mDialogOptions).maxLength = maxLength;
            return this;
        }

        public Builder setAutoFocus(Boolean autoFocus) {
            ((InputDialogOptions) this.mDialogOptions).autoFocus = autoFocus;
            return this;
        }

        public Builder setAutoCorrect(Boolean autoCorrect) {
            ((InputDialogOptions) this.mDialogOptions).autoCorrect = autoCorrect;
            return this;
        }

        public Builder setAutoCapitalize(String autoCapitalize) {
            ((InputDialogOptions) this.mDialogOptions).autoCapitalize = autoCapitalize;
            return this;
        }

        public Builder setSecureTextEntry(Boolean secureTextEntry) {
            ((InputDialogOptions) this.mDialogOptions).secureTextEntry = secureTextEntry;
            return this;
        }

        public Builder setSelectTextOnFocus(Boolean selectTextOnFocus) {
            ((InputDialogOptions) this.mDialogOptions).selectTextOnFocus = selectTextOnFocus;
            return this;
        }

        @Override
        public InputDialogOptions.Builder populate(ReadableMap map) {
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

            return this;
        }

        @Override
        public InputDialogOptions build() {
            return (InputDialogOptions) this.mDialogOptions;
        }
    }
}
