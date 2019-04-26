package com.semicolon.happify;

import android.app.FragmentManager;
//import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;
    FragmentManager fragmentManager = getFragmentManager();
    //FragmentTransaction fragmentTransaction = getSupportFragmentManager();

    FirebaseAuth mAuth;
    GoogleSignInOptions mGoogleSignInOptions;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseUser user;
    FirebaseAuth.AuthStateListener mAuthListener;
    private TextView userName;
    private TextView userEmail;
    private ImageView userPhoto;
    public User localUser;

    //fragmentTransaction = getSupportFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_feed:
                    loadFragment(new story_frag());
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.frame_layout, new story_frag()).commit();
                    return true;
                case R.id.navigation_chat:
                    if(User.getUserEmail().split("@")[1].equals("snu.edu.in")){
                        loadFragment(new Users());
                    }
                    else{
                        Toast.makeText(MainActivity.this, "You are not authorised to use this feature", Toast.LENGTH_SHORT).show();
                    }
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.frame_layout, new Users()).commit();
                    return true;
                case R.id.navigation_find_psy:
                    loadFragment(new MapsActivity2());
                    //fragmentManager.beginTransaction().replace(R.id.frame_layout, new MapsActivity2()).commit();
                    //fragmentManager.beginTransaction()
                    //        .replace(R.id.frame_layout, new MapsActivity2()).commit();
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
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();


        if (id == R.id.navigation_events) {
            loadFragment(new EventsActivity());
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_layout, new EventsActivity()).commit();

        } else if (id == R.id.navigation_book_app) {
            loadFragment(new AppointmentBookingActivity());
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_layout, new AppointmentBookingActivity()).commit();

        } else if (id == R.id.navigation_info) {
            loadFragment(new MapsActivity());
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_layout, new MapsActivity()).commit();

        } else if (id == R.id.navigation_logout) {
            completeSignOut();
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

        //fragmentManager.beginTransaction().replace(R.id.frame_layout, new story_frag()).commit();

        loadFragment(new story_frag());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        mAuth=FirebaseAuth.getInstance();

        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
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


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    private void completeSignOut(){
        mAuth.signOut();
        mGoogleSignInClient.signOut();
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


}
