package com.semicolon.happify;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;
    FragmentManager fragmentManager = getFragmentManager();

    FirebaseAuth mAuth;
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
                case R.id.navigation_feed:
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, new story_frag()).commit();
                    return true;
                case R.id.navigation_chat:
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, new MapsActivity()).commit();
                    return true;
//                case R.id.navigation_dummy:
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.frame_layout, new MapsActivity()).commit();
//                    return true;
                case R.id.navigation_find_psy:
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, new MapsActivity()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();


        if (id == R.id.navigation_events) {
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, new EventsActivity()).commit();

        } else if (id == R.id.navigation_book_app) {
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, new AppointmentBookingActivity()).commit();

        } else if (id == R.id.navigation_info) {
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, new MapsActivity()).commit();

        } else if (id == R.id.navigation_logout) {
            mAuth.signOut();
            // Remove these 2 lines lateron when the signin is setup
//            finish();
//            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        setContentView(R.layout.nav_bar_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager.beginTransaction().replace(R.id.frame_layout, new story_frag()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        mAuth=FirebaseAuth.getInstance();

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser()==null)
                {
                    finish();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                } else{
                    localUser = new User(mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getEmail(), mAuth.getCurrentUser().getPhotoUrl());
                    setNameEmail(localUser);
                    Log.d("LOGERR", localUser.getUserEmail() + " " + localUser.getUserGoogleName());
                }
            }
        };


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.navigationBottom);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    protected void setNameEmail(User user){
        if ((findViewById(R.id.toolbar_user_email) != null) && (findViewById(R.id.toolbar_user_name) != null)){
            userName = (TextView) findViewById(R.id.toolbar_user_name);
            userEmail = (TextView) findViewById(R.id.toolbar_user_email);
            userPhoto = (ImageView) findViewById(R.id.toolbar_profile_image);
            //Log.d("LOGERR",userName.getText().toString());
            userName.setText(user.getUserGoogleName());
            userEmail.setText(user.getUserEmail());
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),user.getUserImageUri());
                userPhoto.setImageBitmap(bitmap);
            }catch(Exception e){
                Log.d("LOGERR","Couldn't not load image "+e.getMessage());
            }
        }
    }


}
