package com.semicolon.happify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class QuestionPrefActivity extends AppCompatActivity {

    ListView testList;
    ArrayList<String> tl;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_ques_pref_activity);

        tl = new ArrayList<String>();
        tl.add("PTSD");
        tl.add("Anxiety");
        tl.add("Depression");
        tl.add("Bipolar");

        testList = (ListView) this.findViewById(R.id.testNames);

        setInit();

        testList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = tl.get(position);
                if (item.equals("PTSD")){
                    startActivity(new Intent(QuestionPrefActivity.this, QuestionnaireActivity1.class));
                }else if (item.equals("Anxiety")){
                    startActivity(new Intent(QuestionPrefActivity.this, QuestionnaireActivity2.class));
                }else if (item.equals("Depression")){
                    startActivity(new Intent(QuestionPrefActivity.this, QuestionnaireActivity3.class));
                }else if (item.equals("Bipolar")){
                    startActivity(new Intent(QuestionPrefActivity.this, QuestionnaireActivity4.class));
                }
            }
        });

    }


    public void setInit(){
        testList.setVisibility(View.VISIBLE);
        testList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tl));
    }




}
