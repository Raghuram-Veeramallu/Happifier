package com.semicolon.happify;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

//import com.google.firebase.firestore.FirebaseFirestore;

public class story_frag extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressDialog progressDialog;
    private List<story> stories;
    View myView;
    FirebaseFirestore db ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.story_feed, container, false);


        recyclerView = (RecyclerView) myView.findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stories = new ArrayList<>();


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        db=FirebaseFirestore.getInstance();
        //displaying progress dialog while fetching images

        Log.w(TAG, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Loaddatafromfirebase();
        progressDialog.dismiss();

        return myView;
    }
    void Loaddatafromfirebase(){
        Log.w(TAG, "Entered function");



            db.collection("projects")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            for (DocumentSnapshot document : task.getResult()) {
                                Log.w(TAG, "Entered for loop");
                                String firstname = document.getString("authorFirstName");
                                String lastname = document.getString("authorLastName");
                                String title = document.getString("title");
                                String content = document.getString("content");
                                story storyobj = new story(firstname, lastname, content, title);
                                stories.add(storyobj);
                                Log.w(TAG, firstname);
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
