package com.zhoujianbin.bklockguard.bean;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 02:36:16
 * Class Description: 音乐文件bean
 * Comment: 音乐文件bean
 ******************************************************************************/

public class SoundFile extends SelectedBean{
    // 音乐名称
    private String title;
    // 音乐路径
    private String data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
