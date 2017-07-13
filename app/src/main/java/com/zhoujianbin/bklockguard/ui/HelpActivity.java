package com.zhoujianbin.bklockguard.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;

import com.zhoujianbin.bklockguard.R;
import com.zhoujianbin.bklockguard.base.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 02:29:15
 * Class Description: 帮助界面
 * Comment: 显示帮助信息
 ******************************************************************************/

public class HelpActivity extends BaseSwipeBackActivity {

    @BindView(R.id.toolbar_title)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        // 设置标题
        toolbarTitle.setText(R.string.title_help);
        setSupportActionBar(toolbar);
        // 设置返回键
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }
}
