package layout;


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
import com.example.carla.snapchat.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsListFragment extends Fragment {

        private ArrayList<String> friends;
        private ArrayAdapter<String> friendListAdapter;

    public FriendsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_friends_list, container, false);

        friends = new ArrayList<String>();
        friendListAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, friends);

        ListView friendsList = (ListView) view.findViewById(R.id.friendList);
        friendsList.setAdapter(friendListAdapter);

        String currentUser = Backendless.UserService.loggedInUser();
        Backendless.Persistence.of(BackendlessUser.class).findById(currentUser, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser user) {
                Object[] friendObjects = (Object[]) user.getProperty("friends");
                if (friendObjects.length > 0) {
                    BackendlessUser[] friendArray = (BackendlessUser []) friendObjects;
                    for (BackendlessUser friend: friendArray) {
                        String name = friend.getProperty("name").toString();
                        friends.add(name);
                        friendListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

        return view;

    }

}
