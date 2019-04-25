package com.semicolon.happify;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class User {

    private static String userGoogleName;
    private static String userEmail;
    private static String userPrefName;
    public static String chatwith;
    private static Uri userImageUri;

    public User(String name, String email, Uri url){
        this.userGoogleName = name;
        this.userEmail = email;
        this.userImageUri = url;
    }


    public static String getUserGoogleName() {
        return userGoogleName;
    }

    public static void setUserGoogleName(String userGoogleName) {
        User.userGoogleName = userGoogleName;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String userEmail) {
        User.userEmail = userEmail;
    }

    public static String getUserPrefName() {
        return userPrefName;
    }

    public static void setUserPrefName(String userPrefName) {
        User.userPrefName = userPrefName;
    }

    public static Uri getUserImageUri() {
        return userImageUri;
    }

    public static void setUserImageUri(Uri userImage) {
        User.userImageUri = userImage;
    }

}
