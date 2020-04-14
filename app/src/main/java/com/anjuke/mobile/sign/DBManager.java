package com.anjuke.mobile.sign;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName: DBManager
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author SuXiaoliang
 * @date 2018年6月23日 下午1:27:22
 *
 */
public class DBManager {

	private SQLiteDatabase db;
	private DBHelper helper;

	public DBManager(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

	public void addHouseList(Context context,List<House> houses) {
		for (House house: houses){
			if (!"1".equals(getErshoufang(house.getTw_url()))){
				HelperUtils.sendmsg(context,"最新新二手房用户 电话："+house.getBroker_mobile());
				LogUtils.d("插入数据house"+house.getTw_url());
				addHouse(house);
//				//执行回调
//				Intent broadCastIntent = new Intent();
//				broadCastIntent.putExtra("phone", house.getBroker_mobile());
//				broadCastIntent.setAction("com.lang.tongcheng.call");
//				context.sendBroadcast(broadCastIntent);
			}
		}
	}

	 public void addHouse(House house) {
        db.beginTransaction();// 开始事务
        try {
            db.execSQL("INSERT INTO ershoufang VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					new Object[] { house.getTw_url(), house.getId(), house.getTitle(),house.getHouse_price(),
							house.getHouse_avg_price(), house.getHouse_area_num(), house.getHouse_layout(),
							house.getHouse_floor_level(),house.getHouse_orient(),house.getHouse_use_type(),
							house.getCommunity_name(),house.getCommunity_address(),house.getBroker_name(),
							house.getBroker_mobile(), house.getPost_date(), house.getIs_call()});
            db.setTransactionSuccessful();// 事务成功
        } catch (Exception e){
        	if (!e.getMessage().contains("UNIQUE constraint failed: ershoufang.tw_url")){
				e.printStackTrace();
				//如果这里出现重复，直接跳过
				//否者重新保存
				LogUtils.e(house.toString());
			}else {
				LogUtils.d("重复的house:"+house.getTw_url());
			}
		}finally {
            db.endTransaction();// 结束事务
        }
    }


	public void updateHouseByCall(String phone) {
		 db.beginTransaction();// 开始事务
		 try {
			 db.execSQL("UPDATE ershoufang SET is_call=? WHERE broker_mobile=? and is_call=?", new Object[] {"yes",phone,"no"});
			 db.setTransactionSuccessful();// 事务成功
		 } finally {
			 db.endTransaction();// 结束事务
		 }
	 }

	public String getErshoufang(String tw_url) {
		String sql = "SELECT count(*) as 'count' FROM ershoufang WHERE tw_url='"+tw_url+"'";
		Cursor c = ExecSQLForCursor(sql);
		String value="";
		if(c.moveToNext()){
			value=c.getString(c.getColumnIndex("count"));
		}
		c.close();
		return value;
	}

	public boolean isNotCall(String phone) {
		boolean isExist=false;
		String sql = "SELECT count(*) as 'count' FROM ershoufang WHERE broker_mobile='"+phone+"' and is_call='no'";
		Cursor c = ExecSQLForCursor(sql);
		if(c.getCount()>0){
			isExist=true;
		}
		c.close();
		return isExist;
	}
//
//	public boolean isNotifyTradeNo(String tradeNo) {
//		boolean isExist=false;
//		String sql = "SELECT * FROM tradeno WHERE tradeno='"+tradeNo+"'";
//		Cursor c = ExecSQLForCursor(sql);
//		if(c.moveToNext()){
//			String status=c.getString(c.getColumnIndex("status"));
//			if(status.equals("1"))
//			isExist=true;
//		}
//		c.close();
//		return isExist;
//	}
//
//	public void updateTradeNo(String tradeNo, String status) {
//		 db.beginTransaction();// 开始事务
//		 try {
//			 db.execSQL("UPDATE tradeno SET status=? WHERE tradeno=?", new Object[] {status, tradeNo});
//			 db.setTransactionSuccessful();// 事务成功
//		 } finally {
//			 db.endTransaction();// 结束事务
//		 }
//	 }
//
//	public void updateOrder(String no, String result) {
//		 db.beginTransaction();// 开始事务
//		 try {
//			 db.execSQL("UPDATE payorder SET result=?,time=time+1 WHERE tradeno=?", new Object[] {result, no});
//			 db.setTransactionSuccessful();// 事务成功
//		 } finally {
//			 db.endTransaction();// 结束事务
//		 }
//	 }
//
//	 public ArrayList<QrCodeBean> FindQrCodes(String money, String mark, String type) {
//		DecimalFormat df = new DecimalFormat("0.00");
//		money=df.format(Double.parseDouble(money));
//    	String sql = "SELECT * FROM qrcode WHERE money =" + "'" + money + "' and mark='"+mark+"' and type='"+type+"'";
//        ArrayList<QrCodeBean> list = new ArrayList<QrCodeBean>();
//        Cursor c = ExecSQLForCursor(sql);
//        while (c.moveToNext()) {
//            QrCodeBean info = new QrCodeBean();
//            info.setMoney(c.getString(c.getColumnIndex("money")));
//            info.setMark(c.getString(c.getColumnIndex("mark")));
//            info.setType(c.getString(c.getColumnIndex("type")));
//            info.setPayurl(c.getString(c.getColumnIndex("payurl")));
//            info.setDt(c.getString(c.getColumnIndex("dt")));
//            list.add(info);
//        }
//        c.close();
//        return list;
//    }
//	 public ArrayList<QrCodeBean> FindQrCodes(String mark, String type) {
//		 String sql = "SELECT * FROM qrcode WHERE mark='"+mark+"'";
//		 ArrayList<QrCodeBean> list = new ArrayList<QrCodeBean>();
//		 Cursor c = ExecSQLForCursor(sql);
//		 while (c.moveToNext()) {
//			 QrCodeBean info = new QrCodeBean();
//			 info.setMoney(c.getString(c.getColumnIndex("money")));
//			 info.setMark(c.getString(c.getColumnIndex("mark")));
//			 info.setType(c.getString(c.getColumnIndex("type")));
//			 info.setPayurl(c.getString(c.getColumnIndex("payurl")));
//			 info.setDt(c.getString(c.getColumnIndex("dt")));
//			 list.add(info);
//		 }
//		 c.close();
//		 return list;
//	 }
//	public ArrayList<OrderBean> FindOrders(String money, String mark, String type) {
//    	String sql = "SELECT * FROM payorder WHERE money =" + "'" + money + "' and mark='"+mark+"' and type='"+type+"'";
//    	ArrayList<OrderBean> list = new ArrayList<OrderBean>();
//    	Cursor c = ExecSQLForCursor(sql);
//    	while (c.moveToNext()) {
//    		OrderBean info = new OrderBean();
//    		info.setMoney(c.getString(c.getColumnIndex("money")));
//    		info.setMark(c.getString(c.getColumnIndex("mark")));
//    		info.setType(c.getString(c.getColumnIndex("type")));
//    		info.setNo(c.getString(c.getColumnIndex("tradeno")));
//    		info.setDt(c.getString(c.getColumnIndex("dt")));
//    		info.setResult(c.getString(c.getColumnIndex("result")));
//    		info.setTime(c.getInt(c.getColumnIndex("time")));
//    		list.add(info);
//    	}
//    	c.close();
//    	return list;
//    }
//	public ArrayList<OrderBean> FindOrders(String mark) {
//		String sql = "SELECT * FROM payorder WHERE mark='"+mark+"'";
//		ArrayList<OrderBean> list = new ArrayList<OrderBean>();
//		Cursor c = ExecSQLForCursor(sql);
//		while (c.moveToNext()) {
//			OrderBean info = new OrderBean();
//			info.setMoney(c.getString(c.getColumnIndex("money")));
//			info.setMark(c.getString(c.getColumnIndex("mark")));
//			info.setType(c.getString(c.getColumnIndex("type")));
//			info.setNo(c.getString(c.getColumnIndex("tradeno")));
//			info.setDt(c.getString(c.getColumnIndex("dt")));
//			info.setResult(c.getString(c.getColumnIndex("result")));
//    		info.setTime(c.getInt(c.getColumnIndex("time")));
//			list.add(info);
//		}
//		c.close();
//		return list;
//	}
//	public ArrayList<OrderBean> FindOrdersByNo(String no) {
//		String sql = "SELECT * FROM payorder WHERE tradeno='"+no+"'";
//		ArrayList<OrderBean> list = new ArrayList<OrderBean>();
//		Cursor c = ExecSQLForCursor(sql);
//		while (c.moveToNext()) {
//			OrderBean info = new OrderBean();
//			info.setMoney(c.getString(c.getColumnIndex("money")));
//			info.setMark(c.getString(c.getColumnIndex("mark")));
//			info.setType(c.getString(c.getColumnIndex("type")));
//			info.setNo(c.getString(c.getColumnIndex("tradeno")));
//			info.setDt(c.getString(c.getColumnIndex("dt")));
//			info.setResult(c.getString(c.getColumnIndex("result")));
//			info.setTime(c.getInt(c.getColumnIndex("time")));
//			list.add(info);
//		}
//		c.close();
//		return list;
//	}

	public ArrayList<House> FindTopHouse() {
		String sql = "SELECT * FROM ershoufang where is_call='no' order by post_date desc limit 1 ";
		ArrayList<House> list = new ArrayList<House>();
		Cursor c = ExecSQLForCursor(sql);
		while (c.moveToNext()) {
			House house = new House();
			house.setId(c.getString(c.getColumnIndex("id")));
			house.setTw_url(c.getString(c.getColumnIndex("tw_url")));
			house.setTitle(c.getString(c.getColumnIndex("title")));
			house.setHouse_price(c.getString(c.getColumnIndex("house_price")));
			house.setHouse_avg_price(c.getString(c.getColumnIndex("house_avg_price")));
			house.setHouse_area_num(c.getString(c.getColumnIndex("house_area_num")));
			house.setHouse_layout(c.getString(c.getColumnIndex("house_layout")));
			house.setHouse_floor_level(c.getString(c.getColumnIndex("house_floor_level")));
			house.setHouse_orient(c.getString(c.getColumnIndex("house_orient")));
			house.setHouse_use_type(c.getString(c.getColumnIndex("house_use_type")));
			house.setCommunity_name(c.getString(c.getColumnIndex("community_name")));
			house.setCommunity_address(c.getString(c.getColumnIndex("community_address")));
			house.setBroker_name(c.getString(c.getColumnIndex("broker_name")));
			house.setBroker_mobile(c.getString(c.getColumnIndex("broker_mobile")));
			house.setPost_date(c.getInt(c.getColumnIndex("post_date")));
			house.setIs_call(c.getString(c.getColumnIndex("is_call")));
			list.add(house);
		}
		c.close();
		return list;
	}

	public ArrayList<House> FindHouseLimit() {
		String sql = "SELECT * FROM ershoufang where is_call='no' order by post_date desc limit 100 ";
		ArrayList<House> list = new ArrayList<House>();
		Cursor c = ExecSQLForCursor(sql);
		while (c.moveToNext()) {
			House house = new House();
			house.setId(c.getString(c.getColumnIndex("id")));
			house.setTw_url(c.getString(c.getColumnIndex("tw_url")));
			house.setTitle(c.getString(c.getColumnIndex("title")));
			house.setHouse_price(c.getString(c.getColumnIndex("house_price")));
			house.setHouse_avg_price(c.getString(c.getColumnIndex("house_avg_price")));
			house.setHouse_area_num(c.getString(c.getColumnIndex("house_area_num")));
			house.setHouse_layout(c.getString(c.getColumnIndex("house_layout")));
			house.setHouse_floor_level(c.getString(c.getColumnIndex("house_floor_level")));
			house.setHouse_orient(c.getString(c.getColumnIndex("house_orient")));
			house.setHouse_use_type(c.getString(c.getColumnIndex("house_use_type")));
			house.setCommunity_name(c.getString(c.getColumnIndex("community_name")));
			house.setCommunity_address(c.getString(c.getColumnIndex("community_address")));
			house.setBroker_name(c.getString(c.getColumnIndex("broker_name")));
			house.setBroker_mobile(c.getString(c.getColumnIndex("broker_mobile")));
			house.setPost_date(c.getInt(c.getColumnIndex("post_date")));
			house.setIs_call(c.getString(c.getColumnIndex("is_call")));
			list.add(house);
		}
		c.close();
		return list;
	}

    /**
     * 执行SQL，返回一个游标
     *
     * @param sql
     * @return
     */
    private Cursor ExecSQLForCursor(String sql) {
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
}
