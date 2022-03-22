package com.example.wang.test;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.annotated.BindView;
import com.example.manager.BindViewManager;
import com.example.wang.R;


public class TestActivity extends AppCompatActivity {
    @BindView(R.id.net)
    TextView net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        BindViewManager.bind(this);
        net.setText("BindView");

    }
}
