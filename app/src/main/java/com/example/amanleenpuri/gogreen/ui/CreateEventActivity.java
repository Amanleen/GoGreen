package com.example.amanleenpuri.gogreen.ui;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import adapter.ProxyUser;

/**
 * Created by amrata on 4/4/16.
 */
public class CreateEventActivity extends AppCompatActivity implements OnMapReadyCallback {

    private EditText eventTitle;
    private EditText eventDescription;
    public static FragmentManager fragmentManager;
    private static TextView datetv;
    private int userId;
    private String userName;

    private static TextView startTimetv;
    private static TextView endTimetv;
    private EditText enterLocation;
    public static boolean mMapIsTouched = false;
    private GoogleMap googleMap;
    Spinner interestAreaSP;
    private LatLng ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_layout);

        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager = getSupportFragmentManager();
        datetv = (TextView) findViewById(R.id.eventDateTextView);
        startTimetv = (TextView) findViewById(R.id.eventStartTimeTextView);
        endTimetv = (TextView) findViewById(R.id.eventEndTimeTextView);
        eventTitle=(EditText) findViewById(R.id.eventNameEditText);
        eventDescription = (EditText) findViewById(R.id.eventDetailEditText);
        enterLocation = (EditText) findViewById(R.id.enterLocEditText);
        interestAreaSP = (Spinner) findViewById(R.id.interest_spinner);

        ImageButton eventDatebtn = (ImageButton)findViewById(R.id.iv_eventDate);
        eventDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        ImageButton eventStartTime = (ImageButton)findViewById(R.id.iv_eventStartTime);
        eventStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartTimePickerDialog(v);
            }
        });

        ImageButton eventEndTime = (ImageButton)findViewById(R.id.iv_eventEndTime);
        eventEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndTimePickerDialog(v);
            }
        });

        Resources res = getResources();
        String[] interestAreaArr = res.getStringArray(R.array.interestArea_array);
        ArrayAdapter<String> adapterInterestArea = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, interestAreaArr);
        interestAreaSP.setAdapter(adapterInterestArea);


        ProxyUser pUser = ProxyUser.getInstance();
        userName = pUser.getUsername(getApplicationContext());
        userId = pUser.getUserId(getApplicationContext());


        Button pb=(Button)findViewById(R.id.publishEventButton);


        if (pb != null) {
            pb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        public void run() {

                            try{


                                String eTitle = eventTitle.getText().toString();
                                String eDescription = eventDescription.getText().toString();
                                String eLocation = enterLocation.getText().toString();
                                String eDate = datetv.getText().toString();
                                String eStartTime = startTimetv.getText().toString();
                                String eEndTime = endTimetv.getText().toString();
                                String interest = interestAreaSP.getSelectedItem().toString();
                                int eInterestId = 0;

                                JSONObject jsonObject = new JSONObject();

                                URL url = new URL("http://192.168.0.6:8080/GoGreenServer/EventServlet");
                                HttpURLConnection connection = (HttpURLConnection)url.openConnection();


                                // Put Http parameter labels with value of Name Edit View control
                                jsonObject.put("eventTitle", eTitle);
                                jsonObject.put("eventDescription", eDescription);
                                jsonObject.put("eventLocation", eLocation);
                                jsonObject.put("eventDate", eDate);
                                jsonObject.put("eventStartTime", eStartTime);
                                jsonObject.put("eventEndTime", eEndTime);
                                jsonObject.put("eventHostedById", userId);
                                if(interest.equals("Indoor")){
                                    jsonObject.put("interestAreaId", 1);
                                }else{
                                    jsonObject.put("interestAreaId", 2);
                                }

                                 Log.d("JSONinputString", jsonObject.toString());

                                connection.setDoOutput(true);
                                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                                out.write(jsonObject.toString());
                                out.close();

                                //connection.disconnect();

                                // connection.setDoInput(true);
                                int responseCode = connection.getResponseCode();
                                Log.d("Response Code = ",String.valueOf(responseCode));
                                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                                String returnString="";

                                while ((returnString = in.readLine()) != null)
                                {
//                      doubledValue= Integer.parseInt(returnString);
                                    Log.d("ReturnString", returnString);
                                    JSONObject eventResponse = new JSONObject(returnString);
                                    int eventId = 0;
                                    eventId = eventResponse.getInt("eventId");
                                    insertNotification(eventId);
                                }

                                in.close();


                            }catch(Exception e)
                            {
                                Log.d("Exception",e.toString());
                            }

                        }
                    }).start();

                    Toast.makeText(CreateEventActivity.this,
                            "Event will be published to all users of GoGreen", Toast.LENGTH_SHORT).show();
                    Intent myintent2 = new Intent(CreateEventActivity.this, MainTimelineActivity.class);
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

    private void insertNotification(int eId) {
        final int eventId = eId;
        Log.v("*********","INSIDE INSERT FUNCTION");
        new Thread(new Runnable() {
            public void run() {
                Log.v("*********","INSIDE RUN");

                try{


                    String eTitle = eventTitle.getText().toString();


                    JSONObject jsonObject = new JSONObject();

                    URL url = new URL("http://192.168.0.6:8080/GoGreenServer/NotificationServlet");
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();


                    // Put Http parameter labels with value of Name Edit View control
                    jsonObject.put("notificationMessage", "Event "+ eTitle+" created by "+userName);
                    jsonObject.put("eventId", eventId);
                    jsonObject.put("greenEntryId", 0);


                    Log.d("JSONinputString", jsonObject.toString());

                    connection.setDoOutput(true);
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                    out.write(jsonObject.toString());
                    out.close();

                    //connection.disconnect();

                    // connection.setDoInput(true);
                    int responseCode = connection.getResponseCode();
                    Log.d("Response Code = ",String.valueOf(responseCode));
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String returnString="";

                    while ((returnString = in.readLine()) != null)
                    {
//                      doubledValue= Integer.parseInt(returnString);
                        Log.d("ReturnString", returnString);

                    }
                    in.close();


                   /* final String responseObj = returnString;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Log.i("%%%%%%%NotificationID", responseObj);

                        }
                    });*/

                }catch(Exception e)
                {
                    Log.d("Exception",e.toString());
                }

            }
        }).start();
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

    public void showStartTimePickerDialog(View v) {
        DialogFragment newFragment = new StartTimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showEndTimePickerDialog(View v) {
        DialogFragment newFragment = new EndTimePickerFragment();
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


    public static class StartTimePickerFragment extends DialogFragment
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

            startTimetv.setText(eventTime);
            startTimetv.setVisibility(View.VISIBLE);
        }


    }

    public static class EndTimePickerFragment extends DialogFragment
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

            endTimetv.setText(eventTime);
            endTimetv.setVisibility(View.VISIBLE);
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
