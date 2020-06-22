package com.bahaa.task.core.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

public class Utils {

    public static boolean isNetworkConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
