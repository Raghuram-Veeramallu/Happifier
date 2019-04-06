package com.semicolon.happify;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    List<story> listArray;
    private Context context;



    public MyAdapter(Context context, List<story> List){
        this.listArray = List;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_model, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final story data = listArray.get(position);
        holder.authorFirstName.setText(String.valueOf(data.getAuthorFirstName()));
        holder.authorLastName.setText(String.valueOf(data.getAuthorLastName()));
        holder.title.setText(String.valueOf(data.getTitle()));
        holder.content.setText(String.valueOf(data.getContent()));


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //Ctrl + O
        TextView authorFirstName,authorLastName,content,title,createdAt;


        public MyViewHolder(View itemView) {
            super(itemView);
            authorFirstName = (TextView)itemView.findViewById(R.id.authorFirstName);
            authorLastName = (TextView) itemView.findViewById(R.id.authorLastName);
            content = (TextView) itemView.findViewById(R.id.content);
            title = (TextView) itemView.findViewById(R.id.title);

        }
    }


    @Override
    public int getItemCount() {
        return listArray.size();
    }
}
//kello