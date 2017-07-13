package com.zhoujianbin.bklockguard.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.alibaba.sdk.android.feedback.util.IUnreadCountCallback;
import com.skyfishjy.library.RippleBackground;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.zhoujianbin.bklockguard.R;
import com.zhoujianbin.bklockguard.base.BaseAppCompatActivity;
import com.zhoujianbin.bklockguard.config.Constants;
import com.zhoujianbin.bklockguard.config.SPConfig;
import com.zhoujianbin.bklockguard.module.SoundPlayer;
import com.zhoujianbin.bklockguard.service.AutoProtectService;
import com.zhoujianbin.bklockguard.util.AlipayUtils;
import com.zhoujianbin.bklockguard.util.ExchangeUtils;
import com.zhoujianbin.bklockguard.util.MobSDKUtils;
import com.zhoujianbin.bklockguard.util.SPUtils;
import com.zhoujianbin.bklockguard.util.ScreenUtils;
import com.zhoujianbin.bklockguard.util.ServiceUtils;
import com.zhoujianbin.bklockguard.util.SnackbarUtils;
import com.zhoujianbin.bklockguard.util.VibratorUtils;
import com.zhoujianbin.bklockguard.widget.CustomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import top.wefor.circularanim.CircularAnim;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 02:57:12
 * Class Description: 应用主页
 * Comment: 防盗按钮、设置按钮、互动按钮处理
 ******************************************************************************/

public class MainActivity extends BaseAppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnTouchListener, NavigationView.OnNavigationItemSelectedListener {

    boolean isUpgradeBadgeShow = false;
    boolean isFeedbackBadgeShow = false;
    @BindView(R.id.toolbar_title)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.tb_lock)
    ToggleButton tbLock;
    @BindView(R.id.fab_setting)
    FloatingActionButton fabSetting;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.ib_share)
    AppCompatImageButton ibShare;
    @BindView(R.id.ripple_back)
    RippleBackground rippleBack;
    private Badge feedbackBadge;
    private Badge upgradeBadge;
    private Badge menuBadge;
    private CustomDialog dialogProtectTips;
    private CustomDialog dialogShare;
    private boolean isProtectTips = true;
    private boolean mIsExit = false;
    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放音乐实例
        soundPlayer.release();
    }

    @SuppressLint("NewApi")
    private void initView() {
        // 实例化对象
        soundPlayer = new SoundPlayer(this);
        // 获取升级信息
        if (Beta.getUpgradeInfo() != null) {
            Beta.checkUpgrade();
        }
        // 设置toolbar的标题
        toolbarTitle.setText(R.string.app_name);
        setSupportActionBar(toolbar);
        // 设置toolbar的返回键
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        // 设置菜单显示
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        // 设置红点
        // 获取升级信息
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        if (upgradeInfo != null) {
            isUpgradeBadgeShow = true;
            upgradeBadge = new QBadgeView(this)
                    .bindTarget(navView.getMenu().findItem(R.id.nav_check_upgrade).getActionView().findViewById(R.id.view_badge))
                    .setBadgeText("")
                    .setBadgeGravity(Gravity.CENTER);
        } else {
            isUpgradeBadgeShow = false;
            if (upgradeBadge != null) {
                upgradeBadge.hide(true);
            }
        }
        FeedbackAPI.getFeedbackUnreadCount(new IUnreadCountCallback() {
            @Override
            public void onSuccess(int i) {
                if (i > 0) {
                    isFeedbackBadgeShow = true;
                    feedbackBadge = new QBadgeView(MainActivity.this)
                            .bindTarget(navView.getMenu().findItem(R.id.nav_feedback).getActionView().findViewById(R.id.view_badge))
                            .setBadgeText("")
                            .setBadgeGravity(Gravity.CENTER);
                } else {
                    isFeedbackBadgeShow = false;
                    if (feedbackBadge != null) {
                        feedbackBadge.hide(true);
                    }
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
        // 设置按钮红点
        menuBadge = new QBadgeView(this)
                .bindTarget(toolbar)
                .setBadgeText("")
                .setBadgeGravity(Gravity.START | Gravity.TOP)
                .setGravityOffset(7, true);
        if (!isUpgradeBadgeShow && !isFeedbackBadgeShow) {
            menuBadge.hide(true);
        }
        // 设置自动防盗功能按键
        tbLock.setChecked(SPUtils.getInstance().getBoolean(SPConfig.AUTO_PROTECT));
        if(!tbLock.isChecked()){
            rippleBack.startRippleAnimation();
        }
        tbLock.setOnCheckedChangeListener(this);
        tbLock.setOnTouchListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (mIsExit) {
                this.finish();
            } else {
                SnackbarUtils.with(toolbar).setMessage("再按一次退出应用~~o(>_<)o ~~").showSuccess();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            if (resultCode == 1) {
                tbLock.setChecked(SPUtils.getInstance().getBoolean(SPConfig.AUTO_PROTECT));
            }
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        // 保存开启状态
        SPUtils.getInstance().put(SPConfig.AUTO_PROTECT, isChecked);
        // 播放防盗开启提示音
        soundPlayer.playOpenTone();
        if (SPUtils.getInstance().getBoolean(SPConfig.OPEN_VIBRATOR, SPConfig.OPEN_VIBRATOR_DEFAULT)) {
            VibratorUtils.getInstance().startVibrator(false);
        }
        if (isChecked) {
            // 关闭波纹效果
            rippleBack.stopRippleAnimation();
            // 开启自动防盗服务
            ServiceUtils.startService(AutoProtectService.class);
            if (SPUtils.getInstance().getBoolean(SPConfig.TIPS_AUTO_PROTECT, SPConfig.TIPS_AUTO_PROTECT_DEFAULT)) {
                CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
                dialogProtectTips = builder
                        .style(R.style.Dialog)
                        .heightpx(ScreenUtils.getScreenHeight())
                        .widthpx(ScreenUtils.getScreenWidth())
                        .cancelTouchOut(false)
                        .view(R.layout.dialog_auto_protect)
                        .addViewOnclick(R.id.fab_sure, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 保存是否提示
                                SPUtils.getInstance().put(SPConfig.TIPS_AUTO_PROTECT, isProtectTips);
                                isProtectTips = true;
                                dialogProtectTips.dismiss();
                            }
                        })
                        .addViewOnclick(R.id.fab_detail, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 跳转帮助页面
                                startActivity(new Intent(MainActivity.this, HelpActivity.class));
                            }
                        })
                        .addViewOncheck(R.id.cb_no_notice_again, new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                                // 记录是否提示
                                isProtectTips = !isChecked;
                            }
                        })
                        .build();
                // 显示对话框
                dialogProtectTips.show();
            }
        } else {
            // 显示波纹效果
            rippleBack.startRippleAnimation();
            // 关闭自动防盗
            ServiceUtils.stopService(AutoProtectService.class);
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.tb_lock) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                rippleBack.stopRippleAnimation();
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                rippleBack.startRippleAnimation();
            }
        }
        return false;
    }

    /**
     * 菜单选择回调
     *
     * @param item 菜单项
     * @return 选择点击回调
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_auto_protect:
                // 打开设置页面
                Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(settingIntent, 1);
                break;
            case R.id.nav_about:
                // 打开设置页面
                Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.nav_check_upgrade:
                /*
                  @param isManual  用户手动点击检查，非用户点击操作请传false
                 * @param isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]
                 */
                // 检查新版本
                Beta.checkUpgrade();
                // 处理红点显示
                if (menuBadge != null && upgradeBadge != null && !isFeedbackBadgeShow) {
                    upgradeBadge.hide(true);
                    menuBadge.hide(true);
                }
                break;
            case R.id.nav_feedback:
                // 打开反馈页面
                FeedbackAPI.openFeedbackActivity();
                // 处理红点显示
                if (menuBadge != null && feedbackBadge != null && !isUpgradeBadgeShow) {
                    feedbackBadge.hide(true);
                    menuBadge.hide(true);
                }
                break;
            case R.id.nav_help:
                // 打开帮助页面
                Intent helpIntent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(helpIntent);
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 绑定点击时间处理
     *
     * @param view 点击的view
     */
    @OnClick({R.id.fab_setting, R.id.ib_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_setting:
                // 波纹效果跳转
                CircularAnim.fullActivity(MainActivity.this, fabSetting)
                        .colorOrImageRes(R.color.white)  //注释掉，因为该颜色已经在App.class 里配置为默认色
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                // 跳转到设置页面
                                Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                                startActivityForResult(settingIntent, 1);
                            }
                        });
                break;
            case R.id.ib_share:
                // 设置互动对话框
                CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
                dialogShare = builder
                        .style(R.style.Dialog)
                        .heightpx(ScreenUtils.getScreenHeight())
                        .widthpx(ScreenUtils.getScreenWidth())
                        .cancelTouchOut(false)
                        .view(R.layout.dialog_share)
                        .addViewOnclick(R.id.mrl_share, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 开启分享操作
                                MobSDKUtils.showShare(MainActivity.this);
                                dialogShare.dismiss();
                            }
                        })
                        .addViewOnclick(R.id.mrl_qq, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 打开QQ对话
                                ExchangeUtils.chatQQ(MainActivity.this, Constants.UIN_QQ);
                                dialogShare.dismiss();
                            }
                        })
                        .addViewOnclick(R.id.mrl_qq_group, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 打开QQ群
                                if (!ExchangeUtils.joinQQGroup(MainActivity.this, Constants.KEY_QQ_GROUP)) {
                                    SnackbarUtils.with(toolbar).setMessage("手机未安装QQ或安装版本木支持接入 T_T").showError();
                                }
                                dialogShare.dismiss();
                            }
                        })
                        .addViewOnclick(R.id.mrl_alipay, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 打开支付宝打赏
                                if (AlipayUtils.hasInstalledAlipayClient(MainActivity.this)) {
                                    AlipayUtils.startAlipayClient(MainActivity.this, Constants.KEY_ALIPAY);
                                } else {
                                    SnackbarUtils.with(toolbar).setMessage("手机木有检测到支付宝客户端 T_T").showError();
                                }
                                dialogShare.dismiss();
                            }
                        })
                        .addViewOnclick(R.id.fab_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogShare.dismiss();
                            }
                        })
                        .build();
                // 显示互动对话框
                dialogShare.show();
                break;
            default:
                break;
        }
    }
}
