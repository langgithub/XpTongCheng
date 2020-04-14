package com.anjuke.mobile.sign;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lang.xptongcheng.CustomApplcation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 *
 * @ClassName: AlarmReceiver
 * @Description: 定时回调，爬虫抓取数据放入到数据库
 * @author SuXiaoliang
 *
 */
public class AlarmReceiver extends BroadcastReceiver{

	private static SSLSocketFactory createSSLSocketFactory() {
		SSLSocketFactory ssfFactory = null;

		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

			ssfFactory = sc.getSocketFactory();
		} catch (Exception e) {
		}

		return ssfFactory;
	}

	@Override
	public void onReceive(final Context context, Intent intent) {
		try {
			// 记录一个开始时间
			HelperUtils.sendmsg(context, "轮询爬虫任务");
			//查询数据库(条件是is_call==no)，查看是否存在新二手房，是则打电话
			DBManager dbManager = new DBManager(CustomApplcation.getInstance().getApplicationContext());

			// 证书配置
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(5, TimeUnit.SECONDS);
			builder.sslSocketFactory(createSSLSocketFactory());
			builder.hostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String s, SSLSession sslSession) {
					return true;
				}
			});
			OkHttpClient client = builder.build();
			//采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
			String urlLeft="https://appsale.58.com/mobile/v5/sale/property/list?";
			String urlRight= "map_type=google&city_id=147&is_struct=1&with_broker_recommend=0&orderby=6&page_size=41&entry=11&source_id=2&is_ax_partition=0&page=1&androidid=65c157ca1d0a5c4c&uuid=42ef0fa8-9956-4d04-a2cd-2a06ef841182&_guid=c4a65472-7550-4e89-a75b-a67ea9705eea&cid=1&pm=447&version_code=90602&m=Android-Redmi Note 5 Pro&qtime=20200303170021&from=mobile&app=a-wb&oaid=&ajk_city_id=147&v=7.1.2&i=869782031767653&origin_imei=869782031767653&_pid=6292&o=whyred-userdebug 7.1.2 NJH47F 0650b406f6 test-keys&cv=9.6.2&macid=65c157ca1d0a5c4c&_chat_id=&manufacturer=Xiaomi&origin_mac=";
			String uuid = UUID.randomUUID().toString();
			Request request = new Request.Builder().get().url(urlLeft+urlRight)
					.addHeader("nsign", HelperUtils.getParam(urlRight,uuid))
					.addHeader("get_md5","d2fa958e5dbb6b0d2f9125b42994eb9201eb01eb")
					.addHeader("nsign_uuid",uuid).build();
			client.newCall(request).enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					e.printStackTrace();
                	HelperUtils.sendmsg(context,"爬虫抓取失败"+e.getMessage());
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					//记录一个结束时间
					try{
						HelperUtils.sendmsg(context, "爬虫抓取成功");
						List<House> saveList=new ArrayList<>();
						String responseStr = response.body().string();
//						System.out.println(responseStr);
						JSONObject jsonObject=new JSONObject(responseStr);
						String msg = jsonObject.getString("msg");
						if ("ok".equals(msg)){
							JSONArray list = jsonObject.getJSONObject("data").getJSONArray("list");
							for(int i=0 ;i<list.length();i++){
								JSONObject info = list.getJSONObject(i).getJSONObject("info");
								JSONObject property = info.getJSONObject("property").getJSONObject("base");

								//58自己的id
								String id = property.getString("id");
								//二手房在58的地址
								String tw_url = property.getString("tw_url");
								//标题
								String title = property.getString("title");
								//房屋属性
								String house_price = property.getJSONObject("attribute").getString("price");
								String house_avg_price = property.getJSONObject("attribute").getString("avg_price");
								String house_area_num = property.getJSONObject("attribute").getString("area_num");
								String house_layout = property.getJSONObject("attribute").getString("room_num")+"室"+property.getJSONObject("attribute").getString("hall_num")+"厅"+property.getJSONObject("attribute").getString("toilet_num")+"卫";
								String house_floor_level = property.getJSONObject("attribute").getString("floor")+"（共"+property.getJSONObject("attribute").getString("total_floor")+"层）";
								String house_orient = property.getJSONObject("attribute").getString("orient");
								String house_use_type= property.getJSONObject("attribute").getString("use_type");
								//小区属性
								JSONObject community = info.getJSONObject("community").getJSONObject("base");
								String community_name= community.getString("name");
								String community_address= community.getString("areaName")+"|"+community.getString("blockName")+"|"+community.getString("address");
								//发布者
								JSONObject broker = info.getJSONObject("broker").getJSONObject("base");
								String broker_name= broker.getString("name");
								String broker_mobile= broker.getString("mobile");
								//发布时间
								String post_date=property.getString("post_date");

								House house=new House();
								house.setId(id);
								house.setTw_url(tw_url);
								house.setTitle(title);
								house.setHouse_price(house_price);
								house.setHouse_avg_price(house_avg_price);
								house.setHouse_area_num(house_area_num);
								house.setHouse_layout(house_layout);
								house.setHouse_floor_level(house_floor_level);
								house.setHouse_orient(house_orient);
								house.setHouse_use_type(house_use_type);
								house.setCommunity_name(community_name);
								house.setCommunity_address(community_address);
								house.setBroker_name(broker_name);
								house.setBroker_mobile(broker_mobile);
								house.setPost_date(Integer.parseInt(post_date));
								house.setIs_call("no");
								saveList.add(house);
							}
						}
						// 新加一条测试

//						House house=new House();
//						house.setId("id");
//						house.setTw_url("tw_url");
//						house.setTitle("title");
//						house.setHouse_price("house_price");
//						house.setHouse_avg_price("house_avg_price");
//						house.setHouse_area_num("house_area_num");
//						house.setHouse_layout("house_layout");
//						house.setHouse_floor_level("house_layout");
//						house.setHouse_orient("house_layout");
//						house.setHouse_use_type("house_use_type");
//						house.setCommunity_name("community_name");
//						house.setCommunity_address("community_address");
//						house.setBroker_name("broker_name");
//						house.setBroker_mobile("15775691981");
//						house.setPost_date(Integer.parseInt("1581005732"));
//						house.setIs_call("no");
//						saveList.add(house);

						//处理保存
//						DBManager dbManager = new DBManager(CustomApplcation.getInstance().getApplicationContext());
						dbManager.addHouseList(context,saveList);

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			//查询数据库(条件是is_call==no)，查看是否存在新二手房，是则打电话
			ArrayList<House> houses = dbManager.FindTopHouse();
			for (House house : houses){
				Intent broadCastIntent = new Intent();
				broadCastIntent.putExtra("phone", house.getBroker_mobile());
				broadCastIntent.setAction("com.lang.tongcheng.call");
				context.sendBroadcast(broadCastIntent);
			}

			// 定期更新拨打的电话
			ArrayList<CallInfo> callHistoryListStr= HelperUtils.getCallHistoryList(null, context.getContentResolver());
			for(CallInfo callInfo : callHistoryListStr){
				if ("呼出".equals(callInfo.getCallTypeStr())&&(!"0分0秒".equals(callInfo.getCallDurationStr()))){
					//更新数据库
//					DBManager dbManager = new DBManager(CustomApplcation.getInstance().getApplicationContext());
					if(dbManager.isNotCall(callInfo.getCallNumber())){
						dbManager.updateHouseByCall(callInfo.getCallNumber());
						HelperUtils.sendmsg(context,"电话："+callInfo.getCallNumber() +"已拨打");
					}
				}
			}
		} catch (Exception e) {
			HelperUtils.sendmsg(context, "AlarmReceiver异常->>"+e.getMessage());
		}
	}

}
