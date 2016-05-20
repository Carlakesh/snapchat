package com.example.carla.snapchat;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginMenuFragment extends Fragment {

    private EditText usernameField;
    private EditText passwordField;

    public LoginMenuFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button button = (Button) view.findViewById(R.id.LoginButton);
        usernameField = (EditText) view.findViewById(R.id.usernameField);
        passwordField = (EditText) view.findViewById(R.id.passwordField);

        button.setOnClickListener(new View.OnClickListener() {



      //  String[] loginMenuItems = {"Register",
      //          "Log In"};
      //  ListView listView = (ListView)view.findViewById(R.id.loginMenu);


     //   ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
       //         getActivity(),
        //        android.R.layout.simple_list_item_1,
          //      loginMenuItems );

       // listView.setAdapter(listViewAdapter);


       // listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         //   @Override
           // public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //   if (position == 0) {
               //     Intent intent = new Intent(getActivity(), RegisterActivity.class);
                 //   startActivity(intent);
                //} else if (position == 1) {
                  //  Intent intent = new Intent(getActivity(), LoginActivity.class);
                    //startActivity(intent);
                //}
            //}

        //});

        //return view;
        //}
    //}

            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                Backendless.UserService.login(username, password, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(getActivity(), "You have logged in!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getActivity(), "Logging in failed", Toast.LENGTH_SHORT).show();
                    }

                });
            }


                });

        return view;
    }

}
