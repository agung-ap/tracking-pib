package id.developer.trackingpib.util;

import android.content.Context;
import android.content.SharedPreferences;

import id.developer.trackingpib.R;

public class GlobalFunction {

    public static void addUidAndUserStatusPref(Context context, String uid, String userStatus){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.CREDENTIAL),context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.GET_UID), uid);
        editor.putString(context.getString(R.string.GET_USER_STATUS), userStatus);
        editor.apply();
    }
}
