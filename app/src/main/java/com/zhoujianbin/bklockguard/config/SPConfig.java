package com.zhoujianbin.bklockguard.config;

import com.zhoujianbin.bklockguard.MyApp;
import com.zhoujianbin.bklockguard.R;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 02:37:08
 * Class Description: SharedPreference的KEY和DEFAULT_VALUE
 * Comment: 默认SP的KEY和默认值
 ******************************************************************************/

public class SPConfig {

    // 第一次打开应用
    public static final String START_APP_FIRST = "START_APP_FIRST";
    public static final boolean START_APP_FIRST_DEFAULT = false;
    // 自动防盗提示
    public static final String TIPS_AUTO_PROTECT = "TIPS_AUTO_PROTECT";
    public static final boolean TIPS_AUTO_PROTECT_DEFAULT = true;
    // 开机自启动
    public static final String AUTO_BOOT = "AUTO_BOOT";
    public static final boolean AUTO_BOOT_DEFAULT = false;
    // 自动防盗开启
    public static final String AUTO_PROTECT = "AUTO_PROTECT";
    public static final boolean AUTO_PROTECT_DEFAULT = false;
    // 口袋防盗
    public static final String AUTO_PROTECT_POCKET = "AUTO_PROTECT_POCKET";
    public static final boolean AUTO_PROTECT_POCKET_DEFAULT = true;
    // USB防盗
    public static final String AUTO_PROTECT_USB = "AUTO_PROTECT_USB";
    public static final boolean AUTO_PROTECT_USB_DEFAULT = true;
    /**
     * 启动提示
     */
    // 开启提示音存储
    public static final String OPEN_SOUND_TONE_NAME = "OPEN_SOUND_TONE_NAME";
    public static final String OPEN_SOUND_TONE_NAME_DEFAULT = MyApp.getContext().getResources().getStringArray(R.array.sound_tones)[0];
    public static final String OPEN_SOUND_TONE_PATH = "OPEN_SOUND_TONE_PATH";
    public static final String OPEN_SOUND_TONE_RAW = "OPEN_SOUND_TONE_RAW";
    public static final int OPEN_SOUND_TONE_RAW_DEFAULT = 0;
    // 开启震动
    public static final String OPEN_VIBRATOR = "OPEN_VIBRATOR";
    public static final boolean OPEN_VIBRATOR_DEFAULT = true;
    /**
     * 防护提示
     */
    // 防护开启缓冲时间
    public static final String PROTECT_DELAY = "PROTECT_DELAY";
    public static final int PROTECT_DELAY_DEFAULT = 2;
    // 防护提示音存储
    public static final String PROTECT_SOUND_TONE_NAME = "PROTECT_SOUND_TONE_NAME";
    public static final String PROTECT_SOUND_TONE_NAME_DEFAULT = MyApp.getContext().getResources().getStringArray(R.array.sound_tones)[0];
    public static final String PROTECT_SOUND_TONE_PATH = "PROTECT_SOUND_TONE_PATH";
    public static final String PROTECT_SOUND_TONE_RAW = "PROTECT_SOUND_TONE_RAW";
    public static final int PROTECT_SOUND_TONE_RAW_DEFAULT = 0;
    // 防护震动
    public static final String PROTECT_VIBRATOR = "PROTECT_VIBRATOR";
    public static final boolean PROTECT_VIBRATOR_DEFAULT = true;
    /**
     * 报警提示
     */
    // 报警开启缓冲时间
    public static final String WARNING_SOUND_DELAY = "PROTECT_WARNING_SOUND_DELAY";
    public static final int WARNING_SOUND_DELAY_DEFAULT = 5;
    // 报警音存储
    public static final String WARNING_SOUND_NAME = "WARNING_SOUND_TONE_NAME";
    public static final String WARNING_SOUND_NAME_DEFAULT = MyApp.getContext().getResources().getStringArray(R.array.sound_warning)[0];
    public static final String WARNING_SOUND_PATH = "WARNING_SOUND_PATH";
    public static final String WARNING_SOUND_RAW = "WARNING_SOUND_RAW";
    public static final int WARNING_SOUND_RAW_DEFAULT = 0;
    // 报警震动
    public static final String WARNING_VIBRATOR = "WARNING_VIBRATOR";
    public static final boolean WARNING_VIBRATOR_DEFAULT = true;
    // 报警音量保持最大
    public static final String WARNING_LOUD = "WARNING_LOUD";
    public static final boolean WARNING_LOUD_DEFAULT = true;
    // 来电话暂停
    public static final String WARNING_CALL_PAUSE = "WARNING_CALL_PAUSE";
    public static final boolean WARNING_CALL_PAUSE_DEFAULT = true;

    private SPConfig() {
        throw new IllegalAccessError("Utility class");
    }
}
