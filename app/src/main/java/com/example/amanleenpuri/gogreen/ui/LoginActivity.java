package com.example.amanleenpuri.gogreen.ui;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amanleenpuri.gogreen.R;

import adapter.ProxyUser;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = (Button)findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameEt = (EditText)findViewById(R.id.et_username);
                EditText passwordEt = (EditText)findViewById(R.id.et_password);
                String userName = usernameEt.getText().toString();
                String password = passwordEt.getText().toString();
                if(userName.isEmpty()){
                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Please enter a user name!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
                if(password.isEmpty()){
                    Toast toast= Toast.makeText(getApplicationContext(),
                            "Please enter a password!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
                if(!userName.isEmpty() && !password.isEmpty()){
                    //TODO: AUTHENTICATE USER FROM SERVER
                    // IF VALID ADD TO LOCAL DB
                    int userId = 1; //TEMP
                    ProxyUser pUser = ProxyUser.getInstance();
                    pUser.addUser(userName,userId, getApplicationContext());

                    Intent i = new Intent(LoginActivity.this, MainTimelineActivity.class);
                    startActivity(i);
                }
            }
        });

        TextView signUpTv = (TextView)findViewById(R.id.tv_signup);
        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        TextView forgotLoginTv = (TextView)findViewById(R.id.tv_forgot_login);
        forgotLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
