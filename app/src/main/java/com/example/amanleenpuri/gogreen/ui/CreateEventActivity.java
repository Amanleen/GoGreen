package com.example.amanleenpuri.gogreen.ui;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.amanleenpuri.gogreen.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.List;

/**
 * Created by amrata on 4/4/16.
 */
public class CreateEventActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static EditText eventTitle;
    public static FragmentManager fragmentManager;
    private static TextView datetv;
    private static TextView timetv;
    private static EditText enterLocation;
    public static boolean mMapIsTouched = false;
    private GoogleMap googleMap;
    private LatLng ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_layout);

        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager = getSupportFragmentManager();
        datetv = (TextView) findViewById(R.id.eventDateTextView);
        timetv = (TextView) findViewById(R.id.eventTimeTextView);
        eventTitle=(EditText) findViewById(R.id.eventNameEditText);
        enterLocation = (EditText) findViewById(R.id.enterLocEditText);


        Button pb=(Button)findViewById(R.id.publishEventButton);


        if (pb != null) {
            pb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CreateEventActivity.this,
                            "Event will be published to all users of GoGreen", Toast.LENGTH_SHORT).show();
                    Intent myintent2 = new Intent(CreateEventActivity.this, TimelineActivity.class);
                    startActivity(myintent2);
                }
            });
        }


        /*MySupportMapFragment msmf = new MySupportMapFragment();
        View mapView = msmf.getView();
        if(mapView!=null) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
            mapView.getContext().startActivity(intent);
        }*/


    }

    public void showLocation(View v){
        if(!enterLocation.getText().equals("")){
            ll = getLocationFromAddress(this,enterLocation.getText().toString());
            Log.v("%%%%%MY ADDR %%%%%%%",ll.toString());

            //googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.location_map)).getMap();

            SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                    .findFragmentById(R.id.location_map);
            mapFragment.getMapAsync(this);

            //MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.location_map);
            //mapFragment.getMapAsync((OnMapReadyCallback) getApplicationContext());


        }

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        this.googleMap=gMap;
        googleMap.addMarker(new MarkerOptions()
                .position(ll)
                .title(eventTitle.getText().toString()));
        googleMap.addMarker(new MarkerOptions().position(ll));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));


    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String eventDate = String.valueOf(month)
                    + "/" + String.valueOf(day)+"/"+String.valueOf(year);

            datetv.setText(eventDate);
            datetv.setVisibility(View.VISIBLE);
        }
    }


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String eventTime = String.valueOf(hourOfDay)
                    + ":" + String.valueOf(minute);
            timetv.setText(eventTime);
            timetv.setVisibility(View.VISIBLE);
        }


    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<android.location.Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            android.location.Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }



}
