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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amanleenpuri.gogreen.R;

import java.util.ArrayList;

/**
 * Created by Kumaril on 4/5/2016.
 */
public class NotificationActivity extends AppCompatActivity {

    ImageView cancel;
    ArrayList<Notification> noteData;
    ListView noteList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        cancel= (ImageView) findViewById(R.id.notification_cancel);
        noteList=(ListView)findViewById(R.id.notification_list);
        noteData = new ArrayList<>();
        setNoteList();
        noteList.setAdapter(new NoteListViewAdapter(this, noteData));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, TimelineActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setNoteList(){
        noteData.add(new Notification("Amrata","Diseased Sunflowers"));
        noteData.add(new Notification("Amanleen","Use Roundup"));
        noteData.add(new Notification("Amrata","Bad Soil"));
        noteData.add(new Notification("Amanleen","Temporary"));
    }

    private class Notification{
        String from;
        String subject;

        protected Notification(){}

        protected Notification(String n, String s){
            from=n;
            subject=s;
        }
    }

    private class NoteListViewAdapter extends ArrayAdapter<Notification> {

        NoteListViewAdapter(Context context, ArrayList<Notification> list){
            super(context, android.R.layout.simple_list_item_1,list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Notification p = getItem(position);
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_item, parent, false);
            }
            TextView fromView=(TextView) convertView.findViewById(R.id.note_from);
            TextView subjectView=(TextView) convertView.findViewById(R.id.note_subject);

            fromView.setText(p.from);
            subjectView.setText(p.subject);
            convertView.setOnClickListener(new View.OnClickListener() {
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
