package com.example.hw2_litalkhotyakov.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hw2_litalkhotyakov.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private ImageView[] panel_IMG_planes;
    private ImageView[] panel_IMG_hearts;
    private ImageView panel_IMG_left_direction;
    private ImageView panel_IMG_right_direction;
    private View[][] panel_LINL_lines;
    private LinearLayout panel_LINL_lineD;
    private LinearLayout panel_LINL_lineE;
    private ImageView[][] bombs;
    private ImageView[][] coins;
    private TextView panel_LBL_score;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private int[][] values;
    private Timer timer = new Timer();
    private int planeLoc = 0;
    private int score = 0;
    private final int MAX_LIVES = 3;
    private int lives = MAX_LIVES;
    private Boolean isRuning = false;
    private boolean esayGame = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        findViews();
        initGame();
        initValMatrix();
        checkCrashing();
        randomly();
        clickDirection();
        updateUI();
        getLastLocation();




    }

    private void getLastLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED){

                        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {

                                if (location != null){
                                    Double lat = location.getLatitude();
                                    Double longt = location.getLongitude();
//                                    textLocation.setText(lat + " " + longt);
                                }
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("onFailure", e.getMessage());

                            }
                        });

                    }else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                    }
                }



    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
    }



    private void startTimer() {
        isRuning = true;
        timer = new Timer();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Move();
                randomly();
                if (isRuning) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateUI();
                            updateLivesViews();
                            checkCrashing();
                            score++;
                        }
                    });
                }
            }
        }, 0, 1000);
    }

    private void stopTimer() {
        isRuning = false;
        timer.cancel();
    }


    private void Move() {
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                if (values[i][j] > 0) {
                    int val = values[i][j];
                    values[i][j] = 0;
                    if (j == (values[i].length - 1)) {
                        values[i][j] = 0;
                    } else {
                        j++;
                        values[i][j] = val;
                    }
                }
            }
        }
    }


    private void clickDirection() {
        panel_IMG_left_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panel_IMG_planes[planeLoc].setVisibility(View.GONE);
                planeLoc--;
                if (planeLoc < 0) {
                    planeLoc = 0;
                }
                panel_IMG_planes[planeLoc].setVisibility(View.VISIBLE);
            }
        });

        panel_IMG_right_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panel_IMG_planes[planeLoc].setVisibility(View.GONE);
                planeLoc++;
                if (planeLoc > values.length - 1) {
                    planeLoc = values.length - 1;
                }
                panel_IMG_planes[planeLoc].setVisibility(View.VISIBLE);
            }
        });
    }

    private void randomly() {
        final int random = new Random().nextInt(values.length);
        values[random][0] = (new Random().nextInt(5)) ==0 ? 2 : 1;
        if (esayGame){
            values[random][0] = 1;
        }

    }

    private void checkCrashing() {
        int lange = values[0].length - 1;
        if (esayGame){
            lange = 4;
        }
        if (values[planeLoc][lange] == 1) {
            Toast.makeText(this, "You crashed", Toast.LENGTH_SHORT).show();
            vibrate();
            lives--;
        }
        if (values[planeLoc][values[0].length - 1] == 2) {
            score += 10;
        }

        if (lives == 0) {
            stopTimer();
//            startActivity(new Intent(this, EndGameActivity.class));
            finish();
        }
    }

    private void updateUI() {
        panel_LBL_score.setText("" + score);
        for (int i = 0; i < bombs.length; i++) {
            for (int j = 0; j < bombs[i].length; j++) {
                ImageView bomb = bombs[i][j];
                ImageView coin = coins[i][j];
                if (values[i][j] == 0) {
                    bomb.setVisibility(View.GONE);
                    coin.setVisibility(View.GONE);
                } else if (values[i][j] == 1) {
                    bomb.setVisibility(View.VISIBLE);
                    coin.setVisibility(View.GONE);
                } else if (values[i][j] == 2) {
                    coin.setVisibility(View.VISIBLE);
                    bomb.setVisibility(View.GONE);
                }
            }
        }
    }


    private void initValMatrix() {
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                values[i][j] = 0;
            }
        }
    }

    private void findViews() {
        if (esayGame){
            panel_IMG_planes = new ImageView[]{
                    findViewById(R.id.panel_IMG_plane5),
                    findViewById(R.id.panel_IMG_plane6),
                    findViewById(R.id.panel_IMG_plane7)
            };
        }else {
            panel_IMG_planes = new ImageView[]{
                    findViewById(R.id.panel_IMG_plane0),
                    findViewById(R.id.panel_IMG_plane1),
                    findViewById(R.id.panel_IMG_plane2),
                    findViewById(R.id.panel_IMG_plane3),
                    findViewById(R.id.panel_IMG_plane4)
            };
        }


        panel_IMG_left_direction = findViewById(R.id.panel_IMG_left_direction);
        panel_IMG_right_direction = findViewById(R.id.panel_IMG_right_direction);
        panel_IMG_hearts = new ImageView[]{
                findViewById(R.id.panel_IMG_heart1),
                findViewById(R.id.panel_IMG_heart2),
                findViewById(R.id.panel_IMG_heart3)
        };
        bombs = new ImageView[][]{
                {findViewById(R.id.panel_IMG_bombA0), findViewById(R.id.panel_IMG_bombA1), findViewById(R.id.panel_IMG_bombA2), findViewById(R.id.panel_IMG_bombA3), findViewById(R.id.panel_IMG_bombA4), findViewById(R.id.panel_IMG_bombA5), findViewById(R.id.panel_IMG_bombA6), findViewById(R.id.panel_IMG_bombA7)},
                {findViewById(R.id.panel_IMG_bombB0), findViewById(R.id.panel_IMG_bombB1), findViewById(R.id.panel_IMG_bombB2), findViewById(R.id.panel_IMG_bombB3), findViewById(R.id.panel_IMG_bombB4), findViewById(R.id.panel_IMG_bombB5), findViewById(R.id.panel_IMG_bombB6), findViewById(R.id.panel_IMG_bombB7)},
                {findViewById(R.id.panel_IMG_bombC0), findViewById(R.id.panel_IMG_bombC1), findViewById(R.id.panel_IMG_bombC2), findViewById(R.id.panel_IMG_bombC3), findViewById(R.id.panel_IMG_bombC4), findViewById(R.id.panel_IMG_bombC5), findViewById(R.id.panel_IMG_bombC6), findViewById(R.id.panel_IMG_bombC7)},
                {findViewById(R.id.panel_IMG_bombD0), findViewById(R.id.panel_IMG_bombD1), findViewById(R.id.panel_IMG_bombD2), findViewById(R.id.panel_IMG_bombD3), findViewById(R.id.panel_IMG_bombD4), findViewById(R.id.panel_IMG_bombD5), findViewById(R.id.panel_IMG_bombD6), findViewById(R.id.panel_IMG_bombD7)},
                {findViewById(R.id.panel_IMG_bombE0), findViewById(R.id.panel_IMG_bombE1), findViewById(R.id.panel_IMG_bombE2), findViewById(R.id.panel_IMG_bombE3), findViewById(R.id.panel_IMG_bombE4), findViewById(R.id.panel_IMG_bombE5), findViewById(R.id.panel_IMG_bombE6), findViewById(R.id.panel_IMG_bombE7)}
        };
        coins = new ImageView[][]{
                {findViewById(R.id.panel_IMG_coinA0), findViewById(R.id.panel_IMG_coinA1), findViewById(R.id.panel_IMG_coinA2), findViewById(R.id.panel_IMG_coinA3), findViewById(R.id.panel_IMG_coinA4), findViewById(R.id.panel_IMG_coinA5), findViewById(R.id.panel_IMG_coinA6), findViewById(R.id.panel_IMG_coinA7)},
                {findViewById(R.id.panel_IMG_coinB0), findViewById(R.id.panel_IMG_coinB1), findViewById(R.id.panel_IMG_coinB2), findViewById(R.id.panel_IMG_coinB3), findViewById(R.id.panel_IMG_coinB4), findViewById(R.id.panel_IMG_coinB5), findViewById(R.id.panel_IMG_coinB6), findViewById(R.id.panel_IMG_coinB7)},
                {findViewById(R.id.panel_IMG_coinC0), findViewById(R.id.panel_IMG_coinC1), findViewById(R.id.panel_IMG_coinC2), findViewById(R.id.panel_IMG_coinC3), findViewById(R.id.panel_IMG_coinC4), findViewById(R.id.panel_IMG_coinC5), findViewById(R.id.panel_IMG_coinC6), findViewById(R.id.panel_IMG_coinC7)},
                {findViewById(R.id.panel_IMG_coinD0), findViewById(R.id.panel_IMG_coinD1), findViewById(R.id.panel_IMG_coinD2), findViewById(R.id.panel_IMG_coinD3), findViewById(R.id.panel_IMG_coinD4), findViewById(R.id.panel_IMG_coinD5), findViewById(R.id.panel_IMG_coinD6), findViewById(R.id.panel_IMG_coinD7)},
                {findViewById(R.id.panel_IMG_coinE0), findViewById(R.id.panel_IMG_coinE1), findViewById(R.id.panel_IMG_coinE2), findViewById(R.id.panel_IMG_coinE3), findViewById(R.id.panel_IMG_coinE4), findViewById(R.id.panel_IMG_coinE5), findViewById(R.id.panel_IMG_coinE6), findViewById(R.id.panel_IMG_coinE7)}
        };
        values = new int[bombs.length][bombs[0].length];
        panel_LBL_score = findViewById(R.id.panel_LBL_score);

        if (esayGame) {
            panel_LINL_lines = new LinearLayout[][]{
                    {findViewById(R.id.panel_LINL_A1), findViewById(R.id.panel_LINL_A2), findViewById(R.id.panel_LINL_A3), findViewById(R.id.panel_LINL_A4), findViewById(R.id.panel_LINL_A5), findViewById(R.id.panel_LINL_A6), findViewById(R.id.panel_LINL_A7)},
                    {findViewById(R.id.panel_LINL_B1), findViewById(R.id.panel_LINL_B2), findViewById(R.id.panel_LINL_B3), findViewById(R.id.panel_LINL_B4), findViewById(R.id.panel_LINL_B5), findViewById(R.id.panel_LINL_B6), findViewById(R.id.panel_LINL_B7)},
                    {findViewById(R.id.panel_LINL_C1), findViewById(R.id.panel_LINL_C2), findViewById(R.id.panel_LINL_C3), findViewById(R.id.panel_LINL_C4), findViewById(R.id.panel_LINL_C5), findViewById(R.id.panel_LINL_C6), findViewById(R.id.panel_LINL_C7)}
            };
            panel_LINL_lineD = findViewById(R.id.panel_LINL_lineD);
            panel_LINL_lineE = findViewById(R.id.panel_LINL_lineE);
        }

        }


    private void initGame() {
        planeLoc = panel_IMG_planes.length / 2;
        for (ImageView plane : panel_IMG_planes) {
            plane.setVisibility(View.GONE);
        }
        panel_IMG_planes[planeLoc].setVisibility(View.VISIBLE);

        if (esayGame) {
            panel_LINL_lineD.setVisibility(View.GONE);
            panel_LINL_lineE.setVisibility(View.GONE);
            for (int i = 0; i < panel_LINL_lines.length; i++) {
                for (int j = 4; j < panel_LINL_lines[i].length; j++) {
                    panel_LINL_lines[i][j].setVisibility(View.GONE);

                }
            }
        }

    }

    private void updateLivesViews() {
        for (int i = panel_IMG_hearts.length - 1; i >= 0; i--) {
            if ((i + 1) > lives) {
                panel_IMG_hearts[i].setVisibility(View.GONE);
            } else {
                panel_IMG_hearts[i].setVisibility(View.VISIBLE);
            }
        }
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
    }
}
