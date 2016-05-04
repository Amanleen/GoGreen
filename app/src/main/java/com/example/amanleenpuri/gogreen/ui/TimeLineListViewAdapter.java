package com.example.amanleenpuri.gogreen.ui;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.example.amanleenpuri.gogreen.R;
import model.GreenEntry;
import util.ImageHelper;
import util.TimeAgoHelper;

/**
 * Created by amanleenpuri on 4/3/16.
 */
public class TimeLineListViewAdapter extends ArrayAdapter<GreenEntry> {

    private int userId=0;
    Context context;

    TimeLineListViewAdapter(Context context, GreenEntry[] list, int userId){
        super(context, android.R.layout.simple_list_item_1,list);
        this.context = context;
        this.userId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GreenEntry ge = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.timeline_list_item, parent, false);
        }
        TextView userNameOnTimeLinetv = (TextView)convertView.findViewById(R.id.tv_user_name_on_timeline);
        TextView timeAgoOnTimeLinetv = (TextView)convertView.findViewById(R.id.tv_time_ago_on_timeline);
        final TextView numberOfStarstv = (TextView)convertView.findViewById(R.id.tv_number_of_stars_on_timeline);
        ImageView userProfilePicOnTimeLineiv = (ImageView)convertView.findViewById(R.id.iv_timeline_image);
        ImageView articleImageiv = (ImageView)convertView.findViewById(R.id.iv_article_on_timeline);
        ToggleButton followIcon = (ToggleButton) convertView.findViewById(R.id.become_follower_icon);
        final ToggleButton sharePostIcon = (ToggleButton) convertView.findViewById(R.id.share_post_icon);
        ToggleButton starIcon = (ToggleButton) convertView.findViewById(R.id.star_icon);
        ExpandableTextView postMessage = (ExpandableTextView)convertView.findViewById(R.id.tv_postMsg);

        if(ge.getPostMessage()!=null) {
            postMessage.setText(ge.getPostMessage());
        }
        if(ge.getPostImageURL()!=null) {
            articleImageiv.setImageBitmap(ImageHelper.getStringToBitMap(ge.getPostImageURL()));
        }

        if(ge.getUserImage()!=null){
            userProfilePicOnTimeLineiv.setImageBitmap(ImageHelper.getStringToBitMap(ge.getUserImage()));
        }
        timeAgoOnTimeLinetv.setText(TimeAgoHelper.getTimeAgoForInputString(ge.getDatePosted()));

        //TODO: ADD CONDITION TO SET START ICON STATE
//        starIcon.setChecked(true);
        userNameOnTimeLinetv.setText(ge.getPostByUserName());

        numberOfStarstv.setText(String.valueOf(ge.getNumOfStars()));
        starIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int count = Integer.valueOf(numberOfStarstv.getText().toString());
                if (isChecked) {
                    // The toggle is enabled
                    count++;
                    numberOfStarstv.setText(String.valueOf(count));
                } else {
                    // The toggle is disabled
                    count--;
                    numberOfStarstv.setText(String.valueOf(count));
                }
            }
        });

        final View finalConvertView = convertView;

        if(userId==ge.getPostedByUserId()){
            followIcon.setVisibility(View.INVISIBLE);
        }else{
            followIcon.setVisibility(View.VISIBLE);
            followIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // The toggle is enabled
                        Toast toast= Toast.makeText(finalConvertView.getContext(),
                                "You are now following "+ge.getPostByUserName(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    } else {
                        // The toggle is disabled
                        Toast toast= Toast.makeText(finalConvertView.getContext(),
                                "Unfollowing "+ge.getPostByUserName(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }
                }
            });
        }

        sharePostIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Toast toast= Toast.makeText(finalConvertView.getContext(),
                            "You Shared "+ge.getPostByUserName()+" Post", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                } else {
                    sharePostIcon.setChecked(true);
                    //TODO: TEST ALL CASES SHADY CODE
                    // The toggle is disabled

                }
            }
        });
        return convertView;
    }

//    private void callBlogIntent(GreenEntry ge) {
//        Intent i = new Intent(context, BlogActivity.class);
//        i.putExtra("GREEN_ENTRY", ge);
//        context.startActivity(i);
//    }
}
