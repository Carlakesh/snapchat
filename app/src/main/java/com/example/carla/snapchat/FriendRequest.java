package com.example.carla.snapchat;


public class FriendRequest {

    private String toUser;
    private String fromUser;
    private boolean accepted;

    public FriendRequest() {
        toUser = "";
        fromUser = "";
        accepted = false;
    }

    public String getToUser() {
        return toUser;
    }
        public void setToUser(String user) {
            toUser = user;

        }
    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String user) {
        fromUser = user;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean isAccepted) {
        accepted = isAccepted;
    }
}
