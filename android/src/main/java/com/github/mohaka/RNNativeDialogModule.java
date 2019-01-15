package com.github.mohaka;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class RNNativeDialogModule extends ReactContextBaseJavaModule {
    public final static String TAG = "RNNativeDialog";

    public RNNativeDialogModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return TAG;
    }

    //region Event Emitter
    private final static String EVENT_POSITIVE_BUTTON = "native_dialog__positive_button";
    private final static String EVENT_NEGATIVE_BUTTON = "native_dialog__negative_button";
    private final static String EVENT_NATURAL_BUTTON = "native_dialog__natural_button";

    private void emitEvent(String event) {
        emitEvent(event, (WritableMap) null);
    }

    private void emitEvent(String event, WritableMap data) {
        ReactApplicationContext context = getReactApplicationContext();
        if (context == null) return;

        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(event, data);
    }

    private void emitEvent(String event, String message) {
        WritableMap params = Arguments.createMap();
        params.putString("value", message);
        emitEvent(event, params);
    }
    //endregion

    //region Input Dialog
    @ReactMethod
    public void showInputDialog(ReadableMap options) {
        Activity activity = getCurrentActivity();
        if (activity == null) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppTheme_AlertDialog);

        DialogInterface.OnClickListener onButtonClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog alertDialog = (AlertDialog) dialog;

                EditText txtInput = alertDialog.findViewById(R.id.txtInput);
                String newValue = txtInput.getText().toString();

                if (which == -1) emitEvent(EVENT_POSITIVE_BUTTON, newValue);
                else emitEvent(EVENT_NEGATIVE_BUTTON, newValue);
            }
        };

        if (options.hasKey("title"))
            builder.setTitle(options.getString("title"));
        if (options.hasKey("message"))
            builder.setMessage(options.getString("message"));
        if (options.hasKey("positiveButton"))
            builder.setPositiveButton(options.getString("positiveButton"), onButtonClick);
        if (options.hasKey("negativeButton"))
            builder.setNegativeButton(options.getString("negativeButton"), onButtonClick);

        builder.setView(R.layout.dialog_input);

        AlertDialog alertDialog = builder.create();
        if (options.hasKey("autoFocus") && options.getBoolean("autoFocus"))
            alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertDialog.show();

        EditText txtInput = alertDialog.findViewById(R.id.txtInput);

        String keyboardType = "default";
        boolean autoCorrect = true;
        String autoCapitalize = "sentences";
        boolean secureTextEntry = false;
        int inputType;

        if (options.hasKey("keyboardType"))
            keyboardType = options.getString("keyboardType");
        if (options.hasKey("autoCorrect"))
            autoCorrect = options.getBoolean("autoCorrect");
        if (options.hasKey("autoCapitalize"))
            autoCapitalize = options.getString("autoCapitalize");
        if (options.hasKey("secureTextEntry"))
            secureTextEntry = options.getBoolean("secureTextEntry");

        switch (keyboardType) {
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
        if (!autoCorrect)
            inputType = inputType | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
        switch (autoCapitalize) {
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
        if (secureTextEntry)
            inputType = inputType | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        txtInput.setInputType(inputType);

        if (options.hasKey("maxLength")) {
            int maxLength = options.getInt("maxLength");
            txtInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }

        if (options.hasKey("selectTextOnFocus"))
            txtInput.setSelectAllOnFocus(options.getBoolean("selectTextOnFocus"));

        if (options.hasKey("placeholder"))
            txtInput.setHint(options.getString("placeholder"));

        if (options.hasKey("value")) {
            if (options.getType("value") == ReadableType.Number)
                txtInput.setText(String.valueOf(options.getInt("value")));
            else
                txtInput.setText(options.getString("value"));
        }
    }
    //endregion
}