package com.lang.xptongcheng;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class DetailHouse extends AppCompatActivity {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;
    private TextView tv10;
    private TextView tv11;
    private TextView tv12;
    private TextView tv13;
    private TextView tv14;
    private TextView tv15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_house);

        Intent intent = getIntent();

        tv1=findViewById(R.id.tw_url);
        tv2=findViewById(R.id.title);
        tv3=findViewById(R.id.house_price);
        tv4=findViewById(R.id.house_avg_price);
        tv5=findViewById(R.id.house_area_num);
        tv6=findViewById(R.id.house_layout);
        tv7=findViewById(R.id.house_floor_level);
        tv8=findViewById(R.id.house_orient);
        tv9=findViewById(R.id.house_use_type);
        tv10=findViewById(R.id.community_name);
        tv11=findViewById(R.id.community_address);
        tv12=findViewById(R.id.broker_name);
        tv13=findViewById(R.id.broker_mobile);
        tv14=findViewById(R.id.post_date);
        tv15=findViewById(R.id.is_call);

        String tw_url = intent.getStringExtra("tw_url");
        String title = intent.getStringExtra("title");
        String house_price = intent.getStringExtra("house_price");
        String house_avg_price = intent.getStringExtra("house_avg_price");
        String house_area_num = intent.getStringExtra("house_area_num");
        String house_layout = intent.getStringExtra("house_layout");
        String house_floor_level = intent.getStringExtra("house_floor_level");
        String house_orient = intent.getStringExtra("house_orient");
        String house_use_type = intent.getStringExtra("house_use_type");
        String community_name = intent.getStringExtra("community_name");
        String community_address = intent.getStringExtra("community_address");
        String broker_name = intent.getStringExtra("broker_name");
        String broker_mobile = intent.getStringExtra("broker_mobile");
        String post_date = intent.getStringExtra("post_date");
        String is_call = intent.getStringExtra("is_call");


        tv1.setText(tw_url);
        tv2.setText(title);
        tv3.setText(house_price);
        tv4.setText(house_avg_price);
        tv5.setText(house_area_num);
        tv6.setText(house_layout);
        tv7.setText(house_floor_level);
        tv8.setText(house_orient);
        tv9.setText(house_use_type);
        tv10.setText(community_name);
        tv11.setText(community_address);
        tv12.setText(broker_name);
        tv13.setText(broker_mobile);
        tv14.setText(post_date);
        tv15.setText(is_call);

    }

}
