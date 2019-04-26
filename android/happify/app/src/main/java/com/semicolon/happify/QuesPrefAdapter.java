package com.semicolon.happify;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class QuesPrefAdapter extends RecyclerView.Adapter<QuesPrefAdapter.MyViewHolder>{

    List<String> listArray;
    private Context context;
    LinearLayout lListen;
    private RecyclerView recyclerView;

    @Override
    public QuesPrefAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questionnaire_pref_model, parent, false);
        recyclerView = parent.findViewById(R.id.questionPreRecycler);
        lListen = (LinearLayout) parent.findViewById(R.id.pref_ques_clickable_outer);
        lListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = recyclerView.getChildLayoutPosition(v);
                String item = listArray.get(itemPosition);
                Log.d("LOGERR",item);
            }
        });

        return new QuesPrefAdapter.MyViewHolder(view);
    }

    public QuesPrefAdapter(Context context, List<String> List){
        this.listArray = List;
        this.context = context;
    }


    @Override
    public void onBindViewHolder(QuesPrefAdapter.MyViewHolder holder, final int position) {
        final String data = listArray.get(position);
        holder.questionSet.setText(data);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView questionSet;


        public MyViewHolder(View itemView) {
            super(itemView);
            questionSet = (TextView) itemView.findViewById(R.id.question_set);
        }
    }

    @Override
    public int getItemCount() {
        return listArray.size();
    }


}
