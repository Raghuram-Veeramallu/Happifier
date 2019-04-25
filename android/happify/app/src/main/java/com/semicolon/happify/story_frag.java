package com.semicolon.happify;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

//import com.google.firebase.firestore.FirebaseFirestore;

public class story_frag extends Fragment {
    Dialog myDialog;
    FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton fab;
    private ProgressDialog progressDialog;
    FragmentManager fragmentManager;
    private FloatingActionButton fab;
    private List<story> stories;
    private TextView story_title_add;
    private TextView story_add;
    User localUser1;
    String title_content;
    String story_content;
    SwipeRefreshLayout pullToRefresh;
    View myView;
    FirebaseFirestore db ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        myView = inflater.inflate(R.layout.story_feed, container, false);
        myDialog = new Dialog(getActivity());

        pullToRefresh = (SwipeRefreshLayout) myView.findViewById(R.id.pullToRefresh);
        recyclerView = (RecyclerView) myView.findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stories = new ArrayList<>();

        fragmentManager = getActivity().getFragmentManager();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        db=FirebaseFirestore.getInstance();
        //displaying progress dialog while fetching images

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {


            @Override
            public void onRefresh() {
                Loaddatafromfirebase();

                pullToRefresh.setRefreshing(false);
            }
        });
        Loaddatafromfirebase();
        progressDialog.dismiss();

        fab = (FloatingActionButton) myView.findViewById(R.id.floating_add);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   fabAction();

//                fragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, new ad_postingActivity()).addToBackStack("ads_stack").commit();


            }
        });

        return myView;
    }




    void fabAction(){
        localUser1 = new User(mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getEmail(), mAuth.getCurrentUser().getPhotoUrl());
        ImageView close_button;
        Button post_button;
        myDialog.setContentView(R.layout.new_story_popup);
        story_title_add = myDialog.findViewById(R.id.story_title_add);
        story_add = myDialog.findViewById(R.id.story_add);
        close_button =(ImageView) myDialog.findViewById(R.id.close_button_popup);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss(); }
       });
        post_button = (Button) myDialog.findViewById(R.id.post_story_button);

        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = view.getId();
                if (i == R.id.post_story_button) {
                    if (validateForm()) {
                        addStoryToDatabase();
                    }

                }
                myDialog.dismiss();
            }

        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        System.out.println();
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

    void addStoryToDatabase(){
        Map<String, Object> data = new HashMap<>();
        data.put("authorFirstName",localUser1.getUserGoogleName() );
        data.put("authorLastName", " ");
        data.put("title",title_content );
        data.put("content", story_content);
        data.put("createdAt", FieldValue.serverTimestamp());

        db.collection("projects")
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

    }




    void Loaddatafromfirebase(){
        Log.w(TAG, "Entered function");



            db.collection("projects")
                    .orderBy("createdAt", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(!stories.isEmpty()){
                                stories.clear();
                            }

                            for (DocumentSnapshot document : task.getResult()) {
                                Log.w(TAG, "Entered for loop");
                                String firstname = document.getString("authorFirstName");
                                String lastname = document.getString("authorLastName");
                                String title = document.getString("title");
                                String content = document.getString("content");
                                story storyobj = new story(firstname, lastname, content, title);
                                stories.add(storyobj);
                              //  Log.w(TAG, firstname);
                            }

                            adapter = new MyAdapter(getActivity(), stories);
                            recyclerView.setAdapter(adapter);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error getting documents.");
                        }
                    });

    }

}
