package com.example.hw2_litalkhotyakov.modules;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class GameRecord {
    private LatLng location;
    private int score;
    private Date date;
    private String name;

    public GameRecord(LatLng location, int score, Date date, String name) {
        this.location = location;
        this.score = score;
        this.date = date;
        this.name = name;
    }

    public LatLng getLocation() {
        return location;
    }

    public GameRecord setLocation(LatLng location) {
        this.location = location;
        return this;
    }

    public int getScore() {
        return score;
    }

    public GameRecord setScore(int score) {
        this.score = score;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public GameRecord setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getName() {
        return name;
    }

    public GameRecord setName(String name) {
        this.name = name;
        return this;
    }
}
