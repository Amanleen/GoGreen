package com.example.amanleenpuri.gogreen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.amanleenpuri.gogreen.R;

/**
 * Created by amrata on 4/4/16.
 */
public class QuestionForumActivity extends AppCompatActivity{
    private Button b1;
    private Button b2;
    private TextView ansTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_forum);

        Button b1=(Button)findViewById(R.id.viewAnsButton);
        Button b2=(Button)findViewById(R.id.answerButton);
        ansTextView = (TextView)findViewById(R.id.answerText);

        if (b1 != null) {
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ansTextView.setVisibility(View.VISIBLE);
                }
            });

        }

        if (b2 != null) {
            b2.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                        Intent myintent2 = new Intent(QuestionForumActivity.this, AnswerActivity.class);
                        startActivity(myintent2);
                }
            });
        }


    }
}
