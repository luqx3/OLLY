package com.example.cyhtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

/**
 * Created by HP on 2017/1/13.
 */
public class StaticReceuver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Bundle bundle=intent.getExtras();
        //由Icon的id得到Bitmap
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.head_portrait);
        //通过getSystemService（）方法得到NotificationManager对象，它负责管理发送通知、清除通知等一系列的对通知的管理工作。
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder=new Notification.Builder(context);
        builder.setContentTitle("线下图书馆")//设置通知栏标题
                .setContentText("借书成功")//设置通知栏显示内容
                .setTicker("借书成功.")//通知首次出现在通知栏，带上升动画效果
                .setLargeIcon(bitmap)
                .setSmallIcon(R.drawable.head_portrait)
                .setAutoCancel(true);//用户点击面板就让通知自动取消
        Intent intent2 = new Intent(context, MainActivity.class);//点击时跳转的intent，跳转到MainActivity
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent2, 0);//PendingIntent授予启动Mainactivity的权利。
        builder.setContentIntent(contentIntent);
        Notification notify=builder.build();//绑定通知，
        notificationManager.notify(0,notify);//显示通知
    }
}
