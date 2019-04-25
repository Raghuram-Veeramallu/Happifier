package com.semicolon.happify;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class StoryPosting extends Fragment{


    private View myView;

    public ProgressDialog mProgressDialog;
    String title_content;
    String story_content;
    private TextView story_title_add;
    private TextView story_add;


    private Boolean posted;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fireFB;
    FirebaseAuth.AuthStateListener mAuthListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.new_story, container, false);
        return myView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.new_ad_post);

        story_title_add = getActivity().findViewById(R.id.story_title_add);
        story_add = getActivity().findViewById(R.id.story_add);


        mAuth = FirebaseAuth.getInstance();
        fireFB = FirebaseFirestore.getInstance();


        Button button = (Button) getActivity().findViewById(R.id.post_story_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = view.getId();
                if (i == R.id.post_story_button) {
                    if (validateForm()) {
                        if (addStoryToDatabase()){
                            getFragmentManager().popBackStackImmediate();
                        }
                    }

                }
            }
        });

    }




    private boolean validateForm() {
        boolean valid = true;

        title_content = story_title_add.getText().toString();
        if (TextUtils.isEmpty(title_content)) {
            story_title_add.setError("Required.");
            valid = false;
        } else {
            story_title_add.setError(null);
        }

        story_content = story_add.getText().toString();
        if (TextUtils.isEmpty(story_content)) {
            story_add.setError("Required.");
            valid = false;
        } else {
            story_add.setError(null);
        }

        return valid;
    }

    boolean addStoryToDatabase(){
        Map<String, Object> data = new HashMap<>();
        data.put("title",title_content );
        data.put("content", story_content);

        fireFB.collection("projects")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        return true;
    }

//    private Boolean databaseUserEntry(){
//
//        posted = false;
//        String userid = mAuth.getUid();
//        String username = adUserName.getText().toString().trim();
//        String location = adLocation.getText().toString().trim();
//        String contact = adContact.getText().toString().trim();
//        String description = adDesc.getText().toString().trim();
//        String addDescription = adAddDesc.getText().toString().trim();
//        String title = adTitle.getText().toString().trim();
//        String company = adComp.getText().toString().trim();
//
//        FirebaseUser user = mAuth.getCurrentUser();
//
//        CollectionReference users = fireFB.collection("ads");
//        ad_new new_ad = new ad_new(userid, username, location, contact, description, addDescription, title, company);
//
//        fireFB.collection("ads").document().set(new_ad).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                //Toast.makeText(getActivity(), "Ad Posted Successfully", Toast.LENGTH_LONG);
//                Log.d("userDetails", "created successfully");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                //Toast.makeText(getActivity(), "Could not post ad", Toast.LENGTH_LONG);
//            }
//        });
//
//        return true;
//
//        users.add(usrInf).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(signupActivity.this, "User Created", Toast.LENGTH_LONG);
//            }
//        });
//    }


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }






}
