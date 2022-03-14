package com.github.mohaka.nativedialog;

import android.content.Context;
import android.net.Uri;
import android.os.Build;

import java.util.Arrays;
import java.util.Collection;

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
