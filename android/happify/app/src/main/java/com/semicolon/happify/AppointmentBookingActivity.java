package com.semicolon.happify;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AppointmentBookingActivity extends Fragment {

    View myView;
    private Spinner citySpinner, areaSpinner, doctorSpinner;
    EditText dateChooser;
    private Button bookAppointment;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db ;
    HashMap<String, String> cityArea;
    HashMap<String, String> areaDoc;
    private FragmentActivity myContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.appointment_booking, container, false);

        citySpinner = (Spinner) myView.findViewById(R.id.citySpinner);
        areaSpinner = (Spinner) myView.findViewById(R.id.areaSpinner);
        doctorSpinner = (Spinner) myView.findViewById(R.id.doctorSpinner);
        dateChooser = (EditText) myView.findViewById(R.id.dateEntryBookApp);
        bookAppointment = (Button) myView.findViewById(R.id.bookAppointButton);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        setSpinners();
        dateChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointmentEntry();
            }
        });

        db = FirebaseFirestore.getInstance();

        //Loaddatafromfirebase();
        progressDialog.dismiss();

        return myView;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePicker();
        newFragment.show(myContext.getSupportFragmentManager(), "datePicker");
    }

    public void setSpinners(){
        String[] cities = new String[]{"", "Noida", "Delhi", "Mumbai"};  // temp
        String[] areas = new String[]{"", "GB Nagar", "Dadri", "Sonpet"};
        String[] doctors = new String[]{"", "SD", "VS", "RJ"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cities);
        citySpinner.setAdapter(adapter);
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, areas);
        areaSpinner.setAdapter(areaAdapter);
        ArrayAdapter<String> doctorAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, doctors);
        doctorSpinner.setAdapter(doctorAdapter);
    }

    public void bookAppointmentEntry(){
        String city = citySpinner.getSelectedItem().toString();
        String area = areaSpinner.getSelectedItem().toString();
        String doctor = doctorSpinner.getSelectedItem().toString();


    }

    void addBookingDatabase(){
        Map<String, Object> data = new HashMap<>();
        data.put("area",areaSpinner.getSelectedItem().toString());
        data.put("city", citySpinner.getSelectedItem().toString());
        data.put("doctor",doctorSpinner.getSelectedItem().toString());
        data.put("userEmail", User.getUserEmail());
        data.put("userName", User.getUserGoogleName());
        data.put("booked", false);

        db.collection("appointmentBookings")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        //Toast.makeText()
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }



    public void completeBooking(){


    }


}
