package com.example.amanleenpuri.gogreen.ui;

import android.app.Activity;

/**
 * Created by amrata on 4/26/16.
 */

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;



import com.example.amanleenpuri.gogreen.R;

import cz.msebera.android.httpclient.Header;
import util.ValidateText;

public class EditProfileActivity extends Activity {

    private static final String SERVICE_URL = "http://192.168.0.6:8080/GoGreen_Server/rest/user";
    ImageView proPic;
    EditText firstNameET;
    EditText lastNameET;
    Spinner roleTypeSP;
    Spinner interestAreaSP;
    EditText cityET;
    EditText stateET;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);
        proPic = (ImageView) findViewById(R.id.profilePic);
        firstNameET = (EditText) findViewById(R.id.first_name);
        lastNameET = (EditText) findViewById(R.id.last_name);
        roleTypeSP = (Spinner) findViewById(R.id.role_spinner);
        interestAreaSP = (Spinner) findViewById(R.id.interest_spinner);
        cityET = (EditText) findViewById(R.id.city_name);
        stateET = (EditText) findViewById(R.id.state_name);

        String[] roleTypeSPArr = new String[]{"Plant Lover", "Horticulturist", "Environmentalist"};
        ArrayAdapter<String> adapterSP1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, roleTypeSPArr);
        roleTypeSP.setAdapter(adapterSP1);

        String[] interestAreaSPArr = new String[]{"Indoor", "Outdoor"};
        ArrayAdapter<String> adapterSP2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, interestAreaSPArr);
        interestAreaSP.setAdapter(adapterSP2);

    }


    public void registerUser(View view){

        String fName = firstNameET.getText().toString();
        String lName = lastNameET.getText().toString();
        Drawable profilePic = proPic.getDrawable();
        String role = roleTypeSP.getSelectedItem().toString();
        String interest = interestAreaSP.getSelectedItem().toString();
        String city = cityET.getText().toString();
        String state = stateET.getText().toString();

        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // When Name Edit View, Email Edit View and Password Edit View have values other than Null
        if(ValidateText.isNotNull(fName) && ValidateText.isNotNull(lName) && ValidateText.isNotNull(role) &&
                ValidateText.isNotNull(interest) && ValidateText.isNotNull(city) && ValidateText.isNotNull(state)){


                // Put Http parameter labels with value of Name Edit View control
                params.put("fname", fName);
                params.put("lname", lName);
                params.put("proPicture", profilePic);
                params.put("userRole", role);
                params.put("userInterest", interest);
                params.put("userCity",city);
                params.put("userState",state);
                // Invoke RESTful Web Service with Http parameters
                invokeWS(params);
        }
        // When any of the Edit View control left blank
        else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }

    }

    public void invokeWS(RequestParams params){

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.0.6:8080/GoGreen_Server/rest/user/sample",params ,new AsyncHttpResponseHandler() {


            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.v("%%%%%%%statusCode",String.valueOf(statusCode));
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject();
                    Log.v("%%%%%%%getBoolean", obj.toString());

                    // When the JSON response has status boolean value assigned with true
                    //if(obj.getBoolean("status")){
                    if(statusCode == 200){
                        // Set Default Values for Edit View controls
                        //setDefaultValues();

                        // Display successfully registered message using Toast
                        Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
                    }
                    // Else display error message
                    else{
                        //errorMsg.setText(obj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.v("%%%%%%%statusCode",String.valueOf(statusCode));
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}