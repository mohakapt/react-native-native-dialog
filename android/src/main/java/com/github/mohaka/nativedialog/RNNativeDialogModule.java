package com.github.mohaka.nativedialog;

import android.content.DialogInterface;
import android.util.SparseBooleanArray;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RatingBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.github.mohaka.ratingstar.RatingStar;

import java.util.ArrayList;
import java.util.List;

import static com.github.mohaka.nativedialog.ItemsDialogOptions.MODE_DEFAULT;
import static com.github.mohaka.nativedialog.ItemsDialogOptions.MODE_MULTIPLE;
import static com.github.mohaka.nativedialog.ItemsDialogOptions.MODE_SINGLE;

public class RNNativeDialogModule extends ReactContextBaseJavaModule {
    public final static String TAG = "RNNativeDialog";

    public RNNativeDialogModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return TAG;
    }

    private WritableMap getWritableResult(int which) {
        WritableMap reVal = Arguments.createMap();
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                reVal.putString("action", "positive");
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                reVal.putString("action", "negative");
                break;
            case DialogInterface.BUTTON_NEUTRAL:
                reVal.putString("action", "neutral");
                break;

            default:
                reVal.putString("action", "dismiss");
                break;
        }
        return reVal;
    }

    @ReactMethod
    public void showDialog(ReadableMap map, Promise promise) {
        AppCompatActivity activity = (AppCompatActivity) getCurrentActivity();
        if (activity == null) return;

        DialogInterface.OnClickListener onClick = (dialog, which) -> {
            WritableMap params = getWritableResult(which);
            promise.resolve(params);
        };
        DialogInterface.OnDismissListener onDismiss = dialog -> {
            WritableMap params = getWritableResult(101);
            promise.resolve(params);
        };

        DialogOptions dialog = new DialogOptions(map);
        dialog.setClickListener(onClick);
        dialog.setDismissListener(onDismiss);
        dialog.show(activity.getSupportFragmentManager(), "dialog");
    }

    @ReactMethod
    public void showInputDialog(ReadableMap map, Promise promise) {
        AppCompatActivity activity = (AppCompatActivity) getCurrentActivity();
        if (activity == null) return;

        DialogInterface.OnClickListener onButtonClick = (dialog, which) -> {
            AlertDialog alertDialog = (AlertDialog) dialog;

            EditText txtInput = alertDialog.findViewById(R.id.txtInput);
            String newValue = txtInput.getText().toString();

            WritableMap params = getWritableResult(which);
            params.putString("value", newValue);
            promise.resolve(params);
        };
        DialogInterface.OnDismissListener onDismiss = dialog -> {
            WritableMap params = getWritableResult(101);
            promise.resolve(params);
        };

        InputDialogOptions dialog = new InputDialogOptions(map);
        dialog.setClickListener(onButtonClick);
        dialog.setDismissListener(onDismiss);
        dialog.show(activity.getSupportFragmentManager(), "dialog_input");
    }

    @ReactMethod
    public void showItemsDialog(ReadableMap map, Promise promise) {
        AppCompatActivity activity = (AppCompatActivity) getCurrentActivity();
        if (activity == null) return;

        final ItemsDialogOptions itemsDialog = new ItemsDialogOptions(map);

        DialogInterface.OnClickListener onButtonClick = (dialog, which) -> {
            WritableMap params = getWritableResult(which);
            switch (which) {
                case DialogInterface.BUTTON_NEGATIVE:
                case DialogInterface.BUTTON_NEUTRAL:
                    promise.resolve(params);
                    return;
            }

            int mode = itemsDialog.getMode();
            List<ItemsDialogOptions.Item> itemList = itemsDialog.getItems();
            ItemsDialogOptions.Item[] items;
            switch (mode) {
                case MODE_DEFAULT:
                default:
                    items = new ItemsDialogOptions.Item[]{itemList.get(which)};
                    break;
                case MODE_SINGLE: {
                    ListView listView = ((AlertDialog) dialog).getListView();
                    int checkedItemPosition = listView.getCheckedItemPosition();
                    items = new ItemsDialogOptions.Item[]{itemList.get(checkedItemPosition)};
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

                    items = selectedItemsArray;
                    break;
                }
            }

            WritableArray values = Arguments.createArray();
            for (ItemsDialogOptions.Item item : items)
                if (item.isIdNumber())
                    values.pushInt((Integer) item.getId());
                else
                    values.pushString((String) item.getId());
            params.putArray("value", values);

            promise.resolve(params);
        };
        DialogInterface.OnDismissListener onDismiss = dialog -> {
            WritableMap params = getWritableResult(101);
            promise.resolve(params);
        };

        itemsDialog.setClickListener(onButtonClick);
        itemsDialog.setDismissListener(onDismiss);
        itemsDialog.show(activity.getSupportFragmentManager(), "dialog_items");
    }

    @ReactMethod
    public void showNumberPickerDialog(ReadableMap map, Promise promise) {
        AppCompatActivity activity = (AppCompatActivity) getCurrentActivity();
        if (activity == null) return;

        DialogInterface.OnClickListener onButtonClick = (dialog, which) -> {
            AlertDialog alertDialog = (AlertDialog) dialog;

            NumberPicker picNumber = alertDialog.findViewById(R.id.picNumber);
            int newValue = picNumber.getValue();

            WritableMap params = getWritableResult(which);
            params.putInt("value", newValue);
            promise.resolve(params);
        };
        DialogInterface.OnDismissListener onDismiss = dialog -> {
            WritableMap params = getWritableResult(101);
            promise.resolve(params);
        };

        NumberPickerDialogOptions dialog = new NumberPickerDialogOptions(map);
        dialog.setClickListener(onButtonClick);
        dialog.setDismissListener(onDismiss);
        dialog.show(activity.getSupportFragmentManager(), "dialog_number_picker");
    }

    @ReactMethod
    public void showRatingDialog(ReadableMap map, Promise promise) {
        AppCompatActivity activity = (AppCompatActivity) getCurrentActivity();
        if (activity == null) return;

        RatingDialogOptions dialogOptions = new RatingDialogOptions(map);

        DialogInterface.OnClickListener onButtonClick = (dialog, which) -> {
            AlertDialog alertDialog = (AlertDialog) dialog;

            int newValue;
            if (dialogOptions.getMode() == RatingDialogOptions.MODE_BAR) {
                RatingBar ratingBar = alertDialog.findViewById(R.id.ratingBar);
                newValue = (int) ratingBar.getRating();
            } else {
                RatingStar ratingStar = alertDialog.findViewById(R.id.ratingStar);
                newValue = ratingStar.getRating();
            }

            WritableMap params = getWritableResult(which);
            params.putInt("value", newValue);
            promise.resolve(params);
        };
        DialogInterface.OnDismissListener onDismiss = dialog -> {
            WritableMap params = getWritableResult(101);
            promise.resolve(params);
        };

        dialogOptions.setClickListener(onButtonClick);
        dialogOptions.setDismissListener(onDismiss);
        dialogOptions.show(activity.getSupportFragmentManager(), "dialog_rating");
    }
}
