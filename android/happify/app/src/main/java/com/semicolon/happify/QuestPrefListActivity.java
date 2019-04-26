package com.semicolon.happify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class QuestPrefListActivity extends AppCompatActivity {

    private static ArrayList<String> ques_list;// = new ArrayList<String>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressDialog progressDialog;
    LinearLayout lListen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_questionnaire);

        recyclerView = (RecyclerView) this.findViewById(R.id.questionPreRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ques_list = new ArrayList<String>();
        ques_list.add("PTSD");
        ques_list.add("Anxiety");
        ques_list.add("Depression");
        ques_list.add("Bipolar");

        adapter = new QuesPrefAdapter(this, ques_list);
        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

//        lListen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkClicks(v);
//            }
//        });


        progressDialog.dismiss();

    }


    public static void checkClicks(View v, RecyclerView rc){
        int itemPosition = rc.getChildLayoutPosition(v);
        String item = ques_list.get(itemPosition);
        //changeActivity();
    }

    public void changeActivity(String item){
        if (item.equals("PTSD")){
            startActivity(new Intent(QuestPrefListActivity.this, QuestionnaireActivity1.class));
        }else if (item.equals("Anxiety")){
            startActivity(new Intent(QuestPrefListActivity.this, QuestionnaireActivity2.class));
        }else if (item.equals("Depression")){
            startActivity(new Intent(QuestPrefListActivity.this, QuestionnaireActivity3.class));
        }else if (item.equals("Bipolar")){
            startActivity(new Intent(QuestPrefListActivity.this, QuestionnaireActivity4.class));
        }
    }





}
