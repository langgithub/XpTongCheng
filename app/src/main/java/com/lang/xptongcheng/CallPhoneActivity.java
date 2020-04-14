package com.lang.xptongcheng;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CallPhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_phone);
        EditText tv = findViewById(R.id.phone_test);
        findViewById(R.id.save).setOnClickListener(arg0 -> {
            System.out.println(tv.getText());
            Intent broadCastIntent = new Intent();
            broadCastIntent.putExtra("phone", tv.getText().toString());
            broadCastIntent.setAction("com.lang.tongcheng.call");
            sendBroadcast(broadCastIntent);
        });
    }

}
