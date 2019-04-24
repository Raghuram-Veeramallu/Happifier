package com.semicolon.happify;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AppointmentBookingActivity extends Fragment {

    View myView;
    private Spinner cityDd, areaDd, doctorDd;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db ;
    HashMap<String, String> cityArea;
    HashMap<String, String> areaDoc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.appointment_booking, container, false);

        cityDd = (Spinner) myView.findViewById(R.id.citySpinner);
        areaDd = (Spinner) myView.findViewById(R.id.areaSpinner);
        doctorDd = (Spinner) myView.findViewById(R.id.doctorSpinner);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        setSpinners();

        db = FirebaseFirestore.getInstance();

        //Loaddatafromfirebase();
        progressDialog.dismiss();

        return myView;
    }

    public void setSpinners(){
        String[] cities = new String[]{"Noida", "Delhi", "Mumbai"};
        String[] areas = new String[]{"GB Nagar", "Dadri", "Sonpet"};
        String[] doctors = new String[]{"SD", "VS", "RJ"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cities);
        cityDd.setAdapter(adapter);
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, areas);
        areaDd.setAdapter(areaAdapter);
        ArrayAdapter<String> doctorAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, doctors);
        doctorDd.setAdapter(doctorAdapter);
    }


    void Loaddatafromfirebase(){

        db.collection("PsychiatristLocations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        cityArea = new HashMap<>();
                        areaDoc = new HashMap<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            String cityName = document.getString("City");
                            String areaName = document.getString("Location");
                            String doctorName = document.getString("Doctor");
                            cityArea.put(cityName, areaName);
                            areaDoc.put(areaName, doctorName);
                        }
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
