package com.zhoujianbin.bklockguard;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.alibaba.sdk.android.feedback.util.ErrorCode;
import com.alibaba.sdk.android.feedback.util.FeedbackErrorCallback;
import com.blankj.ALog;
import com.mob.MobSDK;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.mta.track.StatisticsDataAPI;
import com.tencent.stat.StatService;
import com.zhoujianbin.bklockguard.config.Constants;
import com.zhoujianbin.bklockguard.util.MTAUtils;
import com.zhoujianbin.bklockguard.ui.MainActivity;

import java.util.concurrent.Callable;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;
import top.wefor.circularanim.CircularAnim;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 03:12:06
 * Class Description: Application
 * Comment: 自定义Application
 ******************************************************************************/

public class MyApp extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        // 全局上下文赋值
        context = getApplicationContext();
        // 初始化MTA
        initMTA();
        // 初始化ALog
        initALog();
        // 初始化Bugly
        initBugly();
        // 初始化阿里反馈
        initAliFeedback();
        // MobSDK初始化
        MobSDK.init(context, Constants.MobSDK_SHARE_APP_KEY, Constants.MobSDK_SHARE_APP_SECRET);
        // Activity右划返回效果初始化
        BGASwipeBackManager.getInstance().init(this);
        // 跳转页面波纹效果设置
        CircularAnim.init(700, 500, R.color.colorPrimaryDark);
    }

    /**
     * 初始化MTA
     */
    private void initMTA(){
        // 初始化埋点
        StatisticsDataAPI.instance(this);
        // 初始化
        StatService.setContext(this);
        // 初始化MTA配置
        // 参数为true时为DEBUG状态
        MTAUtils.initConfig(Constants.isDebugMode);
        // 注册Activity生命周期监控，自动统计时长
        StatService.registerActivityLifecycleCallbacks(this);
        // 初始化MTA的Crash模块，可监控java、native的Crash，以及Crash后的回调
        MTAUtils.initCrash(this);
    }

    /**
     * 初始化ALog
     */
    private void initALog() {
        // ALog初始设置
        new ALog.Builder(context)
                .setLogSwitch(BuildConfig.DEBUG)// 设置log总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setConsoleFilter(ALog.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
                .setFileFilter(ALog.V);// log文件过滤器，和logcat过滤器同理，默认Verbose
    }

    /**
     * 初始化Bugly
     */
    private void initBugly() {
        // 自动初始化开关；true表示app启动自动初始化升级模块; false不会自动初始化;
        // 开发者如果担心sdk初始化影响app启动速度，可以设置为false，在后面某个时刻手动调用Beta.init(getApplicationContext(),false);
        Beta.autoInit = true;
        // 自动检查更新开关；true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
        Beta.autoCheckUpgrade = false;
        // 升级检查周期设置；设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略);
        Beta.upgradeCheckPeriod = 60 * 1000;
        // 延迟初始化；设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度;
        Beta.initDelay = 3 * 1000;
        // 设置开启显示打断策略；设置点击过确认的弹窗在App下次启动自动检查更新时会再次显示。
        Beta.showInterruptedStrategy = true;
        // 添加可显示弹窗的Activity；如只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗; 如果不设置默认所有activity都可以显示弹窗。
        Beta.canShowUpgradeActs.add(MainActivity.class);
        // 设置Wifi下自动下载；如果你想在Wifi网络下自动下载，可以将这个接口设置为true，默认值为false。
        Beta.autoDownloadOnWifi = true;
        // 设置是否显示消息通知；如果你不想在通知栏显示下载进度，你可以将这个接口设置为false，默认值为true。
        Beta.enableNotification = true;
        // 设置是否显示弹窗中的apk信息；如果你使用我们默认弹窗是会显示apk信息的，如果你不想显示可以将这个接口设置为false。
        Beta.canShowApkInfo = true;
        // 关闭热更新能力；升级SDK默认是开启热更新能力的，如果你不需要使用热更新，可以将这个接口设置为false。
        Beta.enableHotfix = false;
        // 设置更新弹窗默认展示的banner；defaultBannerId为项目中的图片资源Id;
        // 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading...“;
        Beta.defaultBannerId = R.drawable.ic_upgrade_banner;
        // Bugly初始化
        // 参数解析：
        // 参数1：上下文对象
        // 参数2：注册时申请的APPID
        // 参数3：是否开启debug模式，true表示打开debug模式，false表示关闭调试模式
        Bugly.init(this, Constants.BUGLY_APP_ID, Constants.isDebugMode);
    }

    /**
     * 初始化阿里反馈
     */
    private void initAliFeedback(){
        // 添加自定义的error handler
        FeedbackAPI.addErrorCallback(new FeedbackErrorCallback() {
            @Override
            public void onError(Context context, String errorMessage, ErrorCode code) {
                Toast.makeText(context, "ErrMsg is: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        FeedbackAPI.addLeaveCallback(new Callable() {
            @Override
            public Object call() throws Exception {
                ALog.d("custom leave callback");
                return null;
            }
        });
        // 设置字号
        // FeedbackAPI.setHistoryTextSize(20);
        // 沉浸式任务栏
        FeedbackAPI.setTranslucent(true);
        //初始化
        FeedbackAPI.init(this, Constants.ALI_FEEDBACK_APP_KEY,Constants.ALI_FEEDBACK_APP_SECRET);
    }

    /**
     * @return 全局上下文
     */
    public static Context getContext() {
        return context;
    }
}
