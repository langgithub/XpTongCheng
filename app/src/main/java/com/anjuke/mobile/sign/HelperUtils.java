package com.anjuke.mobile.sign;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.CallLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class HelperUtils {
    public static String MSGRECEIVED_ACTION = "com.tools.payhelper.msgreceived";

    public static void sendmsg(Context context, String msg) {
        Intent broadCastIntent = new Intent();
        broadCastIntent.putExtra("msg", msg);
        broadCastIntent.setAction(MSGRECEIVED_ACTION);
        context.sendBroadcast(broadCastIntent);
    }

    public static String getParam(String urlRight,String uuid) {
        Map map=new HashMap();
        String[] split = urlRight.split("&");
        for (String items : split){
            String[] split1 = items.split("=");
            if(split1.length==1){
                map.put(split1[0],"");
            }else {
                map.put(split1[0],split1[1]);
            }
        }
//      uuid="b3adec5c-0392-4b2f-97bf-5997bf479cc2";
        String str8 = SignUtil.m14978a("/mobile/v5/sale/property/list", (byte[]) null, map, uuid);
        System.out.println(str8);
        return str8;
    }
    @SuppressLint("MissingPermission")
    public static ArrayList<CallInfo> getCallHistoryList(Context context, ContentResolver cr){

        Cursor cs;
        cs=cr.query(CallLog.Calls.CONTENT_URI, //系统方式获取通讯录存储地址
                new String[]{
                        CallLog.Calls.CACHED_NAME,  //姓名
                        CallLog.Calls.NUMBER,    //号码
                        CallLog.Calls.TYPE,  //呼入/呼出(2)/未接
                        CallLog.Calls.DATE,  //拨打时间
                        CallLog.Calls.DURATION   //通话时长
                },null,null,CallLog.Calls.DEFAULT_SORT_ORDER);

        ArrayList<CallInfo> list=new ArrayList<>();
        int i=0;
        if(cs!=null &&cs.getCount()>0){
            for(cs.moveToFirst();!cs.isAfterLast() & i<50; cs.moveToNext()){
                String callName=cs.getString(0);
                String callNumber=cs.getString(1);
                //通话类型
                int callType=Integer.parseInt(cs.getString(2));
                String callTypeStr="";
                switch (callType) {
                    case CallLog.Calls.INCOMING_TYPE:
                        callTypeStr="呼入";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        callTypeStr="呼出";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callTypeStr="未接";
                        break;
                }
                //拨打时间
                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date callDate=new Date(Long.parseLong(cs.getString(3)));
                String callDateStr=sdf.format(callDate);
                //通话时长
                int callDuration=Integer.parseInt(cs.getString(4));
                int min=callDuration/60;
                int sec=callDuration%60;
                String callDurationStr=min+"分"+sec+"秒";
                CallInfo callInfo =new CallInfo();
                callInfo.setCallDateStr(callDateStr);
                callInfo.setCallDurationStr(callDurationStr);
                callInfo.setCallName(callName);
                callInfo.setCallNumber(callNumber);
                callInfo.setCallTypeStr(callTypeStr);
                list.add(callInfo);

                i++;
            }
        }

        return list;
    }
}
