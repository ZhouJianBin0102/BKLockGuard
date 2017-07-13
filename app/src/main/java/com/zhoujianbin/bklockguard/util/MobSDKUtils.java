package com.zhoujianbin.bklockguard.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.zhoujianbin.bklockguard.MyApp;
import com.zhoujianbin.bklockguard.R;

import java.io.File;
import java.io.FileOutputStream;

import cn.sharesdk.onekeyshare.OnekeyShare;

import static com.tencent.bugly.beta.tinker.TinkerManager.getApplication;
import static com.zhoujianbin.bklockguard.config.Constants.ICON_IMAGE_URL;
import static com.zhoujianbin.bklockguard.config.Constants.YYB_SHARE_URL;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 03:10:33
 * Class Description: MobSDK工具类
 * Comment: MobSDK工具类
 ******************************************************************************/

public class MobSDKUtils {
    public static void showShare(Context context) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(context.getString(R.string.app_name));
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(YYB_SHARE_URL);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("本应用致力于由锁屏功能为基础实现真正意义上的实时实地防盗保护您的手机，只通过解锁屏而无需其它麻烦操作，从而免除被偷窃和被无关人员使用的安全问题。");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(drawable2sdFilePath("icon",R.mipmap.ic_launcher));//确保SDcard下面存在此张图片
        //oks.setAddress(YYB_SHARE_URL);
        //oks.setInstallUrl(YYB_SHARE_URL);
        oks.setImageUrl(ICON_IMAGE_URL);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(YYB_SHARE_URL);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(YYB_SHARE_URL);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(YYB_SHARE_URL);
        oks.setSilent(false);
        // 启动分享GUI
        oks.show(context);
    }

    //把图片从drawable复制到sdcard中
    //copy the picture from the drawable to sdcard
    public static String drawable2sdFilePath(String fileName, int drawableResId) {
        String imgPath;
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && Environment.getExternalStorageDirectory().exists()) {
                imgPath = Environment.getExternalStorageDirectory().getAbsolutePath() + fileName;
            } else {
                imgPath = getApplication().getFilesDir().getAbsolutePath() + fileName;
            }
            File file = new File(imgPath);
            if (!file.exists()) {
                file.createNewFile();
                Bitmap pic = BitmapFactory.decodeResource(MyApp.getContext().getResources(), drawableResId);
                FileOutputStream fos = new FileOutputStream(file);
                pic.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
        return imgPath;
    }
}
