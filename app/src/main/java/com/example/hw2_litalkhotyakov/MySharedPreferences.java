package com.example.hw2_litalkhotyakov;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.hw2_litalkhotyakov.modules.GameRecord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MySharedPreferences {

    private final String SP_FILE = "SP_FILE";
    private static MySharedPreferences me;
    private SharedPreferences sharedPreferences;

    public static MySharedPreferences getMe() {
        return me;
    }

    private MySharedPreferences(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
    }

    public static MySharedPreferences initHelper(Context context) {
        if (me == null) {
            me = new MySharedPreferences(context);
        }
        return me;
    }

    public void putDouble(String KEY, double defValue) {
        putString(KEY, String.valueOf(defValue));
    }

    public double getDouble(String KEY, double defValue) {
        return Double.parseDouble(getString(KEY, String.valueOf(defValue)));
    }

    public int getInt(String KEY, int defValue) {
        return sharedPreferences.getInt(KEY, defValue);
    }

    public void putInt(String KEY, int value) {
        sharedPreferences.edit().putInt(KEY, value).apply();
    }

    public String getString(String KEY, String defValue) {
        return sharedPreferences.getString(KEY, defValue);
    }

    public void putString(String KEY, String value) {
        sharedPreferences.edit().putString(KEY, value).apply();
    }

    private void saveGameRecordsList(List<GameRecord> gameRecords){
        //arraylist convert into String using Gson
        Gson gson = new Gson();
        String data = gson.toJson(gameRecords);
        Log.e("TAG", "json:" + gson);
        putString("key" , data);
    }

    public void saveGameRecord(GameRecord gameRecord){
        List<GameRecord> gameRecords = getGameRecord();
        if (gameRecords == null){
            gameRecords = new ArrayList<>();
        }
        gameRecords.add(gameRecord);
        saveGameRecordsList(gameRecords);
    }

    public List<GameRecord> getGameRecord(){
        //String to ArrayList
        Gson gson = new Gson();
        String data = getString("key" ,"");
        if (data.isEmpty()) {
            return null;
        }
            return gson.fromJson(data, new TypeToken<List<GameRecord>>() {}.getType());

    }
}
