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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;



import com.example.amanleenpuri.gogreen.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;
import util.ValidateText;

public class EditProfileActivity extends Activity {

    private static final String SERVICE_URL = "http://192.168.0.6:8080/GoGreen_Server/rest/user";
    ImageView proPic;
    EditText usernameET;
    EditText passwordET;
    EditText firstNameET;
    EditText lastNameET;
    Spinner roleTypeSP;
    Spinner interestAreaSP;
    EditText cityET;
    EditText stateET;
    Button registerB;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);
        proPic = (ImageView) findViewById(R.id.profilePic);

        //usernameET = (EditText) findViewById(R.id.user_name) ;
        //passwordET = (EditText) findViewById(R.id.user_password);

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

        //registerB = (Button) findViewById(R.id.bn_post);
        //registerB.setOnClickListener(this);

    }



    public void registerUser(View view) throws JSONException, IOException {
        new Thread(new Runnable() {
            public void run() {

                try{

                    //String uName = usernameET.getText().toString();
                    //String pwd= passwordET.getText().toString();

                    String fName = firstNameET.getText().toString();
                    String lName = lastNameET.getText().toString();
                    Drawable profilePic = proPic.getDrawable();
                    String role = roleTypeSP.getSelectedItem().toString();
                    String interest = interestAreaSP.getSelectedItem().toString();
                    String city = cityET.getText().toString();
                    String state = stateET.getText().toString();
                    JSONObject jsonObject = new JSONObject();

                    //URL url = new URL("http://172.29.92.223:8080/GoGreenServer/test");
                    URL url = new URL("http://192.168.0.6:8080/GoGreenServer/UserServlet");

                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                    // Put Http parameter labels with value of Name Edit View control
                    //jsonObject.put("username", uName);
                    //jsonObject.put("password", pwd);
                    jsonObject.put("firstName", fName);
                    jsonObject.put("lastName", lName);
                    jsonObject.put("firstName", fName);
                    jsonObject.put("lastName", lName);
                    //jsonObject.put("proPicture", profilePic);
                    jsonObject.put("roleType", role);
                    if(interest.equals("Indoor")){
                        jsonObject.put("interestArea", 1);
                    }else{
                        jsonObject.put("interestArea", 2);
                    }

                    jsonObject.put("city",city);
                    jsonObject.put("state",state);

                    Log.d("JSONinputString", jsonObject.toString());
                    //invokeWS(jsonObject);

//                            String inputString = inputValue.getText().toString();
                    //String inputString = "{\"name\":\"Komal\", \"age\":26}";

                    //inputString = URLEncoder.encode(inputString, "UTF-8");


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
                    String studentObj = "";



                    while ((returnString = in.readLine()) != null)
                    {
//                      doubledValue= Integer.parseInt(returnString);
                        Log.d("ReturnString", returnString);
                        studentObj = returnString;
                    }
                    in.close();


//                            final Integer finalDoubledValue = doubledValue;
                    final String stu = studentObj;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Log.i("%%%%%%%SER", stu);//JSON returning Student ID


                        }
                    });

                }catch(Exception e)
                {
                    Log.d("Exception",e.toString());
                }

            }
        }).start();


    }

}