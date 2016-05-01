package com.example.amanleenpuri.gogreen.ui;

import android.app.Activity;

/**
 * Created by amrata on 4/26/16.
 */

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import adapter.ProxyUser;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;
import model.User;
import util.ValidateText;

public class EditProfileActivity extends AppCompatActivity {

    private static final String SERVICE_URL = "http://192.168.0.6:8080/GoGreen_Server/rest/user";


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ProxyUser pUser = ProxyUser.getInstance();
        String userName = pUser.getUsername(getApplicationContext());
        int userId = pUser.getUserId(getApplicationContext());
        if(userName.isEmpty() || userId==0){
            Intent i = new Intent(EditProfileActivity.this, LoginActivity.class);
            startActivity(i);

        }else{

            setContentView(R.layout.appbar_editprofile);

            //TODO: CALL SERVLET AND GET USER INFORMATION FROM USERNAME AND USERID

            Resources res = getResources();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.EditProfileLabel);
            setSupportActionBar(toolbar);

            final Spinner roleTypeSp = (Spinner)findViewById(R.id.sp_roleSpinnerEditProfile);
            String[] roleTypeArr = res.getStringArray(R.array.roleType_array);
            ArrayAdapter<String> adapterRoleType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, roleTypeArr);
            roleTypeSp.setAdapter(adapterRoleType);
            //TODO: SET SELECTED ROLE TYPE ON SPINNER

            Spinner interestAreaSp = (Spinner)findViewById(R.id.sp_interestAreaSpinnerEditProfile);
            String[] interestAreaArr = res.getStringArray(R.array.interestArea_array);
            ArrayAdapter<String> adapterInterestArea = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, interestAreaArr);
            interestAreaSp.setAdapter(adapterInterestArea);
            //TODO: SET SELECTED INTEREST TYPE ON SPINNER

            final EditText firstNameEt = (EditText)findViewById(R.id.et_firstNameEditProfile);
            firstNameEt.setTag("First Name");
            //TODO: SET SELECTED FIRSTNAME

            final EditText lastNameEt = (EditText)findViewById(R.id.et_lastNameEditProfile);
            lastNameEt.setTag("Last Name");
            //TODO: SET SELECTED LASTNAME

            final EditText cityEt = (EditText)findViewById(R.id.et_cityEditProfile);
            cityEt.setTag("City");
            //TODO: SET SELECTED CITY

            final EditText stateEt = (EditText)findViewById(R.id.et_stateEditProfile);
            stateEt.setTag("State");
            //TODO: SET SELECTED STATE

            ImageView profilePicIv = (ImageView)findViewById(R.id.iv_profilePicEditProfile);
            //TODO:******* FIGURE OUT WHAT IS TO BE DONE WITH PROFILE PIC


            final String roleSelection = roleTypeSp.getSelectedItem().toString();
            final String interestAreaSelection = interestAreaSp.getSelectedItem().toString();

            Button editProfileBtn = (Button) findViewById(R.id.btn_editProfile);
            editProfileBtn.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    EditText invalidEditText = checkIfEntered(firstNameEt, lastNameEt, cityEt, stateEt);
                    if (invalidEditText != null) {
                        Toast toast= Toast.makeText(getApplicationContext(), invalidEditText.getTag() + " field cannot be empty.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }else if(roleSelection.isEmpty()){
                        Toast toast= Toast.makeText(getApplicationContext(), " Select a role Type!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }else if(interestAreaSelection.isEmpty()){
                        Toast toast= Toast.makeText(getApplicationContext(), " Select an area of Interest!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }else{

                        User user = new User();
                        user.setFirstName(firstNameEt.getText().toString());
                        user.setLastName(lastNameEt.getText().toString());
                        user.setCity(cityEt.getText().toString());
                        user.setState(stateEt.getText().toString());
                        user.setRole(roleSelection);
                        user.setInterest(interestAreaSelection);

                        String jsonString = "";
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            jsonString = mapper.writeValueAsString(user);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }

                        //TODO: CALL SERVLET TO SEND THE USER's JSON OBJECT

                    }
                }
            });
        }
    }

    EditText checkIfEntered(EditText... allInputFields) {
        for (EditText editText : allInputFields) {
            if (TextUtils.isEmpty(editText.getText())) {
                return editText;
            }
        }
        return null;
    }

}
