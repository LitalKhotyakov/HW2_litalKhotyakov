package com.example.hw2_litalkhotyakov.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.hw2_litalkhotyakov.R;
import com.example.hw2_litalkhotyakov.fragments.ButtonFragment;
import com.example.hw2_litalkhotyakov.fragments.MapsFragment;
import com.example.hw2_litalkhotyakov.fragments.callBacks.ButtonFragmentCallBack;

public class Activity_Panel extends AppCompatActivity  {

    private MapsFragment mapsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);


        getSupportFragmentManager().beginTransaction().add(R.id.panel_FRL_menu, ButtonFragment.getInstance(this,buttonFragmentCallBack)).commit();

//        mapsFragment = new MapsFragment();
//        mapsFragment.setActivity(this);
//        getSupportFragmentManager().beginTransaction().add(R.id.panel_FRL_menu, mapsFragment).commit();
    }

    private ButtonFragmentCallBack buttonFragmentCallBack = new ButtonFragmentCallBack() {
        @Override
        public void easyButtonClicked() {
            Log.d("lital", "easyButtonClicked ");
        }

        @Override
        public void hardButtonClicked() {
            Log.d("lital", "hardButtonClicked ");
        }
        @Override
        public void top10ButtonClicked() {
            Log.d("lital", "top10ButtonClicked ");
            getSupportFragmentManager().beginTransaction().add(R.id.panel_FRL_menu,MapsFragment.getInstance(Activity_Panel.this)).commit();
        }
    };


}