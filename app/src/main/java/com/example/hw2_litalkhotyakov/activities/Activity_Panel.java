package com.example.hw2_litalkhotyakov.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hw2_litalkhotyakov.R;
import com.example.hw2_litalkhotyakov.fragments.ButtonFragment;
import com.example.hw2_litalkhotyakov.fragments.MapsFragment;

public class Activity_Panel extends AppCompatActivity {

    private MapsFragment mapsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);


        getSupportFragmentManager().beginTransaction().add(R.id.panel_FRL_menu, ButtonFragment.getInstance(this)).commit();

//        mapsFragment = new MapsFragment();
//        mapsFragment.setActivity(this);
//        getSupportFragmentManager().beginTransaction().add(R.id.panel_FRL_menu, mapsFragment).commit();
    }
}