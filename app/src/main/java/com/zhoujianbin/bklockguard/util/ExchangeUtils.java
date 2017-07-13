package com.zhoujianbin.bklockguard.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.blankj.ALog;



/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 03:09:39
 * Class Description: 互动工具类
 * Comment: 打开QQ对话，连接QQ群
 ******************************************************************************/

public class ExchangeUtils {

    private ExchangeUtils() {
        throw new IllegalAccessError("Utility class");
    }

    /****************
     * @param uinQQ 要发起临时对话的QQ号码
     * @return 返回true表示呼起手Q成功，返回false表示呼起失败
     ******************/
    public static boolean chatQQ(Context context ,String uinQQ) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + uinQQ));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            ALog.e(e);
            return false;
        }
    }

    /****************
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回false表示呼起失败
     ******************/
    public static boolean joinQQGroup(Context context ,String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            ALog.e(e);
            return false;
        }
    }
}
