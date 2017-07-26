package com.phonesj.news.util;


import com.orhanobut.logger.Logger;
import com.phonesj.news.BuildConfig;

import android.util.Log;

/**
 * Created by codeest on 2016/8/3.
 */
public class LogUtil {

    public static boolean isDebug = BuildConfig.DEBUG;
    private static final String TAG = "phone";

    public static void e(String tag, Object o) {
        if (isDebug) {
            Logger.e(tag, o);
        }
    }

    public static void e(Object o) {
        LogUtil.e(TAG, o);
    }

    public static void w(String tag, Object o) {
        if (isDebug) {
            Logger.w(tag, o);
        }
    }

    public static void w(Object o) {
        LogUtil.w(TAG, o);
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, msg);
        }
    }

    public static void w(String msg) {
        LogUtil.w(TAG, msg);
    }


    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        LogUtil.d(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Logger.i(msg);
        }
    }

    public static void i(String msg) {
        LogUtil.i(TAG, msg);
    }
}
