package com.github.mohaka.nativedialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.widget.EditText;
import android.widget.ListView;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.ArrayList;
import java.util.List;

import static com.github.mohaka.ItemsDialogOptions.MODE_DEFAULT;
import static com.github.mohaka.ItemsDialogOptions.MODE_MULTIPLE;
import static com.github.mohaka.ItemsDialogOptions.MODE_SINGLE;

public class RNNativeDialogModule extends ReactContextBaseJavaModule {
    public final static String TAG = "RNNativeDialog";
    private final int dialogTheme;

    public RNNativeDialogModule(ReactApplicationContext reactContext, @StyleRes int dialogTheme) {
        super(reactContext);
        this.dialogTheme = dialogTheme;
    }

    @Override
    public String getName() {
        return TAG;
    }

    //region Event Emitter
    private final static String EVENT_POSITIVE_BUTTON = "native_dialog__positive_button";
    private final static String EVENT_NEGATIVE_BUTTON = "native_dialog__negative_button";
    private final static String EVENT_NEUTRAL_BUTTON = "native_dialog__neutral_button";
    private final static String EVENT_DISMISS_DIALOG = "native_dialog__dismiss_dialog";

    private void emitEvent(String event) {
        emitData(event, null);
    }

    private void emitData(String event, WritableMap data) {
        ReactApplicationContext context = getReactApplicationContext();
        if (context == null) return;

        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(event, data);
    }

    private void emitInput(String event, String value) {
        WritableMap params = Arguments.createMap();
        params.putString("value", value);
        emitData(event, params);
    }

    private void emitSelection(ItemsDialogOptions.Item[] items) {
        ReactApplicationContext context = getReactApplicationContext();
        if (context == null) return;

        WritableArray params = Arguments.createArray();
        for (ItemsDialogOptions.Item item : items)
            if (item.isIdNumber())
                params.pushInt((Integer) item.getId());
            else
                params.pushString((String) item.getId());

        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(EVENT_POSITIVE_BUTTON, params);
    }
    //endregion

    private DialogInterface.OnDismissListener onDismiss = new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            emitEvent(EVENT_DISMISS_DIALOG);
        }
    };

    @ReactMethod
    public void showDialog(ReadableMap map) {
        Activity activity = getCurrentActivity();
        if (activity == null) return;

        DialogInterface.OnClickListener onClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        emitEvent(EVENT_POSITIVE_BUTTON);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        emitEvent(EVENT_NEGATIVE_BUTTON);
                        break;
                    case DialogInterface.BUTTON_NEUTRAL:
                        emitEvent(EVENT_NEUTRAL_BUTTON);
                        break;
                }
            }
        };

        DialogOptions dialog = new DialogOptions(map);
        dialog.setClickListener(onClick);
        dialog.setDismissListener(onDismiss);
        dialog.showDialog(activity, dialogTheme);
    }

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
                        emitInput(EVENT_POSITIVE_BUTTON, newValue);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        emitInput(EVENT_NEGATIVE_BUTTON, newValue);
                        break;
                    case DialogInterface.BUTTON_NEUTRAL:
                        emitInput(EVENT_NEUTRAL_BUTTON, newValue);
                        break;
                }
            }
        };

        InputDialogOptions dialog = new InputDialogOptions(map);
        dialog.setClickListener(onButtonClick);
        dialog.setDismissListener(onDismiss);
        dialog.showDialog(activity, dialogTheme);
    }

    @ReactMethod
    public void showItemsDialog(ReadableMap map) {
        Activity activity = getCurrentActivity();
        if (activity == null) return;

        final ItemsDialogOptions itemsDialog = new ItemsDialogOptions(map);

        DialogInterface.OnClickListener onButtonClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        emitEvent(EVENT_NEGATIVE_BUTTON);
                        return;
                    case DialogInterface.BUTTON_NEUTRAL:
                        emitEvent(EVENT_NEUTRAL_BUTTON);
                        return;
                }

                int mode = itemsDialog.getMode();
                List<ItemsDialogOptions.Item> itemList = itemsDialog.getItems();
                switch (mode) {
                    case MODE_DEFAULT:
                    default:
                        emitSelection(new ItemsDialogOptions.Item[]{itemList.get(which)});
                        break;
                    case MODE_SINGLE: {
                        ListView listView = ((AlertDialog) dialog).getListView();
                        int checkedItemPosition = listView.getCheckedItemPosition();
                        emitSelection(new ItemsDialogOptions.Item[]{itemList.get(checkedItemPosition)});
                        break;
                    }
                    case MODE_MULTIPLE: {
                        ListView listView = ((AlertDialog) dialog).getListView();
                        SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();

                        List<ItemsDialogOptions.Item> selectedItemsList = new ArrayList<>();
                        for (int i = 0; i < itemList.size(); i++)
                            if (checkedItemPositions.get(i))
                                selectedItemsList.add(itemList.get(i));
                        ItemsDialogOptions.Item[] selectedItemsArray = new ItemsDialogOptions.Item[selectedItemsList.size()];
                        selectedItemsArray = selectedItemsList.toArray(selectedItemsArray);

                        emitSelection(selectedItemsArray);
                        break;
                    }
                }
            }
        };

        itemsDialog.setClickListener(onButtonClick);
        itemsDialog.setDismissListener(onDismiss);
        itemsDialog.showDialog(activity, dialogTheme);
    }

    @ReactMethod
    public void showProgressDialog(ReadableMap map) {
        Activity activity = getCurrentActivity();
        if (activity == null) return;

        DialogInterface.OnClickListener onClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        emitEvent(EVENT_POSITIVE_BUTTON);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        emitEvent(EVENT_NEGATIVE_BUTTON);
                        break;
                    case DialogInterface.BUTTON_NEUTRAL:
                        emitEvent(EVENT_NEUTRAL_BUTTON);
                        break;
                }
            }
        };

        ProgressDialogOptions dialog = new ProgressDialogOptions(map);
        dialog.setClickListener(onClick);
        dialog.setDismissListener(onDismiss);
        dialog.showDialog(activity, dialogTheme);
    }
}
