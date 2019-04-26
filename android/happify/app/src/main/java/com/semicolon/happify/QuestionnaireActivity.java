package com.semicolon.happify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class QuestionnaireActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    ListView listView;

    ArrayList<String> Question_ques = new ArrayList<>();
    ArrayList<String> selected=new ArrayList<String>();
    static int score = 0;
    Button submitQuest;

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
        score = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.questionnare_new_front);

        listView = (ListView) findViewById(R.id.rv);
        submitQuest = findViewById(R.id.submit);
        submitQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuestionnaireActivity.this, MainActivity.class));
                Toast.makeText(QuestionnaireActivity.this, "" + score, Toast.LENGTH_SHORT).show();

            }
        });


        addQuestionsToList();
        setup_questions_list();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser()==null)
                {
                    Intent i=new Intent(QuestionnaireActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        };

    }

    private void addQuestionsToList(){
        //TODO: Remove this function after making proper list in Firebase
        Question_ques.add("Having Upsetting thought or images about the traumatic Event that come into your head when you did not want them to.");
        Question_ques.add("Having bad dreams or nightmares about the Event.");
        Question_ques.add("Reliving the traumatic Event");
        Question_ques.add("Feeling emotionally upset when you are reminded of the traumatic Event.");
        Question_ques.add("Experiencing physical reactions when you are reminded of the traumatic Event (sweating, increased heart rate.");
        Question_ques.add("Trying not to think or talk about the traumatic Event.");
        Question_ques.add("Trying to avoid activities or people that remind you of that traumatic Event.");
        Question_ques.add("Not being able to remember an important part of the traumatic Event.");
        Question_ques.add("Having much less interest or participation in important activities.");
        Question_ques.add("Feeling distant or cut off from people around you.");
        Question_ques.add("Feeling emotionally numb (unable to cry or have feelings)");
        Question_ques.add("Feeling as if your future hopes or plans will not come true.");
        Question_ques.add("Having trouble falling or staying asleep.");
        Question_ques.add("Feeling irritable or having fits of anger.");
        Question_ques.add("Having trouble concentrating.");
        Question_ques.add("Being overly alert.");
        Question_ques.add("Being jumpy or easily startled.");
    }

    private void setup_questions_list(){
        QuestionnaireActivity.SimpleAdapter simpleAdapter = new SimpleAdapter(QuestionnaireActivity.this, Question_ques);
        listView.setAdapter(simpleAdapter);
    }

    public class SimpleAdapter extends BaseAdapter {

        Context mContext;
        LayoutInflater layoutinflater;
        TextView Question;
        RadioGroup group;
        ArrayList<String> quesArray;

        RadioButton rb1;
        RadioButton rb2;
        RadioButton rb3;
        RadioButton rb4;

        public SimpleAdapter(Context context, ArrayList<String> questions)
        {
            mContext=context;
            quesArray=questions;
            layoutinflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return quesArray.size();
        }

        @Override
        public Object getItem(int position) {
            return quesArray.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null)
            {
                convertView=layoutinflater.inflate(R.layout.cards_list_questionnare,null);
            }
            Question=convertView.findViewById(R.id.ques);
            group=convertView.findViewById(R.id.radioGroup);

            rb1 = convertView.findViewById(R.id.button1);
            rb2 = convertView.findViewById(R.id.button2);
            rb3 = convertView.findViewById(R.id.button3);
            rb4 = convertView.findViewById(R.id.button4);

            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb=findViewById(checkedId);

                    String text = rb.getText().toString();
                    selected.add(text);

                    if (text.equalsIgnoreCase("Rarely"))
                    {
                        score=score+1;
                    }
                    else if (text.equalsIgnoreCase("Never"))
                    {
                        score=score+0;
                    }
                    else if (text.equalsIgnoreCase("Often"))
                    {
                        score=score+3;
                    }
                    else if (text.equalsIgnoreCase("All the time"))
                    {
                        score=score+5;
                    }
                }
            });

            Question.setText(quesArray.get(position));

            return convertView;

        }
    }


}
