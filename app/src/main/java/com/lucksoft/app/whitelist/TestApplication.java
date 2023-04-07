package com.lucksoft.app.whitelist;

import android.app.Application;
import android.content.Intent;
import android.os.Build;

import com.lucksoft.app.whitelist.service.TestNotificationListenerService;
import com.lucksoft.app.whitelist.service.TestService;

import comluchsoft.whitelist.LogUtils;

/**
 * lucksoft
 * Created by AndroidDevelop on 2023/3/27.
 * 158045632@qq.com
 */
public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startService();
        LogUtils.setLogCallback(null);
    }

    /**
     * 启动服务
     */
    private void startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /****************第三点*******************/
            //安卓8.0以上开启为前台服务
            startForegroundService(new Intent(this, TestService.class));
        } else {
            startService(new Intent(this, TestService.class));
        }
        startService(new Intent(this, TestNotificationListenerService.class));
    }

}
