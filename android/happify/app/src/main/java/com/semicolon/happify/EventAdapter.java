package com.semicolon.happify;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    List<Event> listArray;
    private Context context;


    public EventAdapter(Context context, List<Event> List){
        this.listArray = List;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_model, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Event event_data = listArray.get(position);
        holder.eventName.setText(String.valueOf(event_data.getEventName()));
        holder.eventLocation.setText(String.valueOf(event_data.getEventLocation()));
        holder.eventDate.setText(String.valueOf(event_data.getEventDate()));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView eventName, eventLocation, eventDate;


        public MyViewHolder(View itemView) {
            super(itemView);
            eventName = (TextView)itemView.findViewById(R.id.eventName);
            eventLocation = (TextView) itemView.findViewById(R.id.eventLocation);
            eventDate = (TextView) itemView.findViewById(R.id.eventDate);
        }
    }


    @Override
    public int getItemCount() {
        return listArray.size();
    }
}