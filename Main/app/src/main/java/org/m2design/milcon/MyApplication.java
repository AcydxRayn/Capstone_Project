package org.m2design.milcon;

import android.app.Application;

import com.google.firebase.FirebaseApp;

/**
 * Created by ajmyr on 5/22/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
