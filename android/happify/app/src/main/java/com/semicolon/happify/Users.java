package com.semicolon.happify;



import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Users extends Fragment{
    View myView;
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    static HashMap<String,String> map = new HashMap<String,String>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_user, container, false);
        usersList = (ListView)myView.findViewById(R.id.usersList);
        noUsersText = (TextView)myView.findViewById(R.id.noUsersText);
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://spmproject-be72b.firebaseio.com/Volunteers.json";
        String url1 = "https://spmproject-be72b.firebaseio.com/messages.json";
        //String s = "https://spmproject-be72b.firebaseio.com/Users.json";
        int j=0;
        try {
            JSONObject obj = new JSONObject(url);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                if((key.equals(User.getUserEmail().split("@")[0])) ) {
//                    key +="\n"+obj.getJSONObject(key).getString("Tag");
//                    map.put(key,key);
//                    al.add(key);
                    j++;
                }

                //totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest request;
        if(j>0) {
            request = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    doOnSuccess1(s);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println("" + volleyError);
                }
            });
        }
        else {

            request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    doOnSuccess(s);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println("" + volleyError);
                }
            });
        }
        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);
        //rQueue.add(request1);
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] str = al.get(position).split("\n");
                User.chatwith = str[0];
                startActivity(new Intent(getActivity(), Chat.class));
            }
        });

        return myView;
    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                if((!key.equals(User.getUserEmail().split("@")[0])) ) {
                    key +="\n"+obj.getJSONObject(key).getString("Tag");
                    //map.put(key,key);
                    al.add(key);
                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();
    }

    public void doOnSuccess1(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();
                String[] str = key.split("_");
                if((str[0].equals(User.getUserEmail().split("@")[0]))) {
                    //key +="\n"+obj.getJSONObject(key).getString("Tag");
                    //map.put(str[0],str[0]);
                    //map.put(str[1],str[1]);
                    al.add(str[1]);
                }


                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();
    }


}