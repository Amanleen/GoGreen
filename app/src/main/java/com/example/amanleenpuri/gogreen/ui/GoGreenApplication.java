package com.example.amanleenpuri.gogreen.ui;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import util.VolleyAppController;
import ws.remote.GreenRESTInterface;

/**
 * Created by amanleenpuri on 5/1/16.
 */
public class GoGreenApplication extends Application {
    public static final String BASE_URL = "http://10.1.10.15:8080/GoGreenServer_Amrata/";

    private Retrofit mRetrofitService;
    private GreenRESTInterface mGoGreenApiService;

    @Override
    public void onCreate() {
        super.onCreate();
        // Trailing slash is needed
        mRetrofitService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        mGoGreenApiService = mRetrofitService.create(GreenRESTInterface.class);
        VolleyAppController.onApplicationCreate(this);
    }

    public GreenRESTInterface getGoGreenApiService() {
        return mGoGreenApiService;
    }

}
