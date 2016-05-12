package com.keda.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {

	private static final String SP_NAME = "config";

	public static SharedPreferences getSp(Context context) {
		return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

	}

	public static void setSp(Context context, String pName, boolean value) {

		SharedPreferences.Editor edit = getSp(context).edit();

		edit.putBoolean(pName, value);

		edit.commit();
	}

	public static boolean get(Context context, String pName) {
		return getSp(context).getBoolean(pName, false);
	}
}
