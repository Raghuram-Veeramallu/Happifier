package com.semicolon.happify;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;

public class VolunteerMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;
    FragmentManager fragmentManager = getFragmentManager();

    FirebaseAuth mAuth;
    //GoogleSignInOptions mGoogleSignInOptions;
    //GoogleSignInClient mGoogleSignInClient;
    FirebaseUser user;
    FirebaseAuth.AuthStateListener mAuthListener;
    private TextView userName;
    private TextView userEmail;
    private ImageView userPhoto;
    public User localUser;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.volunteer_navigation_feed:
                    loadFragment(new story_frag());
//                    fragmentManager.beginTransaction()
//                        .replace(R.id.volunteer_frame_layout, new story_frag()).commit();
                return true;
                case R.id.volunteer_navigation_chat:
                    loadFragment(new volunteer_chat_users());
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.volunteer_frame_layout, new volunteer_chat_users()).commit();
                    return true;

            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.volunteer_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();


        if (id == R.id.volunteer_navigation_events) {
            loadFragment(new EventsActivity());
//            fragmentManager.beginTransaction()
//                    .replace(R.id.volunteer_frame_layout, new EventsActivity()).commit();

        }

          else if (id == R.id.volunteer_navigation_logout) {
            completeSignOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.volunteer_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer_nav_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // fragmentManager.beginTransaction().replace(R.id.volunteer_frame_layout, new story_frag()).commit();

        loadFragment(new story_frag());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.volunteer_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        mAuth=FirebaseAuth.getInstance();

        //mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        //        .requestIdToken(getString(R.string.default_web_client_id))
        //        .requestEmail()
        //        .build();

        //mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser()==null)
                {
                    finish();
                    startActivity(new Intent(VolunteerMainActivity.this, LoginActivity.class));
                } else{
                    localUser = new User(mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getEmail(), mAuth.getCurrentUser().getPhotoUrl());
                    setNameEmail(localUser);
                    Log.d("LOGERR", localUser.getUserEmail() + " " + localUser.getUserGoogleName());
                }
            }
        };


        NavigationView navigationView = (NavigationView) findViewById(R.id.volunteer_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.volunteer_navigationBottom);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void completeSignOut(){
        mAuth.signOut();
        //mGoogleSignInClient.signOut();
        //mGoogleSignInClient.revokeAccess();
    }

    protected void setNameEmail(User user){
        if ((findViewById(R.id.toolbar_user_email) != null) && (findViewById(R.id.toolbar_user_name) != null)){
            userName = (TextView) findViewById(R.id.toolbar_user_name);
            userEmail = (TextView) findViewById(R.id.toolbar_user_email);
            userPhoto = (ImageView) findViewById(R.id.toolbar_profile_image);
            userName.setText(user.getUserGoogleName());
            userEmail.setText(user.getUserEmail());
            try{
                InputStream imageInpStr = getContentResolver().openInputStream(user.getUserImageUri());
                userPhoto.setImageBitmap(BitmapFactory.decodeStream(imageInpStr));
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(user.getUserImageUri()));
                //userPhoto.setImageBitmap(bitmap);
            }catch(Exception e){
                Log.d("LOGERR","Couldn't not load image "+e.getMessage());
            }
        }
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.volunteer_frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }


}
