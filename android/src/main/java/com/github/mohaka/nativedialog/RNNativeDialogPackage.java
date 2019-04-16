package com.github.mohaka.nativedialog;

import android.support.annotation.StyleRes;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RNNativeDialogPackage implements ReactPackage {
    private final int dialogTheme;
    private final int lightDialogTheme;

    public RNNativeDialogPackage() {
        this.dialogTheme = 0;
        this.lightDialogTheme = 0;
    }

    public RNNativeDialogPackage(@StyleRes int dialogTheme) {
        this.dialogTheme = dialogTheme;
        this.lightDialogTheme = dialogTheme;
    }

    public RNNativeDialogPackage(@StyleRes int dialogTheme, @StyleRes int lightDialogTheme) {
        this.dialogTheme = dialogTheme;
        this.lightDialogTheme = lightDialogTheme;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(new RNNativeDialogModule(reactContext, dialogTheme, lightDialogTheme));
    }

    // Deprecated from RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
