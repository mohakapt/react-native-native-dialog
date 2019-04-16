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
    private final int lightDialogTheme;
    private final int darkDialogTheme;

    public RNNativeDialogPackage() {
        this.lightDialogTheme = 0;
        this.darkDialogTheme = 0;
    }

    public RNNativeDialogPackage(@StyleRes int dialogTheme) {
        this.lightDialogTheme = dialogTheme;
        this.darkDialogTheme = dialogTheme;
    }

    public RNNativeDialogPackage(@StyleRes int lightDialogTheme, @StyleRes int darkDialogTheme) {
        this.lightDialogTheme = lightDialogTheme;
        this.darkDialogTheme = darkDialogTheme;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(new RNNativeDialogModule(reactContext, lightDialogTheme, darkDialogTheme));
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
