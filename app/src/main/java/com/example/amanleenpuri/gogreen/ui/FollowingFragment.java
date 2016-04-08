package com.example.amanleenpuri.gogreen.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.amanleenpuri.gogreen.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



/**
 * Created by Tejal.
 */
public class FollowingFragment  extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private String pkgName;
    private ArrayList<Person> followData;

    public static FollowingFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FollowingFragment fragment = new FollowingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        pkgName = getContext().getPackageName();
        getFollowData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.following_page, container, false);
        ListView followListView= (ListView)view.findViewById(R.id.lv_following);
        followListView.setAdapter(new FollowListViewAdapter(getContext(), followData));
        return view;
    }

    private void getFollowData(){
        followData = new ArrayList<Person>();
        if(mPage==1){
            followData.add(new Person("Amanleen", R.mipmap.profilepic));
            followData.add(new Person("Amrata", R.mipmap.profilepic));
        }
        else if(mPage==2){
            followData.add(new Person("Sandra Bullock", R.mipmap.profilepic));
            followData.add(new Person("Johnny Depp", R.mipmap.profilepic));
            followData.add(new Person("George Clooney", R.mipmap.profilepic));
        }
    }


    private class Person{
        String name;
        int imageId;

        protected Person(){}

        protected Person(String n, int img){
            name=n;
            imageId=img;
        }
    }

    private class FollowListViewAdapter extends ArrayAdapter<Person> {

        FollowListViewAdapter(Context context, ArrayList<Person> list){
            super(context, android.R.layout.simple_list_item_1,list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Person p = getItem(position);
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.following_item, parent, false);
            }
            TextView userNameFollowList = (TextView)convertView.findViewById(R.id.username_follow);
            ImageView userProfilePicFollowList = (ImageView)convertView.findViewById(R.id.user_image_follow);

            userNameFollowList.setText(p.name);
            userProfilePicFollowList.setImageResource(p.imageId);
            //Picasso.with(getContext()).load(p.getImageUrl()).placeholder(R.color.colorPrimary).into(userProfilePicFollowList);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TimelineActivity.class);
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }
}
