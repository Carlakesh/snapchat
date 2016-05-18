package com.example.carla.snapchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.backendless.Backendless;

public class MainActivity extends AppCompatActivity {

    public static final String APP_ID = "7347C59D-B155-5471-FFD1-8C2D9BE85F00";
    public static final String SECRET_KEY = "95C1C46D-CE06-7450-FF5E-371BDE7A3700";
    public static final String VERSION = "v1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginMenuFragment loginMenu = new LoginMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, loginMenu).commit();

        Backendless.initApp(this, APP_ID, SECRET_KEY, VERSION);
    }

}
