package com.example.carla.snapchat;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import layout.SnapchatService;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFriendFragment extends Fragment {


    public AddFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view inflater.inflate(R.layout.fragment_add_friend, container, false);

        Button addFriendButton = (Button) view.findViewById(R.id.addFriendButton);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Add a Friend.");

                final EditText inputField = new EditText(getActivity());
                alertDialog.setView(inputField);
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //user cancels

                    }
                });

                alertDialog.setPositiveButton("Add Friend", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addFriend(inputField.getText().toString());
                        Toast.makeText(getActivity(), "Add friend", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.create();
                alertDialog.show();
            }

        });
        return view;
    }

        private void addFriend(final String friendName) {
            String currentUserId = Backendless.UserService.loggedInUser();
            Backendless.Persistence.of(BackendlessUser.class).findById(currentUserId, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser currUser) {
                    Intent intent = new Intent(getActivity(), SnapchatService.class);
                    intent.setAction(Constants.ACTION_ADD_FRIEND);
                    intent.putExtra("firstUserName", currUser.getProperty("name").toString());
                    intent.putExtra("secondUserName", friendName);

                    getActivity().startService(intent);
                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });


        }
}