package com.example.amanleenpuri.gogreen.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.amanleenpuri.gogreen.R;

import util.ImagePicker;

/**
 * Created by amrata on 4/3/16.
 */
public class BlogTagAskActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv;
    private CheckBox chkIsQ;
    Boolean Question = false;
    static int TAKE_PICTURE = 1;
    private static final int PICK_IMAGE_ID = 234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_tag_ask_layout);

        ImageButton b1=(ImageButton)findViewById(R.id.imageButton1);
        iv=(ImageView)findViewById(R.id.ivPhoto);
        CheckBox cb = (CheckBox) findViewById(R.id.isQCheck);
        Button b2=(Button)findViewById(R.id.Button2);
        ImageButton b3=(ImageButton) findViewById(R.id.imageButton2);

       b1.setOnClickListener(this);

       if (b1 != null) {
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, TAKE_PICTURE);
                }
            });

        }

        b3.setOnClickListener(this);

        if (b3 != null) {
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent chooseImageIntent = ImagePicker.getPickImageIntent(BlogTagAskActivity.this);
                    startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
                }
            });

        }


        chkIsQ = (CheckBox) findViewById(R.id.isQCheck);

        chkIsQ.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Question = true;
                    Toast.makeText(BlogTagAskActivity.this,
                            "Question will be posted to Forum", Toast.LENGTH_SHORT).show();
                }

            }
        });


        if (b2 != null) {
            b2.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if(!Question) {
                        Intent myintent2 = new Intent(BlogTagAskActivity.this, TimelineActivity.class);
                        startActivity(myintent2);
                    }else {
                        Intent myintent3 = new Intent(BlogTagAskActivity.this, QuestionForumActivity.class);
                        startActivity(myintent3);
                    }

                }
            });
        }


    }

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_ID) {
            Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
            iv.setImageBitmap(bitmap);
        }else {
            super.onActivityResult(requestCode, resultCode, data);
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            iv.setImageBitmap(bp);
        }
    }*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED){
            if (requestCode == PICK_IMAGE_ID) {
                if (data.getExtras() == null){
                    Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                    iv.setImageBitmap(bitmap);
                }else{
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    iv.setImageBitmap(photo);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {

    }
}
