package com.example.carla.snapchat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SnapchatReceiver extends BroadcastReceiver {
    public SnapchatReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Constants.BROADCAST_ADD_FRIEND_SUCCESS)) {
            Toast.makeText(context, "added friend!", Toast.LENGTH_SHORT).show();
        } else if (action.equals(Constants.BROADCAST_ADD_FRIEND_FAILURE)) {
            Toast.makeText(context, "Failed to add friend", Toast.LENGTH_SHORT).show();
                }
        }

}