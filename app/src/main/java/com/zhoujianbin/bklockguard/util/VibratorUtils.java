package com.zhoujianbin.bklockguard.util;

import android.content.Context;
import android.os.Vibrator;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 09:30:11
 * Class Description: 震动工具类
 * Comment: 震动工具类
 ******************************************************************************/

public final class VibratorUtils {
    private Vibrator vibrator;

    public VibratorUtils() {
        vibrator = (Vibrator) Utils.getContext()
                .getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static VibratorUtils getInstance() {
        return new VibratorUtils();
    }

    public void startVibrator(boolean isLooping) {
        if (vibrator != null) {
            vibrator.cancel();
            if (isLooping) {
                long[] pattern = {1000, 1000, 1000, 1000};
                vibrator.vibrate(pattern, 0);
            } else {
                long[] pattern = {100, 1000};
                vibrator.vibrate(pattern, -1);
            }
        }
    }

    public void stopVibrator() {
        if (vibrator != null)
            vibrator.cancel();
    }
}
