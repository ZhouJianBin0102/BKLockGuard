package com.zhoujianbin.bklockguard.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suke.widget.SwitchButton;
import com.zhoujianbin.bklockguard.R;
import com.zhoujianbin.bklockguard.base.BaseSwipeBackActivity;
import com.zhoujianbin.bklockguard.config.SPConfig;
import com.zhoujianbin.bklockguard.module.SoundPlayer;
import com.zhoujianbin.bklockguard.util.SPUtils;
import com.zhoujianbin.bklockguard.util.SnackbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 03:05:02
 * Class Description: 设置页面
 * Comment: 配置设置项
 ******************************************************************************/

public class SettingActivity extends BaseSwipeBackActivity implements SwitchButton.OnCheckedChangeListener {


    public String[] toneItems;
    public String[] warningItems;
    private String[] protectDelays;
    private String[] warningDelays;
    // 请求码
    private static final int REQUEST_OPEN_TONE = 1;
    private static final int REQUEST_PROTECT_TONE = 2;
    private static final int REQUEST_WARNING_SOUND = 3;

    private SoundPlayer soundPlayer;

    @BindView(R.id.toolbar_title)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.sb_protect_pocket)
    SwitchButton sbProtectPocket;
    @BindView(R.id.ll_protect_pocket)
    LinearLayout llProtectPocket;
    @BindView(R.id.sb_protect_usb)
    SwitchButton sbProtectUsb;
    @BindView(R.id.ll_protect_usb)
    LinearLayout llProtectUsb;
    @BindView(R.id.tv_open_tone)
    TextView tvOpenTone;
    @BindView(R.id.ll_open_tone)
    LinearLayout llOpenTone;
    @BindView(R.id.sb_open_vibrator)
    SwitchButton sbOpenVibrator;
    @BindView(R.id.ll_open_vibrator)
    LinearLayout llOpenVibrator;
    @BindView(R.id.tv_protect_delay)
    TextView tvProtectDelay;
    @BindView(R.id.ll_protect_delay)
    LinearLayout llProtectDelay;
    @BindView(R.id.tv_protect_tone)
    TextView tvProtectTone;
    @BindView(R.id.ll_protect_tone)
    LinearLayout llProtectTone;
    @BindView(R.id.sb_protect_vibrator)
    SwitchButton sbProtectVibrator;
    @BindView(R.id.ll_protect_vibrator)
    LinearLayout llProtectVibrator;
    @BindView(R.id.tv_warning_delay)
    TextView tvWarningDelay;
    @BindView(R.id.tv_warning_sound)
    TextView tvWarningSound;
    @BindView(R.id.ll_warning_sound)
    LinearLayout llWarningSound;
    @BindView(R.id.sb_warning_vibrator)
    SwitchButton sbWarningVibrator;
    @BindView(R.id.ll_warning_vibrator)
    LinearLayout llWarningVibrator;
    @BindView(R.id.sb_warning_loud)
    SwitchButton sbWarningLoud;
    @BindView(R.id.ll_warning_loud)
    LinearLayout llWarningLoud;
    @BindView(R.id.sb_warning_call_pause)
    SwitchButton sbWarningCallPause;
    @BindView(R.id.ll_warning_call_pause)
    LinearLayout llWarningCallPause;
    @BindView(R.id.ll_warning_delay)
    LinearLayout llWarningDelay;
    @BindView(R.id.sb_other_boot)
    SwitchButton sbOtherBoot;
    @BindView(R.id.ll_other_boot)
    LinearLayout llOtherBoot;
    @BindView(R.id.sb_auto_protect_tips)
    SwitchButton sbAutoProtectTips;
    @BindView(R.id.ll_auto_protect_tips)
    LinearLayout llAutoProtectTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放音乐实例
        soundPlayer.release();
    }

    private void initView() {
        soundPlayer = new SoundPlayer(this);
        // 准备使用的数据
        toneItems = getResources().getStringArray(R.array.sound_tones);
        warningItems = getResources().getStringArray(R.array.sound_warning);
        protectDelays = getResources().getStringArray(R.array.delays_protect);
        warningDelays = getResources().getStringArray(R.array.delays_warning);
        // 设置项文本
        tvOpenTone.setText(SPUtils.getInstance().getString(SPConfig.OPEN_SOUND_TONE_NAME, SPConfig.OPEN_SOUND_TONE_NAME_DEFAULT));
        tvProtectTone.setText(SPUtils.getInstance().getString(SPConfig.PROTECT_SOUND_TONE_NAME, SPConfig.PROTECT_SOUND_TONE_NAME_DEFAULT));
        tvWarningSound.setText(SPUtils.getInstance().getString(SPConfig.WARNING_SOUND_NAME, SPConfig.WARNING_SOUND_NAME_DEFAULT));
        tvProtectDelay.setText(protectDelays[SPUtils.getInstance().getInt(SPConfig.PROTECT_DELAY, SPConfig.PROTECT_DELAY_DEFAULT)]);
        tvWarningDelay.setText(warningDelays[SPUtils.getInstance().getInt(SPConfig.WARNING_SOUND_DELAY, SPConfig.WARNING_SOUND_DELAY_DEFAULT)]);
        // 设置项开关
        sbProtectPocket.setChecked(SPUtils.getInstance().getBoolean(SPConfig.AUTO_PROTECT_POCKET, SPConfig.AUTO_PROTECT_POCKET_DEFAULT));
        sbProtectUsb.setChecked(SPUtils.getInstance().getBoolean(SPConfig.AUTO_PROTECT_USB, SPConfig.AUTO_PROTECT_USB_DEFAULT));
        sbOpenVibrator.setChecked(SPUtils.getInstance().getBoolean(SPConfig.OPEN_VIBRATOR, SPConfig.OPEN_VIBRATOR_DEFAULT));
        sbProtectVibrator.setChecked(SPUtils.getInstance().getBoolean(SPConfig.PROTECT_VIBRATOR, SPConfig.PROTECT_VIBRATOR_DEFAULT));
        sbWarningVibrator.setChecked(SPUtils.getInstance().getBoolean(SPConfig.WARNING_VIBRATOR, SPConfig.WARNING_VIBRATOR_DEFAULT));
        sbWarningLoud.setChecked(SPUtils.getInstance().getBoolean(SPConfig.WARNING_LOUD, SPConfig.WARNING_LOUD_DEFAULT));
        sbWarningCallPause.setChecked(SPUtils.getInstance().getBoolean(SPConfig.WARNING_CALL_PAUSE, SPConfig.WARNING_CALL_PAUSE_DEFAULT));
        sbOtherBoot.setChecked(SPUtils.getInstance().getBoolean(SPConfig.AUTO_BOOT, SPConfig.AUTO_BOOT_DEFAULT));
        sbAutoProtectTips.setChecked(SPUtils.getInstance().getBoolean(SPConfig.TIPS_AUTO_PROTECT, SPConfig.TIPS_AUTO_PROTECT_DEFAULT));
        // 设置项绑定开关事件
        sbProtectPocket.setOnCheckedChangeListener(this);
        sbProtectUsb.setOnCheckedChangeListener(this);
        sbOpenVibrator.setOnCheckedChangeListener(this);
        sbProtectVibrator.setOnCheckedChangeListener(this);
        sbWarningVibrator.setOnCheckedChangeListener(this);
        sbWarningLoud.setOnCheckedChangeListener(this);
        sbWarningCallPause.setOnCheckedChangeListener(this);
        sbOtherBoot.setOnCheckedChangeListener(this);
        sbAutoProtectTips.setOnCheckedChangeListener(this);
        // 设置标题和返回按钮
        toolbarTitle.setText(R.string.setting_protect);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    // 开关事件
    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.sb_protect_pocket:
                SPUtils.getInstance().put(SPConfig.AUTO_PROTECT_POCKET, isChecked);
                break;
            case R.id.sb_protect_usb:
                SPUtils.getInstance().put(SPConfig.AUTO_PROTECT_USB, isChecked);
                break;
            case R.id.sb_open_vibrator:
                SPUtils.getInstance().put(SPConfig.OPEN_VIBRATOR, isChecked);
                break;
            case R.id.sb_protect_vibrator:
                SPUtils.getInstance().put(SPConfig.PROTECT_VIBRATOR, isChecked);
                break;
            case R.id.sb_warning_vibrator:
                SPUtils.getInstance().put(SPConfig.WARNING_VIBRATOR, isChecked);
                break;
            case R.id.sb_warning_loud:
                SPUtils.getInstance().put(SPConfig.WARNING_LOUD, isChecked);
                break;
            case R.id.sb_warning_call_pause:
                SPUtils.getInstance().put(SPConfig.WARNING_CALL_PAUSE, isChecked);
                break;
            case R.id.sb_other_boot:
                SPUtils.getInstance().put(SPConfig.AUTO_BOOT, isChecked);
                break;
            case R.id.sb_auto_protect_tips:
                SPUtils.getInstance().put(SPConfig.TIPS_AUTO_PROTECT, isChecked);
                break;
            default:
                break;
        }
    }

    //点击事件
    @OnClick({R.id.ll_protect_pocket, R.id.ll_protect_usb, R.id.ll_open_tone, R.id.ll_open_vibrator, R.id.ll_protect_delay, R.id.ll_protect_tone, R.id.ll_protect_vibrator, R.id.ll_warning_sound, R.id.ll_warning_vibrator, R.id.ll_warning_loud, R.id.ll_warning_call_pause, R.id.ll_warning_delay, R.id.ll_other_boot, R.id.ll_auto_protect_tips})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_protect_pocket:
                if (sbProtectPocket.isChecked() && !sbProtectUsb.isChecked()) {
                    SnackbarUtils.with(toolbar).setMessage("防盗模式不能都关闭").showWarning();
                } else {
                    sbProtectPocket.setChecked(!sbProtectPocket.isChecked());
                }
                break;
            case R.id.ll_protect_usb:
                if (!sbProtectPocket.isChecked() && sbProtectUsb.isChecked()) {
                    SnackbarUtils.with(toolbar).setMessage("防盗模式不能都关闭").showWarning();
                } else {
                    sbProtectUsb.setChecked(!sbProtectUsb.isChecked());
                }
                break;
            case R.id.ll_open_vibrator:
                sbOpenVibrator.setChecked(!sbOpenVibrator.isChecked());
                break;
            case R.id.ll_protect_vibrator:
                sbProtectVibrator.setChecked(!sbProtectVibrator.isChecked());
                break;
            case R.id.ll_warning_vibrator:
                sbWarningVibrator.setChecked(!sbWarningVibrator.isChecked());
                break;
            case R.id.ll_warning_loud:
                sbWarningLoud.setChecked(!sbWarningLoud.isChecked());
                break;
            case R.id.ll_warning_call_pause:
                sbWarningCallPause.setChecked(!sbWarningCallPause.isChecked());
                break;
            case R.id.ll_other_boot:
                sbOtherBoot.setChecked(!sbOtherBoot.isChecked());
                break;
            case R.id.ll_auto_protect_tips:
                sbAutoProtectTips.setChecked(!sbAutoProtectTips.isChecked());
                break;
            case R.id.ll_open_tone:
                showSoundChooser(R.string.open_tone,
                        toneItems,
                        SPUtils.getInstance().getInt(SPConfig.OPEN_SOUND_TONE_RAW, SPConfig.OPEN_SOUND_TONE_RAW_DEFAULT),
                        tvOpenTone,
                        SPConfig.OPEN_SOUND_TONE_RAW,
                        SPConfig.OPEN_SOUND_TONE_NAME,
                        REQUEST_OPEN_TONE);
                break;
            case R.id.ll_protect_tone:
                showSoundChooser(R.string.protect_tone,
                        toneItems,
                        SPUtils.getInstance().getInt(SPConfig.PROTECT_SOUND_TONE_RAW, SPConfig.PROTECT_SOUND_TONE_RAW_DEFAULT),
                        tvProtectTone,
                        SPConfig.PROTECT_SOUND_TONE_RAW,
                        SPConfig.PROTECT_SOUND_TONE_NAME,
                        REQUEST_PROTECT_TONE);
                break;
            case R.id.ll_warning_sound:
                showSoundChooser(R.string.warning_sound,
                        warningItems,
                        SPUtils.getInstance().getInt(SPConfig.WARNING_SOUND_RAW, SPConfig.WARNING_SOUND_RAW_DEFAULT),
                        tvWarningSound,
                        SPConfig.WARNING_SOUND_RAW,
                        SPConfig.WARNING_SOUND_NAME,
                        REQUEST_WARNING_SOUND);
                break;
            case R.id.ll_protect_delay:
                showDelayChooser(R.string.protect_delay,
                        protectDelays,
                        SPUtils.getInstance().getInt(SPConfig.PROTECT_DELAY, SPConfig.PROTECT_DELAY_DEFAULT),
                        tvProtectDelay,
                        SPConfig.PROTECT_DELAY);
                break;
            case R.id.ll_warning_delay:
                showDelayChooser(R.string.waning_delay,
                        warningDelays,
                        SPUtils.getInstance().getInt(SPConfig.WARNING_SOUND_DELAY, SPConfig.WARNING_SOUND_DELAY_DEFAULT),
                        tvWarningDelay,
                        SPConfig.WARNING_SOUND_DELAY);
                break;
            default:
                break;
        }
    }

    /**
     * 设置延时项选择弹窗
     *
     * @param titleId   标题资源id
     * @param items     选项数据
     * @param saveIndex 保存当前选中设置
     * @param textView  修改设置项保存文本
     * @param keySave   保存KEY
     */
    private void showDelayChooser(int titleId,
                                  final String[] items,
                                  int saveIndex,
                                  final TextView textView,
                                  final String keySave) {
        final int[] chooseDex = {saveIndex};
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle(titleId)
                .setSingleChoiceItems(items, saveIndex,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                chooseDex[0] = i;
                            }
                        })
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        SPUtils.getInstance().put(keySave, chooseDex[0]);
                        textView.setText(items[chooseDex[0]]);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    /**
     * 设置声音选择弹窗
     *
     * @param titleId     标题资源id
     * @param items       选项数据
     * @param saveIndex   保存的当前选中设置
     * @param textView    修改设置项保存文本
     * @param keySaveId   保存音乐文件对应ID的KEY
     * @param keySaveName 保存音乐文件对应名称Name的KEY
     * @param requestCode 请求编码，用于区分和处理返回是哪个打开音乐自定义选择列表
     */
    private void showSoundChooser(int titleId,
                                  final String[] items,
                                  int saveIndex,
                                  final TextView textView,
                                  final String keySaveId,
                                  final String keySaveName,
                                  final int requestCode) {
        final int[] chooseDex = {saveIndex};
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle(titleId)
                .setSingleChoiceItems(items, saveIndex,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                // 判断选项是不是自定义
                                if (i == 3) {
                                    Intent intent = new Intent(SettingActivity.this, SoundFileActivity.class);
                                    // 判断requestCode是不是请求警报声的，是的话传值为true使自定义播放为循环播放
                                    intent.putExtra(SoundFileActivity.IS_SOUND_LOOPING, requestCode == REQUEST_WARNING_SOUND);
                                    startActivityForResult(intent, requestCode);
                                    dialog.dismiss();
                                } else {
                                    chooseDex[0] = i;
                                    // 判断requestCode是不是请求警报声的
                                    if (requestCode == REQUEST_WARNING_SOUND) {
                                        soundPlayer.playWarningRaw(i);
                                    } else {
                                        soundPlayer.playToneRaw(i);
                                    }
                                }
                            }
                        })
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        // 保存选择
                        SPUtils.getInstance().put(keySaveId, chooseDex[0]);
                        SPUtils.getInstance().put(keySaveName, items[chooseDex[0]]);
                        textView.setText(items[chooseDex[0]]);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        soundPlayer.stop();
                    }
                })
                .create();
        dialog.show();
    }

    /**
     * 处理返回数据
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 请求码为OK才处理
        if (resultCode != RESULT_OK) {
            return;
        }
        // 把返回的数据进行显示和保存
        switch (requestCode) {
            case REQUEST_OPEN_TONE:
                tvOpenTone.setText(data.getStringExtra(SoundFileActivity.RESULT_FILE_NAME));
                SPUtils.getInstance().put(SPConfig.OPEN_SOUND_TONE_NAME, data.getStringExtra(SoundFileActivity.RESULT_FILE_NAME));
                SPUtils.getInstance().put(SPConfig.OPEN_SOUND_TONE_RAW, 3);
                SPUtils.getInstance().put(SPConfig.OPEN_SOUND_TONE_PATH, data.getStringExtra(SoundFileActivity.RESULT_FILE_DATA));
                break;
            case REQUEST_PROTECT_TONE:
                tvProtectTone.setText(data.getStringExtra(SoundFileActivity.RESULT_FILE_NAME));
                SPUtils.getInstance().put(SPConfig.PROTECT_SOUND_TONE_NAME, data.getStringExtra(SoundFileActivity.RESULT_FILE_NAME));
                SPUtils.getInstance().put(SPConfig.PROTECT_SOUND_TONE_RAW, 3);
                SPUtils.getInstance().put(SPConfig.PROTECT_SOUND_TONE_PATH, data.getStringExtra(SoundFileActivity.RESULT_FILE_DATA));
                break;
            case REQUEST_WARNING_SOUND:
                tvWarningSound.setText(data.getStringExtra(SoundFileActivity.RESULT_FILE_NAME));
                SPUtils.getInstance().put(SPConfig.WARNING_SOUND_NAME, data.getStringExtra(SoundFileActivity.RESULT_FILE_NAME));
                SPUtils.getInstance().put(SPConfig.WARNING_SOUND_RAW, 3);
                SPUtils.getInstance().put(SPConfig.WARNING_SOUND_PATH, data.getStringExtra(SoundFileActivity.RESULT_FILE_DATA));
                break;
            default:
                break;
        }
    }
}
