package com.zhoujianbin.bklockguard.base;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.tencent.stat.StatService;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 02:33:21
 * Class Description: AppCompatActivity积累
 * Comment: 进行一些初始化操作
 ******************************************************************************/

public class BaseAppCompatActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        // MTA
        // 页面开始
        // 注意：每次调用，MTA会检查是否产生新会话（session超时），即生成启动次数。
        // void StatService.onResume(Context ctx)或void StatService.trackBeginPage(Context ctx, String pageName)
        // pageName 自定义页面名称，需跟trackEndPage一一匹配使用
        // 注意：onResume和onPause或trackBeginPage和trackEndPage需要成对使用才能正常统计activity，
        // 为了统计准确性，建议在每个activity中都调用以上接口，否则可能会导致MTA上报过多的启动次数，
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // MTA
        // 页面结束
        // void StatService.onPause (Context ctx)或void StatService.trackEndPage(Context ctx, String pageName)
        StatService.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        StatService.onStop(this);
    }

    /**
     * 设置点击toolbar的返回按钮关闭当前activity
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
