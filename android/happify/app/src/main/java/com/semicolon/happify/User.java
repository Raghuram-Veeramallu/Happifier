package com.semicolon.happify;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class User {

    private String userGoogleName;
    private String userEmail;
    private String userPrefName;
    private Uri userImageUri;

    public User(String name, String email, Uri url){
        this.userGoogleName = name;
        this.userEmail = email;
        this.userImageUri = url;
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

    public Uri getUserImageUri() {
        return userImageUri;
    }

    public void setUserImageUri(Uri userImage) {
        this.userImageUri = userImage;
    }

}
