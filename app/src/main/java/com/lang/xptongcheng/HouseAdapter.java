package com.lang.xptongcheng;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.anjuke.mobile.sign.House;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;

import static android.Manifest.permission.CALL_PHONE;

public class HouseAdapter extends BaseAdapter {

    private ArrayList<House> mData;
    private Context mContext;

    public HouseAdapter(ArrayList<House> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.simple_item,parent,false);
//        ImageView img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView broker_name = (TextView) convertView.findViewById(R.id.broker_name);
        Button phone = convertView.findViewById(R.id.phone);
        title.setText(mData.get(position).getTitle());
        broker_name.setText(mData.get(position).getBroker_name());
        phone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(Intent.ACTION_CALL);
//                Uri data = Uri.parse("tel:" + mData.get(position).getBroker_mobile());
//                intent.setData(data);
//                mContext.startActivity(intent);
                Intent broadCastIntent = new Intent();
                broadCastIntent.putExtra("phone", mData.get(position).getBroker_mobile());
                broadCastIntent.setAction("com.lang.tongcheng.call");
                mContext.sendBroadcast(broadCastIntent);
            }
        });
        return convertView;
    }

}