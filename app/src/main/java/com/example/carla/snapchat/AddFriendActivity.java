package com.example.carla.snapchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        AddFriendFragment addFriend = new AddFriendFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.addFriend, addFriend).commit();
    }
}
