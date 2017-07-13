package com.zhoujianbin.bklockguard.base;

import android.content.Context;
import android.media.MediaPlayer;

import com.blankj.ALog;
/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 02:34:13
 * Class Description: 播放积累
 * Comment: 扩展MediaPlayer
 ******************************************************************************/
public class BaseSoundPlayer extends MediaPlayer {

    private MediaPlayer mediaPlayer;

    /**
     * 创建实例方法
     */
    public BaseSoundPlayer() {
        this.mediaPlayer = new MediaPlayer();
    }

    /**
     * 播放声音（文件路径）
     * @param path 文件路径
     * @param isLooping 是否循环
     */
    public void playPath(String path, boolean isLooping) {
        try {
            //mediaPlayer.reset();
            // 设置文件路径
            mediaPlayer.setDataSource(path);
            // 异步准备装载文件
            mediaPlayer.prepareAsync();
            // 设置循环
            mediaPlayer.setLooping(isLooping);
            // 添加准备监听
            mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 装载准备完毕就播放
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 添加错误监听
        mediaPlayer.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int i, int i1) {
                // 错误状态进行重置
                mediaPlayer.reset();
                return true;
            }
        });
    }


    /**
     * 播放声音（raw资源）
     * @param context 上下文
     * @param rawId 播放raw资源id
     * @param isLooping 循环播放
     */
    public void playRaw(Context context, int rawId, boolean isLooping) {
        try {
            // 重置
            mediaPlayer.reset();
            // 设置播放raw资源id
            mediaPlayer = MediaPlayer.create(context, rawId);
            // 设置循环
            mediaPlayer.setLooping(isLooping);
            // 添加准备监听
            mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 装载准备完毕就播放
                    // 装载完毕回调
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 添加错误监听
        mediaPlayer.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int i, int i1) {
                // 错误状态进行重置
                mediaPlayer.reset();
                return true;
            }
        });
    }

    /**
     * 暂停播放
     */
    public void pause() {
        try {
            if (mediaPlayer != null && isPlaying()) {
                mediaPlayer.pause();
            }
        } catch (IllegalStateException e) {
            ALog.e(e);
        }
    }

    /**
     * 停止播放
     */
    public void stop() {
        try {
            if (mediaPlayer != null && isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.seekTo(0);
            }
        } catch (IllegalStateException e) {
            ALog.e(e);
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
        } catch (IllegalStateException e) {
            ALog.e(e);
        }
    }

    /**
     * @return 是否在播放状态
     */
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }
}
