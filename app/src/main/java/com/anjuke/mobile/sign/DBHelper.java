package com.anjuke.mobile.sign;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * @ClassName: DBHelper
 * @Description: 创建数据库
 * @author yuanlang
 */
public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
        super(context, "house.db", null, 1);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS ershoufang" +
				"(tw_url varchar PRIMARY KEY, id varchar, title varchar, house_price varchar," +
				" house_avg_price varchar, house_area_num varchar, house_layout varchar," +
				" house_floor_level varchar, house_orient varchar, house_use_type varchar," +
				" community_name varchar, community_address varchar, broker_name varchar, " +
				" broker_mobile varchar, post_date INTEGER, is_call varchar)");
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}  
}
