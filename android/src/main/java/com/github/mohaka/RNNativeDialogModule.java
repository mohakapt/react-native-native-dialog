package com.github.mohaka;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
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
    private final static String EVENT_NEUTRAL_BUTTON = "native_dialog__neutral_button";

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
    public void showInputDialog(ReadableMap map) {
        Activity activity = getCurrentActivity();
        if (activity == null) return;

        DialogInterface.OnClickListener onButtonClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog alertDialog = (AlertDialog) dialog;

                EditText txtInput = alertDialog.findViewById(R.id.txtInput);
                String newValue = txtInput.getText().toString();

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        emitEvent(EVENT_POSITIVE_BUTTON, newValue);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        emitEvent(EVENT_NEGATIVE_BUTTON, newValue);
                        break;
                    case DialogInterface.BUTTON_NEUTRAL:
                        emitEvent(EVENT_NEUTRAL_BUTTON, newValue);
                        break;
                }
            }
        };

        InputDialogOptions.Builder builder = new InputDialogOptions.Builder();
        builder.populate(map);
        builder.setOnButtonClickListener(onButtonClick);
        builder.build().showDialog(activity);
    }
    //endregion
}