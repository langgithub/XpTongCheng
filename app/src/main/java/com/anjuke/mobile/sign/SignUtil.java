package com.anjuke.mobile.sign;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SignUtil {
    static {
        System.loadLibrary("signutil");
    }
//    public static void main(String[] args) {
//        String u="https://appsale.58.com/mobile/v5/sale/property/list?map_type=google&city_id=1&is_struct=1&with_broker_recommend=0&orderby=6&page_size=41&entry=11&source_id=2&is_ax_partition=0&page=1&androidid=65c157ca1d0a5c4c&uuid=848eda3e-ffbd-4903-a597-49096615f157&_guid=45b60ca1-1676-4462-8cd0-576d628c843b&cid=1&pm=447&version_code=90602&m=Android-Redmi%20Note%205%20Pro&qtime=20200218095004&from=mobile&app=a-wb&oaid=&ajk_city_id=1&v=7.1.2&i=869782031767653&origin_imei=869782031767653&_pid=8732&o=whyred-userdebug%207.1.2%20NJH47F%200650b406f6%20test-keys&cv=9.6.2&macid=65c157ca1d0a5c4c&_chat_id=&manufacturer=Xiaomi&origin_mac=";
//        String a="https://appsale.58.com/mobile/v5/sale/property/list?map_type=google&city_id=1&is_struct=1&with_broker_recommend=0&orderby=6&page_size=41&entry=11&source_id=2&is_ax_partition=0&page=2&androidid=65c157ca1d0a5c4c&uuid=848eda3e-ffbd-4903-a597-49096615f157&_guid=9bbd07cb-05e6-4688-a647-62f883dc72cf&cid=1&pm=447&version_code=90602&m=Android-Redmi%20Note%205%20Pro&qtime=20200218095057&from=mobile&app=a-wb&oaid=&ajk_city_id=1&v=7.1.2&i=869782031767653&origin_imei=869782031767653&_pid=8732&o=whyred-userdebug%207.1.2%20NJH47F%200650b406f6%20test-keys&cv=9.6.2&macid=65c157ca1d0a5c4c&_chat_id=&manufacturer=Xiaomi&origin_mac=";
//
//        String url_param="map_type=google&city_id=1&is_struct=1&with_broker_recommend=0&orderby=6&page_size=41&entry=11&source_id=2&is_ax_partition=0&page=2&androidid=65c157ca1d0a5c4c&uuid=848eda3e-ffbd-4903-a597-49096615f157&_guid=9bbd07cb-05e6-4688-a647-62f883dc72cf&cid=1&pm=447&version_code=90602&m=Android-Redmi Note 5 Pro&qtime=20200218095057&from=mobile&app=a-wb&oaid=&ajk_city_id=1&v=7.1.2&i=869782031767653&origin_imei=869782031767653&_pid=8732&o=whyred-userdebug 7.1.2 NJH47F 0650b406f6 test-keys&cv=9.6.2&macid=65c157ca1d0a5c4c&_chat_id=&manufacturer=Xiaomi&origin_mac=";
//        String[] split = url_param.split("&");
//        Map map=new HashMap();
//        for (String items : split){
//            String[] split1 = items.split("=");
//            if(split1.length==1){
//                map.put(split1[0],"");
//            }else {
//                map.put(split1[0],split1[1]);
//            }
//        }
//        System.out.println(map.toString());
//        System.out.println(map.size());
//        String uuid = UUID.randomUUID().toString();
//        System.out.println(uuid);
//
////                uuid="b3adec5c-0392-4b2f-97bf-5997bf479cc2";
//        String str8 = SignUtil.m14978a("/mobile/v5/sale/property/list", (byte[]) null, map, uuid);
//        System.out.println(str8);
//
//    }

    private static String hFo;
    private static final HashMap<String, String> hFp = new HashMap<>();
    private static final byte[] hFq = new byte[0];

    private static native String getSign0(String str, String str2, Map<String, byte[]> map, String str3, int i);

    private static native void setCacheFilePath(String str);

    /* renamed from: a */
    public static String m14978a(String str, byte[] bArr, Map<String, String> map, String str2) {
        if (bArr == null) {
            bArr = hFq;
        }
        if (map == null) {
            map = hFp;
        }
        for (String next : map.keySet()) {
            if (map.get(next) == null) {
                map.put(next, "");
            }
        }
        HashMap hashMap = new HashMap();
        for (String next2 : map.keySet()) {
            hashMap.put(next2, map.get(next2).getBytes(Charset.forName("UTF-8")));
        }
        String s = m14981u(bArr);
        System.out.println(s);
        return getSign0(str, s, hashMap, str2, bArr.length);
    }

    /* renamed from: c */
    public static String m14979c(Map<String, String> map, String str) {
        int i = 0;
        for (String next : map.keySet()) {
            i = i + next.length() + map.get(next).getBytes(Charset.forName("UTF-8")).length;
        }
        if (str == null) {
            str = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(m14980rJ(i));
        int i2 = 0;
        for (String split : str.split("&")) {
            String[] split2 = split.split("=", 2);
            if (split2.length == 2) {
                try {
                    i2 = i2 + URLDecoder.decode(split2[1], "UTF-8").getBytes().length + split2[0].length();
                } catch (Exception unused) {
                }
            } else {
                i2 += split2[0].length();
            }
        }
        sb.append(m14980rJ(i2));
        return sb.toString();
    }

    /* renamed from: rJ */
    private static String m14980rJ(int i) {
        byte[] bytes = "0123456789abcdef".getBytes();
        return new String(new byte[]{bytes[(61440 & i) >> 12], bytes[(i & 3840) >> 8], bytes[(i & 240) >> 4], bytes[i & 15]});
    }

    public static void setCacheDir(File file) {
        if (file != null) {
            File file2 = new File(file.getAbsolutePath() + "/signlog/");
            try {
                if (!file2.exists()) {
                    file2.mkdir();
                }
                hFo = file2.getAbsolutePath();
                setCacheFilePath(file2.getAbsolutePath() + "/sign.log");
            } catch (Exception unused) {
            }
        }
    }

    public static String getCacheDir() {
        return hFo;
    }

    /* renamed from: u */
    public static String m14981u(byte[] bArr) {
        try {
            char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            byte[] digest = MessageDigest.getInstance("MD5").digest(bArr);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                sb.append(cArr[(digest[i] & 240) >>> 4]);
                sb.append(cArr[digest[i] & 15]);
            }
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }
}