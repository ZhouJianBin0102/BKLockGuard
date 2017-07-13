package com.zhoujianbin.bklockguard.service;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.blankj.ALog;
import com.zhoujianbin.bklockguard.R;
import com.zhoujianbin.bklockguard.config.SPConfig;
import com.zhoujianbin.bklockguard.module.SoundPlayer;
import com.zhoujianbin.bklockguard.ui.MainActivity;
import com.zhoujianbin.bklockguard.util.SPUtils;
import com.zhoujianbin.bklockguard.util.ServiceUtils;
import com.zhoujianbin.bklockguard.util.VibratorUtils;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 02:37:50
 * Class Description: 自动防盗服务
 * Comment: 监控防盗各种需要的状态，操作提示音，控制报警服务开启
 ******************************************************************************/

public class AutoProtectService extends Service {

    private SensorManager sensorManager;
    private KeyguardManager keyguardManager;
    private SoundPlayer soundPlayer;
    // USB防护状态记录
    private boolean isUsbGuard = false;
    // USB拔插状态
    private boolean isUsbIn = false;
    // 来电状态记录
    private boolean isPhoneCalling = false;
    private Handler handler = new Handler();

    //口袋防护状态
    private enum PocketProtectState {
        UNLOCK,       // 解锁正常使用
        IN_POCKET,    // 在口袋
        START_GUARD,  // 防盗保护开启
        WARNING_READY // 报警预备
    }

    // 口袋防护状态记录，默认为解锁正常使用状态
    private PocketProtectState pocketProtectState = PocketProtectState.UNLOCK;

    /* 监听状态 */
    private final SensorEventListener functionSensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            // 只使用近距离传感器
            if (event.sensor.getType() != Sensor.TYPE_PROXIMITY) {
                return;
            }
            // 判断是否开启口袋防盗，未开启不防盗处理并重置口袋防护状态记录
            if (!SPUtils.getInstance().getBoolean(SPConfig.AUTO_PROTECT_POCKET, SPConfig.AUTO_PROTECT_POCKET_DEFAULT)) {
                pocketProtectState = PocketProtectState.UNLOCK;
                return;
            }
            // 判断来电状态，来电不进行防盗处理并重置口袋防护状态记录
            if (SPUtils.getInstance().getBoolean(SPConfig.WARNING_CALL_PAUSE, SPConfig.WARNING_CALL_PAUSE_DEFAULT) && isPhoneCalling) {
                pocketProtectState = PocketProtectState.UNLOCK;
                return;
            }
            // 获取传感器取得的参数
            float proximityValue = event.values[0];
            // 根据参数判断距离远近
            if (proximityValue == 0.0) { // 手机距离贴近
                // 如果当前记录状态处于解锁状态且现已锁屏，则变为在口袋状态
                if (pocketProtectState == PocketProtectState.UNLOCK
                        && keyguardManager.inKeyguardRestrictedInputMode()) {
                    // 口袋防护状态记录变为在口袋状态
                    pocketProtectState = PocketProtectState.IN_POCKET;
                    // 延时开启防护
                    handler.postDelayed(pocketGuardRunnable,
                            SPUtils.getInstance().getInt(SPConfig.PROTECT_DELAY, SPConfig.PROTECT_DELAY_DEFAULT) * 1000);

                }
            } else { // 手机距离远离
                // 如果口袋防护状态记录处于保护状态，则变为离开口袋状态，并开启报警服务
                if (pocketProtectState == PocketProtectState.START_GUARD) {
                    // 记录变为报警准备状态
                    pocketProtectState = PocketProtectState.WARNING_READY;
                    // 开启报警服务WarningService
                    ServiceUtils.startService(WarningService.class);
                }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //throw new UnsupportedOperationException("Not yet implemented");
        }
    };

    /**
     * 锁屏广播监控
     * ACTION_SCREEN_ON 开屏
     * ACTION_SCREEN_OFF 锁屏
     * ACTION_USER_PRESENT 解锁
     */
    private final BroadcastReceiver screenBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_USER_PRESENT)) { // 解锁
                // 只要解锁，立刻关闭报警服务WarningService
                ServiceUtils.stopService(WarningService.class);
                // 口袋防护状态记录变为解锁正常使用状态
                pocketProtectState = PocketProtectState.UNLOCK;
                // 只有USB插入状态下，解锁才使USB防盗状态变为解锁正常使用的默认状态
                if (!isUsbIn) {
                    isUsbGuard = false;
                }
            }
        }
    };

    /**
     * 来点状态广播监控
     * CALL_STATE_IDLE 无任何状态时
     * CALL_STATE_RINGING 电话进来时
     * CALL_STATE_OFFHOOK 接起电话时
     * 只要电话进来，isPhoneCalling记录为true
     */
    private final PhoneStateListener phoneListener = new PhoneStateListener() {
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:// 无任何状态时
                    isPhoneCalling = false;
                    break;
                case TelephonyManager.CALL_STATE_RINGING:// 电话进来时
                    isPhoneCalling = true;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:// 接起电话时
                    isPhoneCalling = true;
                    break;
                default:
                    break;
            }
        }
    };


    /* 电池状态全局监控 */
    private final BroadcastReceiver BatteryChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 如果未开启USB防盗则不做处理
            if (!SPUtils.getInstance().getBoolean(SPConfig.AUTO_PROTECT_USB, SPConfig.AUTO_PROTECT_USB_DEFAULT)) {
                return;
            }
            // 只使用电池充电变化监控
            if (!intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                return;
            }
            /*
              获取变化状态
              values为BATTERY_STATUS_NOT_CHARGING时，电池没在充电，所以判断为USB拔出
              values为其它时，说明USB插入
             */
            int values = intent.getIntExtra("plugged", 0);
            ALog.d(values);
            if (values != BatteryManager.BATTERY_STATUS_CHARGING) {// USB拔出
                // 记录拔插状态
                isUsbIn = false;
                // 判断来电状态，来电不进行防盗处理并重置USB防护记录
                if (SPUtils.getInstance().getBoolean(SPConfig.WARNING_CALL_PAUSE, SPConfig.WARNING_CALL_PAUSE_DEFAULT) && isPhoneCalling) {
                    isUsbGuard = false;
                    return;
                }
                // 拔出前开启着防护才进行报警（拔出状态需开启防护才可以报警，避免开启时已经是拔出状态然后立刻报警的异常）
                if (isUsbGuard) {
                    ServiceUtils.startService(WarningService.class);
                }
            } else {
                // USB插入
                // 只要是插入状态都直接进行防护开启
                if (!isUsbGuard && !isUsbIn) {
                    soundPlayer.playProtectTone();
                }
                // 记录拔插状态
                isUsbIn = true;
                isUsbGuard = true;
            }
        }
    };

    /**
     * 口袋防盗开启
     */
    private final Runnable pocketGuardRunnable = new Runnable() {
        @Override
        public void run() {
            // 判断是否在口袋状态
            if (pocketProtectState == PocketProtectState.IN_POCKET) {
                // 判断是否震动
                if (SPUtils.getInstance().getBoolean(SPConfig.PROTECT_VIBRATOR, SPConfig.PROTECT_VIBRATOR_DEFAULT)) {
                    VibratorUtils.getInstance().startVibrator(false);
                }
                // 播放提示音
                soundPlayer.playProtectTone();
                // 口袋防护状态记录为开启防护状态
                pocketProtectState = PocketProtectState.START_GUARD;
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化对象和创建实例
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        soundPlayer = new SoundPlayer(this);
        // 注册USB防盗需要的电池全局监控
        final IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(BatteryChangedReceiver, mIntentFilter);
        // 设置电话状态监听
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        // 注册传感器
        sensorManager.registerListener(functionSensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_UI);
        // 注册监听锁屏解锁状态广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(screenBroadcastReceiver, filter);
        // 通知跳转
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // 设置通知栏样式
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .build();
        // 设置为前台通知
        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 释放实例
        soundPlayer.release();
        // 释放注册对象
        sensorManager.unregisterListener(functionSensorListener);
        unregisterReceiver(screenBroadcastReceiver);
        unregisterReceiver(BatteryChangedReceiver);
        // 关闭前台
        stopForeground(true);
    }

}
