package com.example.hw2_litalkhotyakov;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySharedPreferences.initHelper(this);
    }
}
