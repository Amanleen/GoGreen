package com.example.amanleenpuri.gogreen.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.amanleenpuri.gogreen.R;

import java.io.ByteArrayOutputStream;

import util.ImagePicker;

/**
 * Created by amrata on 4/3/16.
 */
public class BlogTagAskActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv;
    private AlertDialog levelDialog;
    private CheckBox chkIsQ;
    Boolean Question = false;
    static int TAKE_PICTURE = 1;
    private static final int PICK_IMAGE_ID = 234;
    Bitmap _bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_tag_ask_layout);
        iv = (ImageView) findViewById(R.id.ivPhoto);

        if(getIntent().hasExtra("imgArray")) {
            //ivTree= new ImageView(this);
            iv.setVisibility(View.VISIBLE);
            _bm = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("imgArray"),0,getIntent().getByteArrayExtra("imgArray").length);
            iv.setImageBitmap(_bm);
        }

        Button b2=(Button)findViewById(R.id.Button2);
        ImageButton b3=(ImageButton) findViewById(R.id.imageButton2);

        /*ImageButton b1=(ImageButton)findViewById(R.id.imageButton1);
        if(iv == null) {
            iv = (ImageView) findViewById(R.id.ivPhoto);
        }

       b1.setOnClickListener(this);

       if (b1 != null) {
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, TAKE_PICTURE);
                }
            });

        }*/

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


        /*chkIsQ = (CheckBox) findViewById(R.id.isQCheck);

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
        });*/


        if (b2 != null) {
            b2.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    final CharSequence[] items = {" This is a Question "," This is a Blog "};

                    // Creating and Building the Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(BlogTagAskActivity.this);
                    builder.setTitle("Please confirm");
                   /* builder.setPositiveButton("Post as A Question", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //BlogTagAskActivity.this.finish();
                            Intent myintent3 = new Intent(BlogTagAskActivity.this, QuestionForumActivity.class);
                            startActivity(myintent3);
                        }
                    })
                            .setNegativeButton("Post as A Blog", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //dialog.cancel();
                                    Intent myintent2 = new Intent(BlogTagAskActivity.this, TimelineActivity.class);
                                    startActivity(myintent2);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();*/
                    builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            switch(item)
                            {
                                case 0:
                                    Intent myintent3 = new Intent(BlogTagAskActivity.this, QuestionForumActivity.class);
                                    startActivity(myintent3);
                                    break;
                                case 1:
                                    Intent myintent2 = new Intent(BlogTagAskActivity.this, TimelineActivity.class);
                                    startActivity(myintent2);
                                    break;
                            }
                            levelDialog.dismiss();
                        }
                    });
                    levelDialog = builder.create();
                    levelDialog.show();

                    /*if(!Question) {
                        Intent myintent2 = new Intent(BlogTagAskActivity.this, TimelineActivity.class);
                        startActivity(myintent2);
                    }else {
                        Intent myintent3 = new Intent(BlogTagAskActivity.this, QuestionForumActivity.class);
                        startActivity(myintent3);
                    }*/

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
                    final Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                    iv.setVisibility(View.VISIBLE);
                    iv.setImageBitmap(bitmap);
                    //iv.setOnClickListener(clickListener);
                    iv.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent tagIntent = new Intent(BlogTagAskActivity.this, TagATreeActivity.class);
                            ByteArrayOutputStream bs = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
                            tagIntent.putExtra("byteArray", bs.toByteArray());
                            startActivity(tagIntent);
                        }
                    });
                }else{
                    final Bitmap photo = (Bitmap) data.getExtras().get("data");
                    iv.setVisibility(View.VISIBLE);
                    iv.setImageBitmap(photo);
                    //iv.setOnClickListener(clickListener);
                    iv.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent tagIntent = new Intent(BlogTagAskActivity.this, TagATreeActivity.class);
                            ByteArrayOutputStream bs = new ByteArrayOutputStream();
                            photo.compress(Bitmap.CompressFormat.PNG, 50, bs);
                            tagIntent.putExtra("byteArray", bs.toByteArray());
                            startActivity(tagIntent);
                        }
                    });
                }
            }
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        public void onClick(View v) {
            Intent tagIntent = new Intent(BlogTagAskActivity.this, TagATreeActivity.class);
            Bitmap bm = null; // your bitmap

            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 50, bs);
            tagIntent.putExtra("byteArray", bs.toByteArray());
            startActivity(tagIntent);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {

    }
}
