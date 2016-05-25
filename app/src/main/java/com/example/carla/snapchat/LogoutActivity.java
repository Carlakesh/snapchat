package com.example.carla.snapchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoggingoutFragment logout = new LoggingoutFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.logoutContainer, logout).commit();
    }
}
