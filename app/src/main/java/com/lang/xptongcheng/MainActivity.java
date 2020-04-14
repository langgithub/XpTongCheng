package com.lang.xptongcheng;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import okhttp3.internal.Util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.anjuke.mobile.sign.AlarmReceiver;
import com.anjuke.mobile.sign.CallInfo;
import com.anjuke.mobile.sign.DBManager;
import com.anjuke.mobile.sign.DaemonService;
import com.anjuke.mobile.sign.HelperUtils;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.Manifest.permission.CALL_PHONE;
import static com.lang.xptongcheng.CustomApplcation.getContext;

public class MainActivity extends AppCompatActivity {

    private static String NOTIFY_ACTION = "com.lang.deamonservice";
    private static String MSGRECEIVED_ACTION = "com.tools.payhelper.msgreceived";
    private static String CALLPHONE_ACTION="com.lang.tongcheng.call";
    private static TextView console;
    private static ScrollView scrollView;
    private BillReceived billReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        console = (TextView) findViewById(R.id.console);
        scrollView = (ScrollView) findViewById(R.id.scrollview);
        findViewById(R.id.ershoufang).setOnClickListener(arg0 -> {
            Intent intent = new Intent(MainActivity.this, ShowHouses.class);
            startActivity(intent);
        });
        findViewById(R.id.test).setOnClickListener(arg0 -> {
            Intent intent = new Intent(MainActivity.this, CallPhoneActivity.class);
            startActivity(intent);
        });

        //注册广播
        billReceived = new BillReceived();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MSGRECEIVED_ACTION);
        intentFilter.addAction(CALLPHONE_ACTION);
        registerReceiver(billReceived, intentFilter);

        // 启动定时任务，并添加定时任务回调
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        IntentFilter alarmIntentFilter = new IntentFilter();
        alarmIntentFilter.addAction(NOTIFY_ACTION);
        registerReceiver(alarmReceiver, alarmIntentFilter);
        startService(new Intent(this, DaemonService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{CALL_PHONE}, 1);
        }
    }


    /**
     * 日志处理
     */
    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String txt = msg.getData().getString("log");
            if (console != null) {
                if (console.getText() != null) {
                    if (console.getText().toString().length() > 7500) {
                        console.setText("日志定时清理完成..." + "\n\n" + txt);
                    } else {
                        console.setText(console.getText().toString() + "\n\n" + txt);
                    }
                } else {
                    console.setText(txt);
                }
                scrollView.post(new Runnable() {
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 日志打印
     * @param txt
     */
    public static void sendmsg(String txt) {
//        LogToFile.i("payhelper", txt);
        Message msg = new Message();
        msg.what = 1;
        Bundle data = new Bundle();
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = dateFormat.format(date);
        data.putString("log", d + ":" + " " + txt);
        msg.setData(data);
        try {
            handler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //自定义接受订单通知广播
    class BillReceived extends BroadcastReceiver {
        @SuppressLint("MissingPermission")
        @Override
        public void onReceive(final Context context, Intent intent) {
            try {
                if (intent.getAction().contentEquals(MSGRECEIVED_ACTION)) {
                    String msg = intent.getStringExtra("msg");
                    sendmsg(msg);
                }else if(intent.getAction().contentEquals(CALLPHONE_ACTION)){
                    Intent call = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + intent.getStringExtra("phone"));
                    call.setData(data);
                    startActivity(call);
                }
            } catch (Exception e) {
                HelperUtils.sendmsg(context, "BillReceived异常" + e.toString());
            }
        }
    }
}
