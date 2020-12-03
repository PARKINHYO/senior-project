package com.example.senierproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LoadingActivity extends Activity {

    ArrayList<String> valueList = new ArrayList<>();
    ArrayList<String> valueList1 = new ArrayList<>();
    String gyro="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        getStringArrayList("gps");
        getStringArrayList1("info");

        // 쉐어드프리퍼런스가 지정된거 없으면 디폴트로 해놓기
        if(valueList.size() == 0) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = prefs.edit();

            JSONArray jsonArray1 = new JSONArray();

            jsonArray1.put(" ");
            jsonArray1.put(" ");
            jsonArray1.put(" ");

            editor.putString("gps", jsonArray1.toString());
            editor.apply();
        }

        if(valueList1.size() == 0) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = prefs.edit();

            JSONArray jsonArray2 = new JSONArray();

            jsonArray2.put("");
            jsonArray2.put("");
            jsonArray2.put("");
            jsonArray2.put("");
            jsonArray2.put("");

            editor.putString("info", jsonArray2.toString());
            editor.apply();
        }

        startLoading();

    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 5500);
    }

    public void getStringArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String json = prefs.getString(key, null);
        if(json != null){
            try{
                JSONArray jArray = new JSONArray(json);
                for(int i = 0; i < jArray.length(); i++){
                    String data = jArray.optString(i);
                    //System.out.println(data);
                    valueList.add(data);
                }
            }catch(JSONException e){
                //Logger.e(tag, e.getMessage());
            }
        }
    }

    public void getStringArrayList1(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String json = prefs.getString(key, null);
        if(json != null){
            try{
                JSONArray jArray = new JSONArray(json);
                for(int i = 0; i < jArray.length(); i++){
                    String data = jArray.optString(i);
                    //System.out.println(data);
                    valueList1.add(data);
                }
            }catch(JSONException e){
                //Logger.e(tag, e.getMessage());
            }
        }
    }


}
