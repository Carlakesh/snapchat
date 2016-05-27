package layout;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.example.carla.snapchat.Constants;
import com.example.carla.snapchat.FriendRequest;

import java.util.List;


public class SnapchatService extends IntentService {


    public SnapchatService() {

        super("SnapchatService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
              String action = intent.getAction();
            if(action.equals(Constants.ACTION_ADD_FRIEND)) {
                String firstUserName = intent.getStringExtra("firstUserName");
                String secondUserName = intent.getStringExtra("secondUserName");
                Log.i("SnapchatService", "Service adding friend. First user: " + firstUserName + ".Second user: "
                + secondUserName);
                addFriends(firstUserName , secondUserName);
            } else if (action.equals(Constants.ACTION_SEND_FRIEND_REQUEST)) {
                String toUser = intent.getStringExtra("toUser");
                String fromUser = intent.getStringExtra("fromUser");
                Log.i("SnapchatService", "Send friend request to" + toUser + "from" + fromUser);
                sendFriendRequest(fromUser, toUser);
            }
        }
    }

        private  void sendFriendRequest(final String fromUser, final String toUser) {
                //make sure toUser exists
            BackendlessDataQuery query = new BackendlessDataQuery();
            query.setWhereClause(String.format("name = '%s'", toUser));

            Backendless.Persistence.of(BackendlessUser.class).find(query, new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
                @Override
                public void handleResponse(BackendlessCollection<BackendlessUser> response) {
                    if(response.getData().size() == 0 ) {
                       broadcastFriendRequestFailure();
                    }else {
                        //create a friend request
                        FriendRequest friendRequest = new FriendRequest();
                        friendRequest.setToUser(toUser);
                        friendRequest.setFromUser(fromUser);
                        friendRequest.setAccepted(false);
                        //save friend request to backendless

                        Backendless.Persistence.save(friendRequest, new AsyncCallback<FriendRequest>() {
                            @Override
                            public void handleResponse(FriendRequest response) {
                               broadcastFriendRequestSuccess();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                broadcastFriendRequestFailure();
                            }
                        });
                    }
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    broadcastFriendRequestFailure();
                }
            });
            //create a friend request
            // save friend request

        }
        private void addFriends(String firstUserName, String secondUserName) {
            //find both users and update the first user & second
            BackendlessDataQuery query = new BackendlessDataQuery();
            query.setWhereClause(String.format("name = '%s' or name = '%s'", firstUserName, secondUserName));
            Backendless.Persistence.of(BackendlessUser.class).find(query, new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
                @Override
                public void handleResponse(BackendlessCollection<BackendlessUser> response) {
                    List<BackendlessUser> users = response.getData();
                    if (users.size() !=2){
                        broadcastAddFriendFailure();
                    } else {
                        BackendlessUser user1 = users.get(0);
                      final  BackendlessUser user2 = users.get(1);

                        updateFriendsList(user1, user2);
                        Backendless.UserService.update(user1, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser user) {
                                //update user 2
                                updateFriendsList(user2, user);
                                Backendless.UserService.update(user2, new AsyncCallback<BackendlessUser>() {
                                    @Override
                                    public void handleResponse(BackendlessUser response) {
                                        broadcastAddFriendSuccess();
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {
                                        broadcastAddFriendFailure();
                                    }
                                });


                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                broadcastAddFriendFailure();
                            }
                        });
                    }

                }



                @Override
                public void handleFault(BackendlessFault fault) {
                        broadcastAddFriendFailure();
                }
            });


        }

        private void broadcastAddFriendSuccess() {
           Intent intent = new Intent(Constants.BROADCAST_ADD_FRIEND_SUCCESS);
            sendBroadcast(intent);


        }

        private void broadcastAddFriendFailure () {
            Intent intent = new Intent(Constants.BROADCAST_ADD_FRIEND_FAILURE);
            sendBroadcast(intent);
        }

        private void broadcastFriendRequestSuccess() {
            Intent intent = new Intent(Constants.BROADCAST_FRIEND_REQUEST_SUCCESS);
            sendBroadcast(intent);
        }

        private void broadcastFriendRequestFailure() {
            Intent intent = new Intent(Constants.BROADCAST_FRIEND_REQUEST_FAILURE);
            sendBroadcast(intent);
        }

    private void updateFriendsList(BackendlessUser user, BackendlessUser friend) {
        BackendlessUser[] newFriends;

        Object[] currentFriendObjects = (Object[]) user.getProperty("friends");
        if (currentFriendObjects.length > 0) {
            BackendlessUser[] currentFriends = (BackendlessUser[]) currentFriendObjects;
            newFriends = new BackendlessUser[currentFriends.length +1 ];
            for (int i = 0; i < currentFriends.length; i++) {
                newFriends[i] = currentFriends[i];
            }
            newFriends[newFriends.length - 1] = friend;
        }else {
            newFriends = new BackendlessUser[] { friend};
        }

        user.setProperty("friends", newFriends);

    }
}
