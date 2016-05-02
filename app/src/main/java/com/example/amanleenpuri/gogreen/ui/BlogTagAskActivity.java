package com.example.amanleenpuri.gogreen.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.amanleenpuri.gogreen.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import adapter.ProxyUser;
import util.ImagePicker;

/**
 * Created by amrata on 4/3/16.
 */
public class BlogTagAskActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv;
    private  Button b2;
    private ImageButton b3;
    private EditText blogMsgET;
    private String blogPost;

    private AlertDialog levelDialog;
    private CheckBox chkIsQ;
    private String blogType;
    private int userId;
    static int TAKE_PICTURE = 1;
    private static final int PICK_IMAGE_ID = 234;
    Bitmap _bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_tag_ask_layout);
        iv = (ImageView) findViewById(R.id.ivPhoto);
        Button b2=(Button)findViewById(R.id.Button2);
        ImageButton b3=(ImageButton) findViewById(R.id.imageButton2);
        final EditText blogMsgET = (EditText) findViewById(R.id.editTextBlog);

        ProxyUser pUser = ProxyUser.getInstance();
        userId = pUser.getUserId(getApplicationContext());



        if(getIntent().hasExtra("imgArray")) {
            //ivTree= new ImageView(this);
            iv.setVisibility(View.VISIBLE);
            _bm = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("imgArray"),0,getIntent().getByteArrayExtra("imgArray").length);
            iv.setImageBitmap(_bm);
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

        if (b2 != null) {


            b2.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    blogPost = blogMsgET.getText().toString();

                    final CharSequence[] items = {" This is a Question "," This is a Blog "};

                    // Creating and Building the Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(BlogTagAskActivity.this);

                    builder.setTitle("Please confirm");

                    builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

                        Intent myintent2 = new Intent(BlogTagAskActivity.this, MainTimelineActivity.class);
                        public void onClick(DialogInterface dialog, int item) {
                            switch(item)
                            {
                                case 0:
                                    blogType = "Question";
                                    makeGreenEntry();
                                    startActivity(myintent2);
                                    break;
                                case 1:
                                    blogType = "Blog";
                                    makeGreenEntry();
                                    startActivity(myintent2);
                                    break;
                            }
                            levelDialog.dismiss();
                        }
                    });
                    levelDialog = builder.create();
                    levelDialog.show();

                    Toast.makeText(BlogTagAskActivity.this,
                            "Your GreenEntry is being published on the Timeline", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void makeGreenEntry() {
        Log.v("INSIDE MAKEGREENENTRY","JUST ENTERED");
        new Thread(new Runnable() {
            public void run() {
                try{

                    Log.v("INSIDE MAKEGREENENTRY",blogPost);
                    Log.v("INSIDE MAKEGREENENTRY",blogType);
                    Drawable blogPic = iv.getDrawable();

                    JSONObject jsonObject = new JSONObject();

                    URL url = new URL("http://192.168.0.6:8080/GoGreenServer/GreenEntryServlet");
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                    // Put Http parameter labels with value of Name Edit View control
                    jsonObject.put("postedByUserId", userId);
                    jsonObject.put("postType", blogType);
                    jsonObject.put("postMessage", blogPost);
                    jsonObject.put("postImageURL", blogPic);


                    Log.d("JSONinputString", jsonObject.toString());

                    connection.setDoOutput(true);
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                    out.write(jsonObject.toString());
                    out.close();

                    int responseCode = connection.getResponseCode();
                    Log.d("Response Code = ",String.valueOf(responseCode));
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String returnString="";
                    while ((returnString = in.readLine()) != null)
                    {
                        Log.d("ReturnString", returnString);
                    }
                    in.close();
                }catch(Exception e)
                {
                    Log.d("Exception",e.toString());
                }

            }
        }).start();
    }

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
