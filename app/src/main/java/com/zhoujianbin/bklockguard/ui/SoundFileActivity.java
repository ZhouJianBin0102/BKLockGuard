package com.zhoujianbin.bklockguard.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.ALog;
import com.zhoujianbin.bklockguard.R;
import com.zhoujianbin.bklockguard.base.BaseSoundPlayer;
import com.zhoujianbin.bklockguard.base.BaseSwipeBackActivity;
import com.zhoujianbin.bklockguard.base.SoundFileAdapter;
import com.zhoujianbin.bklockguard.bean.SoundFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 03:06:39
 * Class Description: 音乐文件选择页面
 * Comment: 显示音乐文件列表、点击播放处理
 ******************************************************************************/

public class SoundFileActivity extends BaseSwipeBackActivity {

    @BindView(R.id.toolbar_title)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.bt_cancel)
    AppCompatButton btCancel;
    @BindView(R.id.bt_sure)
    AppCompatButton btSure;

    private List<SoundFile> soundFiles = new ArrayList<>();
    private SoundFileAdapter soundFileAdapter;
    public static final String RESULT_FILE_NAME = "RESULT_FILE_NAME";
    public static final String RESULT_FILE_DATA = "RESULT_FILE_DATA";
    public static final String IS_SOUND_LOOPING = "IS_SOUND_LOOPING";

    private BaseSoundPlayer baseSoundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_file);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放音乐实例
        baseSoundPlayer.release();
    }

    private void initView() {
        // 实例化对象
        baseSoundPlayer = new BaseSoundPlayer();
        // 显示toolbar返回按钮
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        // 获取手机音乐文件列表
        soundFiles = getSoundFiles();
        if (!soundFiles.isEmpty()) {
            // 设置RecycleView
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recycleView.setLayoutManager(layoutManager);
            soundFileAdapter = new SoundFileAdapter(recycleView, soundFiles, baseSoundPlayer,getIntent().getBooleanExtra(IS_SOUND_LOOPING, false));
            recycleView.setAdapter(soundFileAdapter);

        }
    }

    /**
     * @return 返回手机中的音乐文件列表SoundFileList
     */
    private List<SoundFile> getSoundFiles() {
        List<SoundFile> files = new ArrayList<>();
        SoundFile soundFile = new SoundFile();
        // 遍历查询音乐文件
        try {
            @SuppressLint("Recycle")
            Cursor cursor = getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    String title = cursor.getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                    String data = cursor.getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    soundFile.setTitle(title);
                    soundFile.setData(data);
                    files.add(soundFile);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            ALog.e(e);
        }
        return files;
    }

    @OnClick({R.id.bt_cancel, R.id.bt_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_cancel:
                finish();
                break;
            case R.id.bt_sure:
                // 确定设置音乐文件
                Intent intent = new Intent();
                if (soundFiles != null && soundFileAdapter != null) {
                    // 只有选择了列表文件才进行返回保存
                    if (soundFileAdapter.getSelectedPos() != -1) {
                        intent.putExtra(RESULT_FILE_NAME, soundFiles.get(soundFileAdapter.getSelectedPos()).getTitle());
                        intent.putExtra(RESULT_FILE_DATA, soundFiles.get(soundFileAdapter.getSelectedPos()).getData());
                        setResult(RESULT_OK, intent);
                    }
                } else {
                    setResult(RESULT_CANCELED, intent);
                }
                finish();
                break;
            default:
                break;
        }
    }
}
