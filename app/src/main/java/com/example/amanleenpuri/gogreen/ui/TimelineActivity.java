package com.example.amanleenpuri.gogreen.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amanleenpuri.gogreen.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.ProxyUser;
import model.GreenEntry;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.UtilNotify;
import ws.remote.GreenRESTInterface;

public class TimelineActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private  int mNotificationsCount = 0;
    ArrayList<model.Notification> noteData;
    ArrayList<GreenEntry> qaData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new FetchCountTask().execute();

        ArrayList<GreenEntry> greenEntryArrayList = new ArrayList<>(5);
        greenEntryArrayList = populateList();
        System.out.println("********* green list=" + greenEntryArrayList.size() + "" + greenEntryArrayList.toString());
        ListView timelinelv = (ListView) findViewById(R.id.list);
        timelinelv.setAdapter(new TimeLineListViewAdapter(getBaseContext(), greenEntryArrayList));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                Intent i = new Intent(getApplicationContext(), BlogTagAskActivity.class);
                startActivity(i);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private ArrayList<GreenEntry> populateList() {
        int countOfEnteries = 5;
        String[] names = {"Tejal", "Amrata", "Aman", "Shah", "Kasture", "Puri", "Bob"};
        String[] dateTime = {"1d ago", "2d ago", "3d ago", "5d ago", "7d ago"};
        ArrayList<GreenEntry> gl = new ArrayList<GreenEntry>();
        for (int i = 0; i < countOfEnteries; i++) {
            GreenEntry ge = new GreenEntry();
            ge.setPostByUserName(names[i]);
            ge.setDatePosted(dateTime[i]);
            ge.setNumOfStars(i);
            gl.add(ge);
            System.out.println("******** i=" + i);
        }
        return gl;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);

        MenuItem item = menu.findItem(R.id.action_notifications);
        LayerDrawable icon = (LayerDrawable) item.getIcon();

        // Update LayerDrawable's BadgeDrawable
        UtilNotify.setBadgeCount(this, icon, mNotificationsCount);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_search) {
            Intent intent = new Intent(this, SearchResultsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_notifications) {
            updateNotificationsBadge(0);
            noteData=new ArrayList<model.Notification>();
            GreenRESTInterface greenRESTInterface = ((GoGreenApplication)getApplication()).getGoGreenApiService();
            Call<List<model.Notification>> getNs = greenRESTInterface.getAllNotifications();
            getNs.enqueue(new Callback<List<model.Notification>>() {

                List<model.Notification> arrN=new ArrayList<model.Notification>();
                @Override
                public void onResponse(Call<List<model.Notification>> call, Response<List<model.Notification>> response) {
                    if (response.isSuccessful()) {

                        arrN = response.body();
                        for(int i=0;i<arrN.size();i++){

                            noteData.add(arrN.get(i));

                        }
                        Intent intent = new Intent(TimelineActivity.this, NotificationActivity.class);
                        Bundle extras = new Bundle();
                        extras.putSerializable("NOTIFS",noteData);
                        intent.putExtras(extras);
                        startActivity(intent);
                    } else {
                        Log.e("Signup", "Error in response " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<model.Notification>> call, Throwable t) {
                    Log.e("NOTIFICATIONS", "Failure to fetch Notifications", t);
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateNotificationsBadge(int count) {
        mNotificationsCount = count;

        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
    }

    class FetchCountTask extends AsyncTask<Void, Void, Integer> {


        @Override
        protected Integer doInBackground(Void... params) {

            int i = CreateEventActivity.count;
            return i;
        }

        @Override
        public void onPostExecute(Integer count) {
            updateNotificationsBadge(count);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        ProxyUser pUser = ProxyUser.getInstance();
        String userName = pUser.getUsername(getApplicationContext());
        int userId = pUser.getUserId(getApplicationContext());
        System.out.println("@@@@@@@ userId="+userId);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.edit_profile) {
            GreenRESTInterface greenRESTInterface = ((GoGreenApplication)getApplication()).getGoGreenApiService();
            //TODO: REMOVE HARD CODING USER ID
            Call<User> getUserDetailsCall = greenRESTInterface.getUserDetails(userId);
            getUserDetailsCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User responseUser = response.body();
                        Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                        i.putExtra("USER_DETAILS_OBJECT", responseUser);
                        startActivity(i);
                    } else {
                        Log.e("Timeline", "Error in response " + response.errorBody());
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("Signup", "Failure to create user", t);
                }
            });


        } else if (id == R.id.nav_following) {
            Intent i = new Intent(getApplicationContext(), FollowingActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_mywall) {
            Intent i = new Intent(getApplicationContext(), TimelineActivity.class);
            startActivity(i);

        } else if (id == R.id.event_creation) {
            Intent i = new Intent(getApplicationContext(), CreateEventActivity.class);
            startActivity(i);

        } else if (id == R.id.qa_forum) {
            qaData = new ArrayList<GreenEntry>();

            GreenRESTInterface greenRESTInterface = ((GoGreenApplication)getApplication()).getGoGreenApiService();
            Call<List<GreenEntry>> getAllQuestions = greenRESTInterface.getQuestions(2);
            getAllQuestions.enqueue(new Callback<List<GreenEntry>>() {

                List<GreenEntry> arrGE=new ArrayList<GreenEntry>();
                @Override
                public void onResponse(Call<List<GreenEntry>> call, Response<List<GreenEntry>> response) {
                    if (response.isSuccessful()) {

                        arrGE = response.body();
                        for(int i=0;i<arrGE.size();i++){
                            qaData.add(arrGE.get(i));

                        }

                       
                        Intent i = new Intent(getApplicationContext(), QuestionForumActivity.class);
                        Bundle extras = new Bundle();
                        extras.putSerializable("Q_DATA",qaData);
                        //extras.putSerializable("QA_HASH",qaHash);
                        i.putExtras(extras);
                        startActivity(i);
                    } else {
                        Log.e("Signup", "Error in response " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<GreenEntry>> call, Throwable t) {
                    Log.e("QA_Forum", "Failure to fetch Questions", t);
                }
            });

        } else if (id == R.id.activate_notification) {
            // define sound URI, the sound to be played when there's a notification
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), soundUri);
            r.play();

            // intent triggered, you can add other intent for other actions
            Intent intent = new Intent(TimelineActivity.this, NotificationActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(TimelineActivity.this, 0, intent, 0);

            // this is it, we'll build the notification!
            // in the addAction method, if you don't want any icon, just set the first param to 0
            NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_cast_light, "Previous", pIntent).build();
            Notification mNotification = new Notification.Builder(this)

                    .setContentTitle("New Post!")
                    .setContentText("There is an event for you on GoGreen!")
                    .setSmallIcon(R.drawable.ic_cast_light)
                    .setContentIntent(pIntent)
                    .setSound(soundUri)
                    //.addAction(action)
                    .build();


            mNotification.defaults |= Notification.DEFAULT_VIBRATE;
            //use the above default or set custom valuse as below
            long[] vibrate = {0, 200, 100, 200};
            mNotification.vibrate = vibrate;

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            // If you want to hide the notification after it was selected, do the code below
            // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(0, mNotification);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}