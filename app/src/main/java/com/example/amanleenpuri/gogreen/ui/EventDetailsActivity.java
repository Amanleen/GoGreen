package com.example.amanleenpuri.gogreen.ui;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.amanleenpuri.gogreen.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import model.Event;

/**
 * Created by amrata on 4/22/16.
 */
public class EventDetailsActivity extends AppCompatActivity implements OnMapReadyCallback{
    private static TextView eventTitle;
    private static TextView eventDetail;
    private static TextView hostedBy;
    private static TextView eventDate;
    private static TextView eventTime;
    private static TextView eventVenue;
    private GoogleMap googleMap;
    private LatLng ltln;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details_layout);

        eventTitle = (TextView) findViewById(R.id.tv_eventName);
        eventDetail = (TextView) findViewById(R.id.tv_eventDetail);
        hostedBy = (TextView) findViewById(R.id.tv_hostName);
        eventDate = (TextView) findViewById(R.id.tv_eventDate);
        eventTime = (TextView) findViewById(R.id.tv_eventTime);
        eventVenue = (TextView) findViewById(R.id.tv_Loc);

        Event ev= getIntent().getParcelableExtra("EventObj");
        eventTitle.setText(ev.getEventTitle());
        eventDetail.setText(ev.getEventDescription());
        hostedBy.setText(ev.getUserNameById(ev.getEventHostedById()));
        eventDate.setText(ev.getEventDate());
        eventTime.setText(ev.getEventStartTime()+" - "+ev.getEventEndTime());
        eventVenue.setText(ev.getEventLocation());

        ltln = getLocationFromAddress(EventDetailsActivity.this,eventVenue.getText().toString());
        Log.v("%%%%%MY ADDR %%%%%%%",eventVenue.getText().toString());
        Log.v("%%%%%MY ADDR %%%%%%%",ltln.toString());

        //37.531063, -121.913610
        //googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.location_map)).getMap();

        SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.location_map_takeMeTo);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap gMap) {
        this.googleMap=gMap;
        googleMap.addMarker(new MarkerOptions()
                .position(ltln)
                .title(eventTitle.getText().toString()));
        googleMap.addMarker(new MarkerOptions().position(ltln));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ltln));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
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

