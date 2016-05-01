package com.example.amanleenpuri.gogreen.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amanleenpuri.gogreen.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.GreenEntry;

/**
 * Created by amanleenpuri on 4/3/16.
 */
public class TimeLineListViewAdapter extends ArrayAdapter<GreenEntry> {


    TimeLineListViewAdapter(Context context, ArrayList<GreenEntry> list){
        super(context, android.R.layout.simple_list_item_1,list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GreenEntry ge = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.timeline_list_item, parent, false);
        }
        TextView userNameOnTimeLinetv = (TextView)convertView.findViewById(R.id.tv_user_name_on_timeline);
        TextView timeAgoOnTimeLinetv = (TextView)convertView.findViewById(R.id.tv_time_ago_on_timeline);
        TextView numberOfStarstv = (TextView)convertView.findViewById(R.id.tv_number_of_stars_on_timeline);
        ImageView userProfilePicOnTimeLineiv = (ImageView)convertView.findViewById(R.id.iv_timeline_image);
        ImageView articleImageiv = (ImageView)convertView.findViewById(R.id.iv_article_on_timeline);
        ImageView followIcon = (ImageView) convertView.findViewById(R.id.become_follower_icon);
        ImageView sharePostIcon = (ImageView) convertView.findViewById(R.id.share_post_icon);

        userNameOnTimeLinetv.setText(ge.getPostByUserName());
        timeAgoOnTimeLinetv.setText(ge.getDatePosted());
        numberOfStarstv.setText(String.valueOf(ge.getNumOfStars()));

        followIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"You are following now", Toast.LENGTH_LONG).show();
            }
        });

        sharePostIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Post has been shared", Toast.LENGTH_LONG).show();
            }
        });

        userProfilePicOnTimeLineiv.setImageResource(0);
        Picasso.with(getContext()).load(ge.getPostImageURL()).placeholder(R.color.colorPrimary).into(userProfilePicOnTimeLineiv);

        articleImageiv.setImageResource(R.mipmap.ic_plant_pic);

        return convertView;
    }
}
