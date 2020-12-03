package com.example.senierproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class PostActivity extends Activity {

    private EditText comment;
    private String category="";

    ImageButton category1;
    ImageButton category2;
    ImageButton category3;
    ImageButton category4;
    ImageButton category5;
    ImageButton category6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        category1 = findViewById(R.id.category1);
        category2 = findViewById(R.id.category2);
        category3 = findViewById(R.id.category3);
        category4 = findViewById(R.id.category4);
        category5 = findViewById(R.id.category5);
        category6 = findViewById(R.id.category6);

        LinearLayout backbtn = findViewById(R.id.back_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onClickCategory(View v){
        switch (v.getId()){
            case R.id.category1:
                category1.setBackgroundResource(R.drawable.bicycle_choice);
                category2.setBackgroundResource(R.drawable.bus);
                category3.setBackgroundResource(R.drawable.car);
                category4.setBackgroundResource(R.drawable.drinkwater);
                category5.setBackgroundResource(R.drawable.dustouting);
                category6.setBackgroundResource(R.drawable.plant);
                category="걷거나 자전거";
                break;
            case R.id.category2:
                category1.setBackgroundResource(R.drawable.bicycle);
                category2.setBackgroundResource(R.drawable.bux_choice);
                category3.setBackgroundResource(R.drawable.car);
                category4.setBackgroundResource(R.drawable.drinkwater);
                category5.setBackgroundResource(R.drawable.dustouting);
                category6.setBackgroundResource(R.drawable.plant);
                category="대중교통 이용";
                break;
            case R.id.category3:
                category1.setBackgroundResource(R.drawable.bicycle);
                category2.setBackgroundResource(R.drawable.bus);
                category3.setBackgroundResource(R.drawable.car_choice);
                category4.setBackgroundResource(R.drawable.drinkwater);
                category5.setBackgroundResource(R.drawable.dustouting);
                category6.setBackgroundResource(R.drawable.plant);
                category="부모님과 함께 등원";
                break;
            case R.id.category4:
                category1.setBackgroundResource(R.drawable.bicycle);
                category2.setBackgroundResource(R.drawable.bus);
                category3.setBackgroundResource(R.drawable.car);
                category4.setBackgroundResource(R.drawable.water_choice);
                category5.setBackgroundResource(R.drawable.dustouting);
                category6.setBackgroundResource(R.drawable.plant);
                category="물 하루 1L 마시기";
                break;
            case R.id.category5:
                category1.setBackgroundResource(R.drawable.bicycle);
                category2.setBackgroundResource(R.drawable.bus);
                category3.setBackgroundResource(R.drawable.car);
                category4.setBackgroundResource(R.drawable.drinkwater);
                category5.setBackgroundResource(R.drawable.dustouting_choice);
                category6.setBackgroundResource(R.drawable.plant);
                category="손과 발을 잘 씻기";
                break;
            case R.id.category6:
                category1.setBackgroundResource(R.drawable.bicycle);
                category2.setBackgroundResource(R.drawable.bus);
                category3.setBackgroundResource(R.drawable.car);
                category4.setBackgroundResource(R.drawable.drinkwater);
                category5.setBackgroundResource(R.drawable.dustouting);
                category6.setBackgroundResource(R.drawable.plant_choice);
                category="아이의 꽃에 물 주기";
                break;
        }
    }
}
