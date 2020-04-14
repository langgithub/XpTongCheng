package com.lang.xptongcheng;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.anjuke.mobile.sign.DBManager;
import com.anjuke.mobile.sign.House;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ShowHouses extends AppCompatActivity {

    private ListView listView;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_houses);
        listView = findViewById(R.id.lv);

        DBManager dbManager = new DBManager(CustomApplcation.getInstance().getApplicationContext());
        ArrayList<House> houses = dbManager.FindHouseLimit();
//        for (House house : houses){
//            System.out.println(house.toString());
//        }
        HouseAdapter houseAdapter = new HouseAdapter(houses, this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent(ShowHouses.this,DetailHouse.class);
                intent.putExtra("tw_url",houses.get(position).getTw_url());
                intent.putExtra("title",houses.get(position).getTitle());
                intent.putExtra("house_price",houses.get(position).getHouse_price());
                intent.putExtra("house_avg_price",houses.get(position).getHouse_avg_price());
                intent.putExtra("house_area_num",houses.get(position).getHouse_area_num());
                intent.putExtra("house_layout",houses.get(position).getHouse_layout());
                intent.putExtra("house_floor_level",houses.get(position).getHouse_floor_level());
                intent.putExtra("house_orient",houses.get(position).getHouse_orient());
                intent.putExtra("house_use_type",houses.get(position).getHouse_use_type());
                intent.putExtra("community_name",houses.get(position).getCommunity_name());
                intent.putExtra("community_address",houses.get(position).getCommunity_address());
                intent.putExtra("broker_name",houses.get(position).getBroker_name());
                intent.putExtra("broker_mobile",houses.get(position).getBroker_mobile());
                Long timestamp = Long.parseLong(String.valueOf(houses.get(position).getPost_date()))*1000;
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.CHINA).format(new Date(timestamp));
                intent.putExtra("post_date",date);
                intent.putExtra("is_call",houses.get(position).getIs_call());
                startActivity(intent);
            }
        });
        listView.setAdapter(houseAdapter);//设置适配器

    }
}
