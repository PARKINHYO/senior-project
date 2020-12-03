package com.example.senierproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class showInfoActivity extends AppCompatActivity {

    // 쉐어드 프리퍼런스 키와 맥주소 출력
    String key = "info";
    EditText name, sex, height, weight, age;

    ArrayList<String> valueList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        LinearLayout backbtn = findViewById(R.id.back_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        name = (EditText)findViewById(R.id.baby_name);
        sex= (EditText)findViewById(R.id.baby_sex);
        height = (EditText)findViewById(R.id.baby_height);
        weight = (EditText)findViewById(R.id.baby_weight);
        age = (EditText)findViewById(R.id.baby_age);

        getStringArrayList(key);
        name.setText(valueList.get(0));
        sex.setText(valueList.get(1));
        height.setText(valueList.get(2));
        weight.setText(valueList.get(3));
        age.setText(valueList.get(4));
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
}
