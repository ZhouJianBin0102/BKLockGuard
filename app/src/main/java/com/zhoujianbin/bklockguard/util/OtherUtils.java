package com.zhoujianbin.bklockguard.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.zhoujianbin.bklockguard.MyApp;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 03:11:26
 * Class Description: 其它工具类
 * Comment: 其它工具类
 ******************************************************************************/

public final class OtherUtils {

    /**
     * 获取单个App版本号
     **/
    public static String getAppVersion(String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = MyApp.getContext().getPackageManager().getPackageInfo(packageName, 0);
        return packageInfo.versionName;
    }

    /**
     * 获取设备码*/

    @SuppressLint("HardwareIds")
    public static String getDeviceInfo(Context context) {
        return ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }
}
