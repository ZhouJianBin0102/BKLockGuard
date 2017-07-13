package com.zhoujianbin.bklockguard.util;

import android.content.Context;

import com.zhoujianbin.bklockguard.MyApp;

/**
 * Copyright©2017-zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/6/27 4:47
 * Class Description: 工具类
 * Comment: 工具类
 */

public final class Utils {

    private Utils() {
        throw new IllegalAccessError("Utility class");
    }

    public static Context getContext(){
        return MyApp.getContext();
    }
}
