package com.example.amanleenpuri.gogreen.ui;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.amanleenpuri.gogreen.R;

import java.util.Calendar;

/**
 * Created by amrata on 4/4/16.
 */
public class CreateEventActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    private static TextView datetv;
    private static TextView timetv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_layout);

        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager = getSupportFragmentManager();
        datetv = (TextView) findViewById(R.id.eventDateTextView);
        timetv = (TextView) findViewById(R.id.eventTimeTextView);

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


    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
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


}
