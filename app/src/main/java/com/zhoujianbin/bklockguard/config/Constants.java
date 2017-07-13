package com.zhoujianbin.bklockguard.config;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 02:36:49
 * Class Description: 常量KEY
 * Comment: 平台申请的各种KEY等和应用DEBUG控制
 ******************************************************************************/

public class Constants {

    // DEBUG状态（开启后，第三方等会都一键设置为DEBUG状态）
    public static final boolean isDebugMode = false;
    // QQ群
    public static final String KEY_QQ_GROUP = "VKgTQovaGmgFRpPa24QtmnKNqPTFAjWm";
    // QQ号码
    public static final String UIN_QQ = "369542423";
    // 支付宝打赏
    public static final String KEY_ALIPAY = "fkx05929r55yyszqoh2di75";
    // 广点通
    public static final String GDT_APPID = "1104334871";
    public static final String GDT_SplashPosID = "9000906522712588";
    // Bugly
    public static final String BUGLY_APP_ID = "601512f6ad";
    public static final String BUGLY_APP_KEY = "221edffd-1f89-4af7-a4db-ae6b9e8dff69";
    // MTA
    public static final String MTA_APP_ID = "3102514122";
    public static final String MTA_APP_KEY = "AJ6J84A5QIWN";
    // 阿里反馈
    public static final String ALI_FEEDBACK_APP_KEY = "24533563";
    public static final String ALI_FEEDBACK_APP_SECRET = "89509c65602498adfa37b566ad8ad4de";
    // MobSDK分享
    public static final String MobSDK_SHARE_APP_KEY = "1ce5f10c82b5a";
    public static final String MobSDK_SHARE_APP_SECRET = "678b06d833d82e28090109205d8ec08d";
    // 应用宝应用微下载链接
    public static final String YYB_SHARE_URL = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zhoujianbin.bklockguard";
    // 应用图标外链
    public static final String ICON_IMAGE_URL = "http://i1.buimg.com/599978/3060e8a06cc74f25.png";

    private Constants() {
        throw new IllegalAccessError("Utility class");
    }

}
