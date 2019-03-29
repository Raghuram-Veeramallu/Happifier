package com.semicolon.happify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button googleLogin, happifyLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        googleLogin = findViewById(R.id.google_signup);
        happifyLogin = findViewById(R.id.happify_signup);

        happifyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });

    }

    protected void signIn(){
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }



}
