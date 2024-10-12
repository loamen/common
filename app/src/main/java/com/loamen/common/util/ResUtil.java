package com.loamen.common.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class ResUtil {

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }
}
