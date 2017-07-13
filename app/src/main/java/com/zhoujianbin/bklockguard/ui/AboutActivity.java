package com.zhoujianbin.bklockguard.ui;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.ALog;
import com.zhoujianbin.bklockguard.R;
import com.zhoujianbin.bklockguard.base.BaseSwipeBackActivity;
import com.zhoujianbin.bklockguard.config.Constants;
import com.zhoujianbin.bklockguard.util.AlipayUtils;
import com.zhoujianbin.bklockguard.util.ExchangeUtils;
import com.zhoujianbin.bklockguard.util.OtherUtils;
import com.zhoujianbin.bklockguard.util.SnackbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 02:28:44
 * Class Description: 关于界面
 * Comment: 显示软件信息
 ******************************************************************************/

public class AboutActivity extends BaseSwipeBackActivity {

    @BindView(R.id.toolbar_title)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_email)
    AppCompatTextView tvEmail;
    @BindView(R.id.tv_qq_group)
    AppCompatTextView tvQqGroup;
    @BindView(R.id.tv_alipay)
    AppCompatTextView tvAlipay;
    @BindView(R.id.tv_copyright)
    AppCompatTextView tvCopyright;
    @BindView(R.id.tv_version_code)
    AppCompatTextView tvVersionCode;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        // 设置标题
        toolbarTitle.setText(R.string.title_about);
        setSupportActionBar(toolbar);
        // 设置toolbar返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        // 显示版本号
        try {
            tvVersionCode.setText("V " + OtherUtils.getAppVersion(getPackageName()));
        } catch (PackageManager.NameNotFoundException e) {
            ALog.e(e);
        }
    }

    @OnClick({R.id.tv_qq_group, R.id.tv_alipay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_qq_group:
                // 打开QQ群
                if (!ExchangeUtils.joinQQGroup(this, Constants.KEY_QQ_GROUP)) {
                    // 没接入提示
                    SnackbarUtils.with(toolbar).setMessage("手机未安装QQ或安装版本木支持接入 T_T").showError();
                }
                break;
            case R.id.tv_alipay:
                // 打开支付宝打赏
                if (AlipayUtils.hasInstalledAlipayClient(this)) {
                    // 有下载支付宝进行打开支付宝操作
                    AlipayUtils.startAlipayClient(this, Constants.KEY_ALIPAY);
                } else {
                    // 没检测到提示
                    SnackbarUtils.with(toolbar).setMessage("手机木有检测到支付宝客户端 T_T").showError();
                }
                break;
            default:
                break;
        }
    }
}
