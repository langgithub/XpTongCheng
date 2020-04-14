package com.anjuke.mobile.sign;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import com.lang.xptongcheng.R;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

/**
 *
 * @ClassName: DaemonService
 * @Description: 后台定时监控58同城二手房 个人 最新房源
 * @author yuanlang
 *
 */
public class DaemonService extends Service {

    public static String NOTIFY_ACTION = "com.lang.deamonservice";
    private static final String TAG = "DaemonService";  
    public static final int NOTICE_ID = 100;  
   

    @Override  
    public void onCreate() {
        //如果API大于18，需要弹出一个可见通知
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
//            Notification.Builder builder = new Notification.Builder(this);
//            builder.setSmallIcon(R.drawable.ic_launcher_background);
//            builder.setContentTitle("58同城检测");
//            builder.setContentText("58同城检测正在运行中...");
//            builder.setAutoCancel(false);
//            builder.setOngoing(true);
//            int id = 100;
//            NotificationManagerCompat.from(this).notify(id, builder.build());
//            startForeground(id, builder.build());
////            startForeground(NOTICE_ID,builder.build());
//        }else{
//            startForeground(NOTICE_ID,new Notification());
//        }
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time= AbSharedUtil.getInt(getApplicationContext(), "time");
        int triggerTime = 1 * 3 * 1000;
//        if(time!=0){
//        	triggerTime = time * 1000;
//        }
        Intent i = new Intent(NOTIFY_ACTION);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.setRepeating(AlarmManager.RTC_WAKEUP , System.currentTimeMillis(), triggerTime, pi);
        HelperUtils.sendmsg(getApplicationContext(), "启动定时任务");
    }

    @Override  
    public int onStartCommand(Intent intent, int flags, int startId) {  
        // 如果Service被终止  
        // 当资源允许情况下，重启service  
        return START_STICKY;  
    }
  
    @Override  
    public void onDestroy() {  
        super.onDestroy();  
        // 如果Service被杀死，干掉通知  
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){  
            NotificationManager mManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);  
            mManager.cancel(NOTICE_ID);  
        }
        // 重启自己  
        Intent intent = new Intent(getApplicationContext(),DaemonService.class);  
        startService(intent);  
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}  