package com.github.mohaka.nativedialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.mohaka.nativedialog.RNNativeDialogPackage.dateDialogTheme;
import static com.github.mohaka.nativedialog.RNNativeDialogPackage.lightDateDialogTheme;

public class DatePickerDialogOptions extends DialogOptions {
    public final static int MODE_DATE = 0;
    public final static int MODE_TIME = 1;
    public final static int MODE_DATE_TIME = 2;

    private Date date;
    private int mode;
    private boolean hour24;
    private Date minDate;
    private Date maxDate;

    public DatePickerDialogOptions() {
    }

    public DatePickerDialogOptions(ReadableMap map) {
        this.populate(map);
    }

    public Date getDate() {
        return date;
    }

    public int getMode() {
        return mode;
    }

    public boolean is24Hour() {
        return hour24;
    }

    public Date getMinDate() {
        return minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }


    public void setDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            this.date = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setMode(String mode) {
        if (mode == null) {
            this.setMode(MODE_DATE);
            return;
        }
        switch (mode.toLowerCase()) {
            case "time":
                this.setMode(MODE_TIME);
                break;
            case "datetime":
                this.setMode(MODE_DATE_TIME);
                break;
            default:
                this.setMode(MODE_DATE);
                break;
        }
    }

    public void set24Hour(boolean hour24) {
        this.hour24 = hour24;
    }

    public void setMinDate(String minDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            this.minDate = format.parse(minDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setMaxDate(String maxDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            this.maxDate = format.parse(maxDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void populate(ReadableMap map) {
        super.populate(map);

        if (map.hasKey("date")) setDate(map.getString("date"));
        if (map.hasKey("mode")) setMode(map.getString("mode"));
        if (map.hasKey("is24Hour")) set24Hour(map.getBoolean("is24Hour"));
        if (map.hasKey("minDate")) setMinDate(map.getString("minDate"));
        if (map.hasKey("maxDate")) setMaxDate(map.getString("maxDate"));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int dialogThemeResId;
        if (getTheme() == THEME_DARK)
            dialogThemeResId = dateDialogTheme == 0 ? android.R.style.Theme_DeviceDefault_Dialog : dateDialogTheme;
        else
            dialogThemeResId = lightDateDialogTheme == 0 ? android.R.style.Theme_DeviceDefault_Light_Dialog : lightDateDialogTheme;

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), dialogThemeResId, (view, year, month, dayOfMonth) -> {

        }, 1996, 1, 1);

        return new TimePickerDialog(getActivity(), (view, hourOfDay, minute) -> {

        }, 14, 25, true);
    }
}
