package com.lucksoft.app.whitelist;

import comluchsoft.whitelist.AutoStartAppUtil;
import comluchsoft.whitelist.LogUtils;
import comluchsoft.whitelist.WhiteListManage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mLeft;
    private TextView mBandName;
    private TextView mAutoStart;
    private TextView mTutorials;
    private TextView mLockApp;
    private TextView mBatteryOptimization;
    private WebView mWebView1, mWebView2;

    private boolean isWeb = false;
    private WhiteListManage whiteListManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        whiteListManage = new WhiteListManage(this);
        //testTry();

        //提示: 需要与服务共享数据自行绑定服务
        initView();
        /****************第四点*******************/
        //开启通知使用权限(有的手机需要开启才能在APP被kill掉后重启APP进程)
        if(!isNotificationListenerEnabled(this)) {
            openNotificationListenSettings(this);
        }
    }
//
//    private void initCheck(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//        }
//        if (!FcfrtAppBhUtils.isIgnoringBatteryOptimizations(this)){
//            FcfrtAppBhUtils.requestIgnoreBatteryOptimizations(this);
//        }
//    }
//
//    private void testTry(){
//        try {
//            //打开白名单
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (!FcfrtAppBhUtils.isIgnoringBatteryOptimizations(this)) {
//                    //不在白名单中
//                    //打开后台运行权限
//                    FcfrtAppBhUtils.requestIgnoreBatteryOptimizations(this);
//
//                    //判断是哪个厂家，打开对应手机管家设置，手动设置APP为白名单
//                    if (FcfrtAppBhUtils.isXiaomi()) {
//                        //小米手机
//                        FcfrtAppBhUtils.goXiaomiSetting(this);
//                    } else if (FcfrtAppBhUtils.isOPPO()) {
//                        //oppo手机
//                        FcfrtAppBhUtils.goOPPOSetting(this);
//                    } else if (FcfrtAppBhUtils.isMeizu()) {
//                        //魅族手机
//                        FcfrtAppBhUtils.goMeizuSetting(this);
//                    } else if (FcfrtAppBhUtils.isSamsung()) {
//                        //三星手机
//                        FcfrtAppBhUtils.goSamsungSetting(this);
//                    } else if (FcfrtAppBhUtils.isLeTV()) {
//                        //乐视手机
//                        FcfrtAppBhUtils.goLetvSetting(this);
//                    } else if (FcfrtAppBhUtils.isVIVO()) {
//                        //vivo手机
//                        FcfrtAppBhUtils.goVIVOSetting(this);
//                    } else if (FcfrtAppBhUtils.isHuawei()) {
//                        //华为手机
//                        FcfrtAppBhUtils.goHuaweiSetting(this);
//                    } else if (FcfrtAppBhUtils.isSmartisan()) {
//                        //锤子手机
//                        FcfrtAppBhUtils.goSmartisanSetting(this);
//                    }
//                }
//            }
//        } catch (Exception exception){
//
//        }
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200) {
            if (isNotificationListenerEnabled(this)) {//已经开启通知使用权
                //未开启APP通知，去开启
                if (!isNotificationEnabledForApp(this))
                    openNotificationSettingsForApp(this);
            }
        } else if (requestCode == 100) {
            if (isNotificationEnabledForApp(this)) {//已经开启APP通知
                //绑定了TestService再调用一下TestService中的initNotif()方法弹出通知。
            }
        }
    }

    /**
     * 是否开启APP通知
     *
     * @param context
     * @return
     */
    public static boolean isNotificationEnabledForApp(Context context) {
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }

    /**
     * 开启APP通知
     *
     * @param context
     */
    public static void openNotificationSettingsForApp(Activity context) {
        try {
            // 锤子不能单个打开自己APP的 通知设置 界面 (打开界面方式有时间可以研究一下)
            if (getDeviceBrand().equalsIgnoreCase("SMARTISAN")) {
                Toast.makeText(context, "锤子手机请手动打开 设置--通知中心--开启IMFitPro通知开关", Toast.LENGTH_LONG).show();
                return;
            }
            // Links to this app's notification settings.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//8.0
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
                intent.putExtra("app_package", context.getPackageName());
                intent.putExtra("app_uid", context.getApplicationInfo().uid);
                context.startActivityForResult(intent, 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否开启通知使用权限
     *
     * @param context
     * @return
     */
    public static boolean isNotificationListenerEnabled(Context context) {
        Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(context);
        if (packageNames.contains(context.getPackageName())) {
            return true;
        }
        return false;
    }

    /**
     * 开启通知使用权限
     */
    public static void openNotificationListenSettings(Activity context) {
        try {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            context.startActivityForResult(intent, 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mBandName = (TextView) findViewById(R.id.bandName);
        mLeft = (TextView) findViewById(R.id.left);
        mLeft.setOnClickListener(this);
        mAutoStart = (TextView) findViewById(R.id.autoStart);
        mAutoStart.setOnClickListener(this);
        mTutorials = (TextView) findViewById(R.id.tutorials);
        mTutorials.setOnClickListener(this);
        mLockApp = (TextView) findViewById(R.id.lockApp);
        mLockApp.setOnClickListener(this);
        mBatteryOptimization = (TextView) findViewById(R.id.battery_optimization);
        mBatteryOptimization.setOnClickListener(this);
        mWebView1 = (WebView) findViewById(R.id.webView1);
        mWebView1.setOnClickListener(this);
        mWebView2 = (WebView) findViewById(R.id.webView2);
        mWebView2.setOnClickListener(this);

        mBandName.setText(getDeviceBrand());
    }

    @Override
    public void onClick(View v) {
        LogUtils.d(" v  id: " + v.getId());
        if (v == mLeft) {
            if (isWeb) {
                isWeb = false;
                mWebView1.setVisibility(View.GONE);
                mWebView2.setVisibility(View.GONE);
                return;
            }
            finish();
        } else if (v == mTutorials) {
            isWeb = true;
            mWebView1.setVisibility(View.VISIBLE);
            mWebView1.loadUrl(whiteListManage.getTutorials());
            //http://121.199.69.42/guide/autostart/autostart_meizu.html
        } else if (v == mAutoStart) {
//            WhiteListUtils.INSTANCE.check(this);
            whiteListManage.openAuto();
        } else if (v == mLockApp) {
            isWeb = true;
            mWebView2.setVisibility(View.VISIBLE);
            LogUtils.d(" =============================》 ");
            ////http://121.199.69.42/guide/autostart/autostart_meizu.html
            if (getSystemLanguage().contains("zh")) {// 中文
                mWebView2.loadUrl("http://121.199.69.42/guide/locktask/lockApp.html");
            } else {
                mWebView2.loadUrl("http://121.199.69.42/guide/locktask/lockApp_e.html");
            }
        } else if (v == mBatteryOptimization) {
            whiteListManage.batteryOptimization();
        }
    }

    /**
     * 确定应用程序是否已经忽略电池优化
     *
     * @return
     */
    private boolean isIgnoringBatteryOptimizations() {
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName());
        }
        return isIgnoring;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intents = null;
                    for (Intent intent : AutoStartAppUtil.POWERMANAGER_INTENTS)
                        if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                            intents = intent;
                            break;
                        }
                    if (intents != null) {
                        startActivity(intents);
                    }
                } else {
                    Toast.makeText(this, "无权打开系统界面, 请点击查看教程手动设置", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    /**
     * 原生Android系统 开启后台运行调用这里
     */
    public void requestIgnoreBatteryOptimizations() {
        try {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);//按返回键,不 finish activity
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

}
