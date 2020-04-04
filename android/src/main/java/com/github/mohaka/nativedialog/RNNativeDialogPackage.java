package com.github.mohaka.nativedialog;

import androidx.annotation.StyleRes;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RNNativeDialogPackage implements ReactPackage {
    static int dialogTheme = 0;
    static int lightDialogTheme = 0;

    static int dateDialogTheme = 0;
    static int lightDateDialogTheme = 0;

    public static void setDialogTheme(@StyleRes int dialogTheme) {
        RNNativeDialogPackage.dialogTheme = dialogTheme;
        RNNativeDialogPackage.lightDialogTheme = dialogTheme;
    }

    public static void setDialogTheme(@StyleRes int dialogTheme, @StyleRes int lightDialogTheme) {
        RNNativeDialogPackage.dialogTheme = dialogTheme;
        RNNativeDialogPackage.lightDialogTheme = lightDialogTheme;
    }

    public static void setDatePickerDialogTheme(@StyleRes int dateDialogTheme) {
        RNNativeDialogPackage.dateDialogTheme = dateDialogTheme;
        RNNativeDialogPackage.lightDateDialogTheme = dateDialogTheme;
    }

    public static void setDatePickerDialogTheme(@StyleRes int dateDialogTheme, @StyleRes int lightDateDialogTheme) {
        RNNativeDialogPackage.dateDialogTheme = dateDialogTheme;
        RNNativeDialogPackage.lightDateDialogTheme = lightDateDialogTheme;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(new RNNativeDialogModule(reactContext));
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
