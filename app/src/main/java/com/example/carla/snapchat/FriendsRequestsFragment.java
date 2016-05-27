package com.example.carla.snapchat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsRequestsFragment extends Fragment {

    private ArrayAdapter<String> friendsRequestAdapter;

    public FriendsRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view inflater.inflate(R.layout.fragment_friends_requests, container, false);

        fromUsers = new ArrayList<String>();
        friendsRequestAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, fromUsers);

        ListView friendsList = (ListView) view.findViewById(R.id.friendsRequestsList);
        friendsList.setAdapter(friendsRequestAdapter);

        String userId = Backendless.UserService.loggedInUser();
        Backendless.UserService.findById(userId, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                String currentUserName = response.getProperty("name").toString();
                getIncomingFriendsRequests(currentUserName);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
        return view;

    }
        private void getIncomingFriendsRequests(String username) {
            BackendlessDataQuery query = new BackendlessDataQuery();
            query.setWhereClause(String.format("toUser = '%s'", username));
        }

}
