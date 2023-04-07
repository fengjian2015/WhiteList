package com.lucksoft.app.whitelist.service;

/**
 * lucksoft
 * Created by AndroidDevelop on 2023/3/27.
 * 158045632@qq.com
 */
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.lucksoft.app.whitelist.MainActivity;
import com.lucksoft.app.whitelist.R;

import static android.app.Notification.VISIBILITY_PUBLIC;


public class TestService extends Service {

    private final String CHANNEL_ONE_ID = "100";
    private int PRIORITY_MAX = 100;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /****************第一点*******************/
        //创建通知栏常驻通知
        initNotif("这是标题", "这是文本内容");
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        /****************第二点*******************/
        //返回START_STICKY,被系统或手动清理后可重启
        return START_STICKY;
    }

    public void initNotif(String title, String context) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.RemoteView);
        Intent nfIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfIntent, PendingIntent.FLAG_IMMUTABLE);
        @SuppressLint("WrongConstant") NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ONE_ID)
                .setContentIntent(pendingIntent) // 设置PendingIntent
                .setSmallIcon(R.mipmap.ic_battery_dialog) // 设置状态栏内的小图标
                //.setLargeIcon(bitmapIcon)// 设置大图标
                .setContentTitle(title)
                .setContentText(context) // 设置内容
                //.setWhen(System.currentTimeMillis())// 设置该通知发生的时间
                .setVisibility(VISIBILITY_PUBLIC)// 锁屏显示全部通知
                //.setDefaults(Notification.DEFAULT_ALL)// //使用默认的声音、振动、闪光
                .setCategory(Notification.CATEGORY_SERVICE)//设置类别
                .setPriority(PRIORITY_MAX);// 优先级为：重要通知

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //安卓8.0以上系统要求通知设置Channel,否则会报错
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, "服务常驻通知", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setLockscreenVisibility(VISIBILITY_PUBLIC);//锁屏显示全部通知
            manager.createNotificationChannel(notificationChannel);
            builder.setChannelId(CHANNEL_ONE_ID);
        }
        Notification notification = builder.build(); // 获取构建好的Notification
        //notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        notification.flags = Notification.FLAG_NO_CLEAR;//不消失的常驻通知
        startForeground(1, notification);//设置常驻通知
    }

}
