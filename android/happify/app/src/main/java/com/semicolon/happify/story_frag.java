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
//
//        String firstname = "Rithvik";
//        String lastname = "Sallaram";
//        String title = "My Interesting Story";
//        String content = "This is my story. Veri molestie atomorum eos ei, laudem omnesque ad eam. In regione accumsan hendrerit vis, mei eu aliquid quaerendum. Habeo nullam sapientem te mea. In has illum augue, albucius delicata argumentum vis at, ex odio nemore cons";
//        story storyobj1 = new story(firstname, lastname, content, title);
//        stories.add(storyobj1);
//
//        String firstname1 = "Raman";
//        String lastname1 = "Dutt";
//        String title1 = "My Awesome Story";
//        String content1 = "Veri molestie atomorum eos ei, laudem omnesque ad eam. In regione accumsan hendrerit vis, mei eu aliquid quaerendum. Habeo nullam sapientem te mea. In has illum augue, albucius delicata argumentum vis at, ex odio nemore cons";
//        story storyobj2 = new story(firstname1, lastname1, content1, title1);
//        stories.add(storyobj2);
//
//        String firstname2 = "Rishabb";
//        String lastname2 = "Ranjan";
//        String title2 = "My amazing Story";
//        String content2 = "Veri molestie atomorum eos ei, laudem omnesque ad eam. In regione accumsan hendrerit vis, mei eu aliquid quaerendum. Habeo nullam sapientem te mea. In has illum augue, albucius delicata argumentum vis at, ex odio nemore cons";
//        story storyobj3 = new story(firstname2, lastname2, content2, title2);
//        stories.add(storyobj3);
//
//        adapter = new MyAdapter(getActivity(), stories);
//        recyclerView.setAdapter(adapter);
        Log.w(TAG, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        progressDialog = new ProgressDialog(getActivity());


        db=FirebaseFirestore.getInstance();
        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
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
