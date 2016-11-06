package com.example.utils;

import com.example.agt.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CommonUtils
{

	public static boolean isNetworkAvailable(Context context)
	{
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected())
		{
			return true;
		}
		return false;
	}

	public static Boolean isUserLoggedInToApplication(Context ctx)
	{
		SharedPreferences loginPrefs = ctx.getSharedPreferences(ctx.getString(R.string.login_prefs), Context.MODE_PRIVATE);
		if (loginPrefs.getString(ctx.getString(R.string.key_is_signed_in), "").equals("true"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
