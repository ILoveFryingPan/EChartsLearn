package com.example.zxing.qrcode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import com.example.zxing.R;

public class HelloWorldActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView hello = new TextView(this);
        hello.setTextSize(30);
        hello.setTextColor(0xff333333);
        hello.setGravity(Gravity.CENTER);
        hello.setText("Hello World");
        setContentView(R.layout.activity_hello);
    }
}
