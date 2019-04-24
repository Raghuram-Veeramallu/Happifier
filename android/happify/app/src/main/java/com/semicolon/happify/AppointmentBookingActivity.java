package com.semicolon.happify;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AppointmentBookingActivity extends Fragment {

    View myView;
    Spinner cityDd, areaDd, doctorDd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.appointment_booking, container, false);

        cityDd = (Spinner) myView.findViewById(R.id.citySpinner);

        String[] items = new String[]{"1", "2", "three"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,items);
        cityDd.setAdapter(adapter);

        return myView;
    }

}
