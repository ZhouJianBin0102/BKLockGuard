package com.zhoujianbin.bklockguard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhoujianbin.bklockguard.config.SPConfig;
import com.zhoujianbin.bklockguard.service.AutoProtectService;
import com.zhoujianbin.bklockguard.util.SPUtils;
import com.zhoujianbin.bklockguard.util.ServiceUtils;

/*******************************************************************************
 * Copyright©2017-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 02:23:54
 * Class Description: 开机启动广播
 * Comment: 开机启动防盗服务
 ******************************************************************************/

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 判断是否开机自启动和是否开启了自动防盗
        if (SPUtils.getInstance().getBoolean(SPConfig.AUTO_BOOT, SPConfig.AUTO_BOOT_DEFAULT)
                && SPUtils.getInstance().getBoolean(SPConfig.AUTO_PROTECT, SPConfig.AUTO_PROTECT_DEFAULT)) {
            // 启动自动防盗服务
            ServiceUtils.startService(AutoProtectService.class);
        }
    }


}
