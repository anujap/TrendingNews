package com.example.anuja.trendingnews;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
