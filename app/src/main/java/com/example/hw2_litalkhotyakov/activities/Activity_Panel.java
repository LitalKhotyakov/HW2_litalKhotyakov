package com.example.hw2_litalkhotyakov.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.hw2_litalkhotyakov.R;
import com.example.hw2_litalkhotyakov.fragments.ButtonFragment;
import com.example.hw2_litalkhotyakov.fragments.MapsFragment;
import com.example.hw2_litalkhotyakov.fragments.ScoreFragment;
import com.example.hw2_litalkhotyakov.fragments.callBacks.ButtonFragmentCallBack;

public class Activity_Panel extends AppCompatActivity  {

    private MapsFragment mapsFragment;
    private  GameActivity gameActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);


        getSupportFragmentManager().beginTransaction().add(R.id.panel_FRL_menu,
                ButtonFragment.getInstance(this,buttonFragmentCallBack)).commit();

    }

    private ButtonFragmentCallBack buttonFragmentCallBack = new ButtonFragmentCallBack() {
        @Override
        public void easyButtonClicked() {
            playGame(true);
        }
        @Override
        public void hardButtonClicked() {
            playGame(false);
        }
        @Override
        public void top10ButtonClicked() {
            getSupportFragmentManager().beginTransaction().add(R.id.panel_FRL_menu, ScoreFragment.getInstance()).commit();
        }
    };

    private void playGame(boolean esayGame) {
        Intent intent = new Intent(Activity_Panel.this, GameActivity.class);
        intent.putExtra("esayGame", esayGame);
        startActivity(intent);
    }


}