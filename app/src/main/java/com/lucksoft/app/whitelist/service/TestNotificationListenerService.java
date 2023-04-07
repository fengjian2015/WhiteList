package com.lucksoft.app.whitelist.service;

/**
 * lucksoft
 * Created by AndroidDevelop on 2023/3/27.
 * 158045632@qq.com
 */
import android.app.Notification;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class TestNotificationListenerService extends NotificationListenerService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        //Notification notification = sbn.getNotification();
        Log.d("tage", "sbn.getPackageName: " + sbn.getPackageName());
    }
}