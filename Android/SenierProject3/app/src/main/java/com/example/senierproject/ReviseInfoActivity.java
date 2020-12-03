package com.example.senierproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.senierproject.Retrofit.INodeJS;
import com.example.senierproject.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ReviseInfoActivity extends AppCompatActivity {


    EditText edt_childName, edt_sex, edt_childHeight, edt_childWeight, edt_childAge;
    ImageButton upload;

    ArrayList<String> valueList = new ArrayList<>();


    // 쉐어드 프리퍼런스 키와 맥주소 출력
    String key = "info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_info);

        LinearLayout backbtn = findViewById(R.id.back_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        upload= (ImageButton)findViewById(R.id.upload);

        edt_childName = (EditText) findViewById(R.id.baby_name);
        edt_sex = (EditText) findViewById(R.id.baby_sex);
        edt_childHeight = (EditText) findViewById(R.id.baby_height);
        edt_childWeight = (EditText) findViewById(R.id.baby_weight);
        edt_childAge = (EditText) findViewById(R.id.baby_age);

        getStringArrayList(key);
        edt_childName.setText(valueList.get(0));
        edt_sex.setText(valueList.get(1));
        edt_childHeight.setText(valueList.get(2));
        edt_childWeight.setText(valueList.get(3));
        edt_childAge.setText(valueList.get(4));

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();

                JSONArray jsonArray = new JSONArray();

                jsonArray.put(edt_childName.getText().toString());
                jsonArray.put(edt_sex.getText().toString());
                jsonArray.put(edt_childHeight.getText().toString());
                jsonArray.put(edt_childWeight.getText().toString());
                jsonArray.put(edt_childAge.getText().toString());

                editor.putString(key, jsonArray.toString());
                editor.apply();
                finish();
            }
        });
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
