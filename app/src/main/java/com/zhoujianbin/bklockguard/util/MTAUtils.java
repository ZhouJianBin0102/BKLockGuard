package com.zhoujianbin.bklockguard.util;

import android.content.Context;

import com.blankj.ALog;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatCrashCallback;
import com.tencent.stat.StatCrashReporter;
import com.tencent.stat.StatTrackLog;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 03:11:00
 * Class Description: 腾讯MTA配置
 * Comment: 腾讯MTA配置
 ******************************************************************************/

public class MTAUtils {

    /**
     * 根据不同的模式，建议设置的开关状态，可根据实际情况调整，仅供参考。
     *
     * @param isDebugMode
     *            根据调试或发布条件，配置对应的MTA配置
     */
    public static void initConfig(boolean isDebugMode) {

        if (isDebugMode) { // 调试时建议设置的开关状态
            // 查看MTA日志及上报数据内容
            StatConfig.setDebugEnable(true);
            // StatConfig.setEnableSmartReporting(false);
            // Thread.setDefaultUncaughtExceptionHandler(new
            // UncaughtExceptionHandler() {
            //
            // @Override
            // public void uncaughtException(Thread thread, Throwable ex) {
            // logger.error("setDefaultUncaughtExceptionHandler");
            // }
            // });
            // 调试时，使用实时发送
            // StatConfig.setStatSendStrategy(StatReportStrategy.BATCH);
            // // 是否按顺序上报
            // StatConfig.setReportEventsByOrder(false);
            // // 缓存在内存的buffer日志数量,达到这个数量时会被写入db
            // StatConfig.setNumEventsCachedInMemory(30);
            // // 缓存在内存的buffer定期写入的周期
            // StatConfig.setFlushDBSpaceMS(10 * 1000);
            // // 如果用户退出后台，记得调用以下接口，将buffer写入db
            // StatService.flushDataToDB(getApplicationContext());

            // StatConfig.setEnableSmartReporting(false);
            // StatConfig.setSendPeriodMinutes(1);
            // StatConfig.setStatSendStrategy(StatReportStrategy.PERIOD);
        } else { // 发布时，建议设置的开关状态，请确保以下开关是否设置合理
            // 禁止MTA打印日志
            StatConfig.setDebugEnable(false);
            // 根据情况，决定是否开启MTA对app未处理异常的捕获
            StatConfig.setAutoExceptionCaught(true);
            // 选择默认的上报策略
            // 策略设置优先级顺序：wifi条件下智能实时发送>web在线配置>本地默认。
            // SDK本地默认为APP_LAUNCH+wifi下实时上报
            // 对于响应要求比较高的应用，比如竞技类游戏，可关闭wifi实时上报，并选择APP_LAUNCH或PERIOD上报策略。
            // 考虑到wifi上报数据的代价比较小，为了更及时获得用户数据，SDK默认在WIFI网络下实时发送数据。
            // 可以调用下面的接口设置false禁用此功能（在wifi条件下仍使用原定策略）。
            StatConfig.setEnableSmartReporting(true);
            //StatConfig.setStatSendStrategy(StatReportStrategy.PERIOD);
            // （仅在发送策略为PERIOD间隔发送时有效）设置间隔时间（默认为24*60，即1天;
            // 10分钟上报一次的周期
            // StatConfig.setSendPeriodMinutes(10);
            // 锁屏超过X之后再次回到前台即算一次会话,X秒通过下面函数设置，默认为30000ms，即30秒
            // StatConfig.setSessionTimoutMillis(30000);
        }


    }

    public static void initCrash(Context appContext){
        StatCrashReporter crashReporter = StatCrashReporter.getStatCrashReporter(appContext);
        // 开启异常时的实时上报
        crashReporter.setEnableInstantReporting(true);
        // 开启java异常捕获
        crashReporter.setJavaCrashHandlerStatus(true);
        // 开启Native c/c++，即so的异常捕获
        // 请根据需要添加，记得so文件
        crashReporter.setJniNativeCrashStatus(true);
        // 添加关键日志，若crash时会把关键日志信息一起上报并展示，协助定位
        StatTrackLog.log("init module");
        // 添加Crash的标签，crash时会自动上报并展示，协助定位
        StatConfig.setCrashKeyValue("myTag", "myValue");
        // crash时的回调，业务可根据需要自选决定是否添加
        crashReporter.addCrashCallback(new StatCrashCallback() {

            @Override
            public void onJniNativeCrash(String tombstoneString) {
                // native dump内容，包含异常信号、进程、线程、寄存器、堆栈等信息
                // 具体请参考：Android原生的tombstone文件格式
                ALog.d("app", "MTA StatCrashCallback onJniNativeCrash:\n" + tombstoneString);
            }

            @Override
            public void onJavaCrash(Thread thread, Throwable ex) {
                //thread:crash线程信息
                // ex:crash堆栈
                ALog.d("app", "MTA StatCrashCallback onJavaCrash:\n", ex);
            }
        });
    }
}
