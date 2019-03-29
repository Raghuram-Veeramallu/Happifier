package com.semicolon.happify;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class AskQuesPrefActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private Button takeQuesPref;
    private TextView skipQuesPref;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire_pref);

        takeQuesPref = (Button) findViewById(R.id.take_ques_button);
        skipQuesPref = (TextView) findViewById(R.id.skip_ques);

        mAuth=FirebaseAuth.getInstance();

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //TODO: Remove this after signin is complete

//                if (firebaseAuth.getCurrentUser()==null)
//                {
//                    Intent i=new Intent(AskQuesPrefActivity.this,LoginActivity.class);
//                    startActivity(i);
//                }
            }
        };


        takeQuesPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AskQuesPrefActivity.this, QuestionnaireActivity.class));
            }
        });

        skipQuesPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AskQuesPrefActivity.this, MainActivity.class));
            }
        });

    }

}
