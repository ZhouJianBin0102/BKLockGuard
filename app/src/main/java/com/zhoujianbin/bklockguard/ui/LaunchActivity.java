package com.zhoujianbin.bklockguard.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.ALog;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.zhoujianbin.bklockguard.R;
import com.zhoujianbin.bklockguard.base.BaseAppCompatActivity;
import com.zhoujianbin.bklockguard.config.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.wefor.circularanim.CircularAnim;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 02:29:40
 * Class Description: 启动页
 * Comment: 获取权限、显示广告和软件logo
 ******************************************************************************/

public class LaunchActivity extends BaseAppCompatActivity implements SplashADListener {

    private static final String SKIP_TEXT = "点击跳过 %d";
    public boolean canJump = false;
    @BindView(R.id.splash_holder)
    AppCompatImageView splashHolder;
    @BindView(R.id.splash_container)
    FrameLayout splashContainer;
    @BindView(R.id.skip_view)
    TextView skipView;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.tv_app_name)
    AppCompatTextView tvAppName;
    @BindView(R.id.tv_app_slogan)
    AppCompatTextView tvAppSlogan;
    @BindView(R.id.tv_app_copyright)
    AppCompatTextView tvAppCopyright;
    @BindView(R.id.iv_app_logo)
    AppCompatImageView ivAppLogo;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);
        // 如果targetSDKVersion >= 23，就要申请好权限。
        // 如果您的App没有适配到Android6.0（即targetSDKVersion < 23），那么只需要在这里直接调用fetchSplashAD接口。
        if (Build.VERSION.SDK_INT >= 23) {
            // 检查获取权限
            checkAndRequestPermission();
        } else {
            // 如果是Android6.0以下的机器，默认在安装时获得了所有权限，可以直接调用SDK
            fetchSplashAD(this, splashContainer, skipView, Constants.GDT_APPID, Constants.GDT_SplashPosID, this, 0);
        }
    }

    /**
     * ----------非常重要----------
     * <p>
     * Android6.0以上的权限适配简单示例：
     * <p>
     * 如果targetSDKVersion >= 23，那么必须要申请到所需要的权限，再调用广点通SDK，否则广点通SDK不会工作。
     * <p>
     * 注意：下面的`checkSelfPermission`和`requestPermissions`方法都是在Android6.0的SDK中增加的API，
     * 如果App还没有适配到Android6.0以上，则不需要调用这些方法，直接调用广点通SDK即可。
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        // 新建需要的权限List容器
        List<String> requestPermissionList = new ArrayList<>();
        // 添加需要的权限
        requestPermissionList.add(Manifest.permission.READ_PHONE_STATE);
        requestPermissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requestPermissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        requestPermissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        // 新建未授权的权限List容器
        List<String> lackedPermissionList = new ArrayList<>();
        // 筛选未授权的权限放到未授权List容器中
        for(String permission:requestPermissionList){
            if (!(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)) {
                lackedPermissionList.add(permission);
            }
        }
        // 权限都已经有了，那么直接调用SDK
        if (lackedPermissionList.size() == 0) {
            // 直接调用广点通SDK
            fetchSplashAD(this, splashContainer, skipView, Constants.GDT_APPID, Constants.GDT_SplashPosID, this, 0);
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermissionList.size()];
            // 转换为数组
            lackedPermissionList.toArray(requestPermissions);
            // 请求权限
            requestPermissions(requestPermissions, 1024);
        }
    }

    /**
     * 判断所有权限授权
     * @param grantResults 授权回调结果数组
     * @return 是否所有权限已经授权
     */
    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 处理请求权限回调
     * @param requestCode 请求码
     * @param permissions 请求权限数组
     * @param grantResults 授权回调结果数组
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 判断请求码和是否所有需要权限已授权
        if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {
            // 所有权限已授权，直接调用广点通SDK
            fetchSplashAD(this, splashContainer, skipView, Constants.GDT_APPID, Constants.GDT_SplashPosID, this, 0);
        } else {
            // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
            Toast.makeText(this, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            finish();
        }
    }

    /**
     * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
     *
     * @param activity      展示广告的activity
     * @param adContainer   展示广告的大容器
     * @param skipContainer 自定义的跳过按钮：传入该view给SDK后，SDK会自动给它绑定点击跳过事件。SkipView的样式可以由开发者自由定制，其尺寸限制请参考activity_splash.xml或者接入文档中的说明。
     * @param appId         应用ID
     * @param posId         广告位ID
     * @param adListener    广告状态监听器
     * @param fetchDelay    拉取广告的超时时长：取值范围[3000, 5000]，设为0表示使用广点通SDK默认的超时时长。
     */
    private void fetchSplashAD(Activity activity, ViewGroup adContainer, View skipContainer,
                               String appId, String posId, SplashADListener adListener, int fetchDelay) {
        new SplashAD(activity, adContainer, skipContainer, appId, posId, adListener, fetchDelay);
    }

    /**
     * 广告显示回调
     */
    @Override
    public void onADPresent() {
        ALog.i("SplashADPresent");
        // 广告展示后一定要把预设的开屏图片隐藏起来
        splashHolder.setVisibility(View.INVISIBLE);
    }

    /**
     * 广告点击回调
     */
    @Override
    public void onADClicked() {
        ALog.i("SplashADClicked");
    }

    /**
     * 倒计时回调，返回广告还将被展示的剩余时间。
     * 通过这个接口，开发者可以自行决定是否显示倒计时提示，或者还剩几秒的时候显示倒计时
     *
     * @param millisUntilFinished 剩余毫秒数
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void onADTick(long millisUntilFinished) {
        skipView.setText(String.format(SKIP_TEXT, Math.round(millisUntilFinished / 1000f)));
    }

    /**
     * 广告消失回调
     */
    @Override
    public void onADDismissed() {
        ALog.i("SplashADDismissed");
        next();
    }
    /**
     * 广告加载失败回调
     */
    @Override
    public void onNoAD(int errorCode) {
        ALog.i("LoadSplashADFail, eCode=" + errorCode);
        /* 如果加载广告失败，则直接跳转 */
        startNextActivity();
    }

    /**
     * 设置一个变量来控制当前开屏页面是否可以跳转，当开屏广告为普链类广告时，点击会打开一个广告落地页，此时开发者还不能打开自己的App主页。
     * 当从广告落地页返回以后，才可以跳转到开发者自己的App主页；当开屏广告是App类广告时只会下载App。
     */
    private void next() {
        if (canJump) {
            startNextActivity();
        } else {
            canJump = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        canJump = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canJump) {
            next();
        }
        canJump = true;
    }

    /**
     * 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME || super.onKeyDown(keyCode, event);
    }

    private void startNextActivity() {
        CircularAnim.fullActivity(LaunchActivity.this, llBottom)
//                        .colorOrImageRes(R.color.colorPrimary)  //注释掉，因为该颜色已经在App.class 里配置为默认色
                .go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        // 跳转到主页MainActivity
                        startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}
