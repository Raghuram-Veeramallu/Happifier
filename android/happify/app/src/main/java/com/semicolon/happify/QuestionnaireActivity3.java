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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class QuestionnaireActivity3 extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    ListView listView;

    ArrayList<String> Question_ques = new ArrayList<>();
    ArrayList<String> Question_opt_sel=new ArrayList<String>();

    Button submitQuest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.questionnaire);

        listView = findViewById(R.id.questions_list_format);
        submitQuest = findViewById(R.id.questionnaire_submit);

        submitQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(QuestionnaireActivity3.this, MainActivity.class));
            }
        });

        addQuestionsToList();
        setup_questions_list();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser()==null)
                {
                    Intent i=new Intent(QuestionnaireActivity3.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        };

    }

    private void addQuestionsToList(){
        //TODO: Remove this function after making proper list in Firebase
        Question_ques.add("Thoughts that you would be better off dead or of hurting yourself?");
        Question_ques.add("Little interest or pleasure in doing things?");
        Question_ques.add("Trouble falling or staying asleep or sleeping too much?");
        Question_ques.add("Feeling tired or having little energy?");
        Question_ques.add("Poor appetite or overeating?");
        Question_ques.add("Feeling bad about yourself- or that you are failure or have let yourself or your family down?");
        Question_ques.add("Trouble concentrating on things, such as reading the newspaper or watching television?");
        Question_ques.add("Moving or speaking so slowly that other people could have noticed?");
        Question_ques.add("Thoughts that you would be better off dead or of hurting yourself?");
        Question_ques.add("If you checked off any problems, how difficult have these problems made it for you at work, home or with other people?");

    }

    private void setup_questions_list(){
        SimpleAdapter simpleAdapter = new SimpleAdapter(QuestionnaireActivity3.this, Question_ques);
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
//                    selected.add(text);

//                    if (text.equalsIgnoreCase("Rarely"))
//                    {
//                        score=score+1;
//                    }
//                    else if (text.equalsIgnoreCase("Never"))
//                    {
//                        score=score+0;
//                    }
//                    else if (text.equalsIgnoreCase("Often"))
//                    {
//                        score=score+3;
//                    }
//                    else if (text.equalsIgnoreCase("All the time"))
//                    {
//                        score=score+5;
//                    }
                }
            });

            Question.setText(quesArray.get(position));

            return convertView;

        }
    }

}
