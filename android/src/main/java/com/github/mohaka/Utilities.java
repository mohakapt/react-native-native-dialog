package com.github.mohaka;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by MoHaKa on 27.11.2015.
 */
public class Utilities {
    private Context context;

    public static Utilities with(Context context) {
        Utilities reVal = new Utilities();
        reVal.context = context;
        return reVal;
    }

    public static boolean flipContent() {
        return hasJellyBeanMR1() && isRTL();
    }

    public static boolean isRTL() {
        return isRTL(Locale.getDefault());
    }

    public static boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName(locale).charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }

    // Resources
    public String getRS(int res) {
        return context.getResources().getString(res);
    }

    public String getRS(int res, Object... formatArgs) {
        String string = getRS(res);
        if (!isEmpty(formatArgs))
            string = String.format(string, formatArgs);
        return string;
    }

    public String getRP(int res, int quantity) {
        return context.getResources().getQuantityString(res, quantity, quantity);
    }

    public int getRDi(int res) {
        return context.getResources().getDimensionPixelSize(res);
    }

    public boolean getRB(int res) {
        return context.getResources().getBoolean(res);
    }

    public int getRI(int res) {
        return context.getResources().getInteger(res);
    }

    public int getRC(int res) {
        return ContextCompat.getColor(context.getApplicationContext(), res);
    }

    public ColorStateList getRCs(int res) {
        return ContextCompat.getColorStateList(context.getApplicationContext(), res);
    }

    public Drawable getRD(int res) {
        return ContextCompat.getDrawable(context.getApplicationContext(), res);
    }

    public float dpiToPixel(float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public float pixelsToDpi(float px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public int calculateColumnNumber(int columnMinWidthDp) {
        Configuration configuration = context.getResources().getConfiguration();
        int widthDp = configuration.screenWidthDp;

        int reVal = widthDp / columnMinWidthDp;
        if (reVal == 0) reVal = 1;
        return reVal;
    }

    // Check system requirements
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean hasMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static String getUniquePsuedoID() {
        String m_szDevIDShort = "35" +
                (Build.BOARD.length() % 10) +
                (Build.BRAND.length() % 10) +
                (Build.DEVICE.length() % 10) +
                (Build.MANUFACTURER.length() % 10) +
                (Build.MODEL.length() % 10) +
                (Build.PRODUCT.length() % 10);

        String serial;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            serial = "serial";
        }

        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    public static boolean isProbablyArabic(String text) {
        for (int i = 0; i < text.length(); ) {
            int c = text.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }

    public static String formatPrice(BigDecimal price, boolean withCurrency) {
        int fraction = 2;
        String symbol = getCurrencySymbol("sar");

        String pattern = "#,##0";
        if (fraction > 0)
            pattern += "." + repeat("0", fraction);

        DecimalFormat formatter = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
        formatter.applyLocalizedPattern(pattern);
        String reVal = formatter.format(price);

        if (withCurrency)
            reVal = String.format("%s %s", reVal, symbol);

        return reVal;
    }

    private static String getCurrencySymbol(String currency) {
        String reVal;
        switch (currency.toLowerCase()) {
            case "usd":
                reVal = "$";
                break;
            case "eur":
                reVal = "€";
                break;
            case "try":
                reVal = "₺";
                break;
            case "gbp":
                reVal = "£";
                break;
            default:
                reVal = currency.toUpperCase(Locale.ENGLISH);
                break;
        }

        return reVal;
    }

    public static boolean isEmpty(final Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static boolean isEmpty(final Object[] a) {
        return a == null || a.length == 0;
    }

    public static String toString(final Object[] a) {
        if (isEmpty(a)) return null;

        StringBuilder reVal = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            reVal.append(a[i].toString());

            if (i != a.length - 1)
                reVal.append(",");
        }

        return reVal.toString();
    }

    public static boolean isEmpty(final Uri uri) {
        return uri == null || uri == Uri.EMPTY;
    }

    public static String repeat(String s, int times) {
        if (s == null) return null;
        if (times <= 0) return "";
        else return s + repeat(s, times - 1);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static boolean[] toBooleanArray(Object[] array, boolean defaultValue) {
        if (array == null) return null;
        boolean[] reVal = new boolean[array.length];
        Arrays.fill(reVal, defaultValue);

        for (int i = 0; i < array.length; i++) {
            boolean value = defaultValue;
            if (array[i] != null && array[i] instanceof Boolean)
                value = (boolean) array[i];
            reVal[i] = value;
        }

        return reVal;
    }
}
