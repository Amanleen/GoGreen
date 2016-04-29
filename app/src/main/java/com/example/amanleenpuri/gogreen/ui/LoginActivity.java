package com.example.amanleenpuri.gogreen.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.amanleenpuri.gogreen.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = (Button)findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(LoginActivity.this, TimelineActivity.class);
                Intent i = new Intent(LoginActivity.this, MainTimelineActivity.class);
                startActivity(i);
            }
        });

        TextView signUpTv = (TextView)findViewById(R.id.tv_signup);
        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
