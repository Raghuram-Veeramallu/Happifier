package com.semicolon.happify;

public class User {

    private String userGoogleName;
    private String userEmail;
    private String userPrefName;


    public User(String name, String email){
        this.userGoogleName = name;
        this.userEmail = email;
    }


    public String getUserGoogleName() {
        return userGoogleName;
    }

    public void setUserGoogleName(String userGoogleName) {
        this.userGoogleName = userGoogleName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPrefName() {
        return userPrefName;
    }

    public void setUserPrefName(String userPrefName) {
        this.userPrefName = userPrefName;
    }




}
