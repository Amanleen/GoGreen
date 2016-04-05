package com.example.amanleenpuri.gogreen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.amanleenpuri.gogreen.R;

/**
 * Created by amrata on 4/4/16.
 */
public class AnswerActivity extends AppCompatActivity {
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_layout);

        Button b = (Button) findViewById(R.id.postAnswerButton);

        if (b != null) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), QuestionForumActivity.class);
                    startActivity(intent);
                }
            });

        }
    }
}
