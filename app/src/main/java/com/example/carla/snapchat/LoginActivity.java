package com.example.carla.snapchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginMenuFragment loginMenu = new LoginMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, loginMenu).commit();
    }
}
