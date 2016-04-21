package com.example.amanleenpuri.gogreen.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.amanleenpuri.gogreen.R;

import java.util.ArrayList;

import model.GreenEntry;

/**
 * Created by amrata on 4/4/16.
 */
public class QuestionForumActivity extends AppCompatActivity{
    private Button b1;
    private Button b2;
    private TextView ansTextView;
    ArrayList<GreenEntry> qaData;
    ArrayList<GreenEntry> ansData;
    ListView qaList;
    ListView ansList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_forum);

        qaList=(ListView)findViewById(R.id.qa_list);
        //ansList = (ListView)findViewById(R.id.ans_list);
        qaData = new ArrayList<>();
        //ansData = new ArrayList<>();
        setQaList();
        //setAnsList();
        qaList.setAdapter(new QAListViewAdapter(getApplicationContext(), qaData));
        //ansList.setAdapter(new ANSListViewAdapter(getApplicationContext(), ansData));

        //Button b1=(Button)findViewById(R.id.viewAnsButton);
        //Button b2=(Button)findViewById(R.id.answerButton);


       /* if (b1 != null) {
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ansList.setVisibility(View.VISIBLE);
                }
            });

        }*/

        /*if (b2 != null) {
            b2.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                        Intent myintent2 = new Intent(QuestionForumActivity.this, AnswerActivity.class);
                        startActivity(myintent2);
                }
            });
        }*/


    }


    private void setQaList(){
        qaData.add(new GreenEntry("Question",12345,"Diseased Sunflowers?"));
        qaData.add(new GreenEntry("Question",23456,"White spots on flowers"));
        qaData.add(new GreenEntry("Question",34567,"Bad Soil"));
        qaData.add(new GreenEntry("Question",45678,"Temporary"));
    }

    private void setAnsList(){
        ansData.add(new GreenEntry("Answer",12345,"Sprinkle Salt"));
        ansData.add(new GreenEntry("Answer",23456,"user organic pesticides"));
        ansData.add(new GreenEntry("Answer",34567,"Please provide some details"));
        ansData.add(new GreenEntry("Answer",45678,"What is your question exactly"));
    }


    private class QAListViewAdapter extends ArrayAdapter<GreenEntry> {

        QAListViewAdapter(Context context, ArrayList<GreenEntry> list){
            super(context, android.R.layout.simple_list_item_1,list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GreenEntry ge = getItem(position);
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.question_item, parent, false);
            }
            ansList = (ListView) convertView.findViewById(R.id.ans_list);

            ansData = new ArrayList<>();
            setAnsList();
            ansList.setAdapter(new ANSListViewAdapter(getApplicationContext(), ansData));

            TextView question=(TextView) convertView.findViewById(R.id.tv_question);
            Button viewAnsB=(Button) convertView.findViewById(R.id.viewAnsButton);
            final LinearLayout ll=(LinearLayout)convertView.findViewById(R.id.ans_layout);

            question.setText("Q. "+ ge.text);
            //viewAnsB.setText("Answers " +ansData.size());
            viewAnsB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ansData.size()>0) {
                        if(ll.getVisibility() == View.VISIBLE){
                            ll.setVisibility(View.GONE);
                        }else {
                            ll.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        Intent intent = new Intent(getContext(), AnswerActivity.class);
                        startActivity(intent);
                    }
                }
            });

            return convertView;
        }
    }


    private class ANSListViewAdapter extends ArrayAdapter<GreenEntry> {

        ANSListViewAdapter(Context context, ArrayList<GreenEntry> list){
            super(context, android.R.layout.simple_list_item_1,list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.answer_item, parent, false);
            }
            TextView answer=(TextView) convertView.findViewById(R.id.tv_answerText);
            Button ansButton=(Button) convertView.findViewById(R.id.answerButton);

            //-----------------ListView In ListView----------------------------------------
            //ansList = (ListView)findViewById(R.id.ans_list);
            //ansData = new ArrayList<>();
            //setAnsList();
            //ansList.setAdapter(new ANSListViewAdapter(getApplicationContext(), ansData));

           /* Button b2=(Button)findViewById(R.id.answerButton);

            if (b2 != null) {
                b2.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        Intent myintent2 = new Intent(QuestionForumActivity.this, AnswerActivity.class);
                        startActivity(myintent2);
                    }
                });
            }*/
            //---------------------------------------------------------
            StringBuilder temp=new StringBuilder();

            for(int i =0; i<ansData.size();i++) {
                //GreenEntry ge = getItem(position);
                GreenEntry ge = ansData.get(i);
                temp.append("Ans. " + ge.text +"\n");
                position=position++;
            }

            answer.setText(temp.toString());

            ansButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), AnswerActivity.class);
                    startActivity(intent);
                }
            });


            return convertView;
        }
    }
}
