package com.example.senierproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;

import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.Direction;
import at.grabner.circleprogress.TextMode;
import im.dacer.androidcharts.LineView;

public class showKcalActivity extends AppCompatActivity {

    CircleProgressView mCircleView1, mCircleView2;
    private LineView lineView;
    int count;
    int kcal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_kcal);
        LocalBroadcastManager.getInstance(this).registerReceiver( gyroMessageReceiver, new IntentFilter("gyro"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        count=intent.getIntExtra("count",1);
        kcal=intent.getIntExtra("kcal",1);

        LinearLayout back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        init1(); // 걸음 수 프로그레스 바
        init2(); // 활동량 프로그레스 바

        TextView today_walk = (TextView)findViewById(R.id.today_walk);
        TextView today_kcal = (TextView)findViewById(R.id.today_kcal);

        today_walk.setText("오늘 "+ count + " 걸음");
        //Log.d("onCreate count",String.valueOf(count));
        today_kcal.setText("오늘 "+ kcal + " kcal");
        //Log.d("onCreate kcal",String.valueOf(kcal));

        lineView = (LineView) findViewById(R.id.line_view);

        // lable
        ArrayList<String> hour = new ArrayList<String>(7);
        // 2 data sets
        ArrayList<Integer> dataList_2_5 = new ArrayList<>();    // 활동량
        ArrayList<Integer> dataList_1_0 = new ArrayList<>();    // 걸음 수

        // 데이터 추가
        hour.add("5 / 25 ~ 5 / 31"); hour.add("월요일"); hour.add("화요일"); hour.add("수요일"); hour.add("목요일"); hour.add("금요일"); hour.add("토요일"); hour.add("일요일");
        dataList_1_0.add(0); dataList_1_0.add(1372); dataList_1_0.add(4832); dataList_1_0.add(6323); dataList_1_0.add(4932); dataList_1_0.add(3202); dataList_1_0.add(2423); dataList_1_0.add(4946);
        dataList_2_5.add(0); dataList_2_5.add(111); dataList_2_5.add(363); dataList_2_5.add(5753); dataList_2_5.add(3221); dataList_2_5.add(2193); dataList_2_5.add(1623); dataList_2_5.add(3646);

        // put data sets into datalist
        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        dataLists.add(dataList_2_5);
        dataLists.add(dataList_1_0);

        // draw line graph
        lineView.setDrawDotLine(true);
        lineView.setShowPopup(LineView.SHOW_POPUPS_All);
        lineView.setColorArray(new int[]{
                Color.parseColor("#2980b9"), Color.parseColor("#1abc9c")
        });
        lineView.setBottomTextList(hour);
        lineView.setDataList(dataLists);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init1() {
        mCircleView1 = (CircleProgressView)findViewById(R.id.circleView1);

        //value setting
        mCircleView1.setMaxValue(10000);
        mCircleView1.setValue(0);
        mCircleView1.setValueAnimated(24);

        //growing/rotating counter-clockwise
        mCircleView1.setDirection(Direction.CCW); // Direction import 해야 함

        //show unit
        mCircleView1.setUnit("%");
        mCircleView1.setUnitVisible(false); // true or false로 쓰면 된다.

        //text sizes
        mCircleView1.setTextSize(50); // text size set, auto text size off
        mCircleView1.setUnitSize(40); // if i set the text size i also have to set the unit size
        mCircleView1.setAutoTextSize(true); // enable auto text size, previous values are overwritten - 위에 쓸 필요 x
        //if you want the calculated text sizes to be bigger/smaller you can do so via
        mCircleView1.setUnitScale(0.9f);
        mCircleView1.setTextScale(0.9f);

        //color
        //you can use a gradient - 1개부터 4개까지 가능!
        mCircleView1.setBarColor(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.accent));

        //colors of text and unit can be set via
        mCircleView1.setTextColor(Color.RED);
        mCircleView1.setTextColor(Color.BLUE);
        //or to use the same color as in the gradient
        mCircleView1.setTextColorAuto(true); //previous set values are ignored - 역시 true의 경우엔 쓸 필요 x

        //text mode
        mCircleView1.setText("걸음 수"); //shows the given text in the circle view
        mCircleView1.setTextMode(TextMode.TEXT); // Set text mode to text to show text

        //in the following text modes, the text is ignored
        mCircleView1.setTextMode(TextMode.VALUE); // Shows the current value
        mCircleView1.setTextMode(TextMode.PERCENT); // Shows current percent of the current value from the max value

        //spinning - 스핀 모드 안쓰면 안넣어도 될 내용!!!
        mCircleView1.spin(); // start spinning
        mCircleView1.stopSpinning(); // stops spinning. Spinner gets shorter until it disappears.
        mCircleView1.setValueAnimated(count); // stops spinning. Spinner spins until on top. Then fills to set value.
    }

    private void init2() {
        mCircleView2 = (CircleProgressView)findViewById(R.id.circleView2);

        //value setting
        mCircleView2.setMaxValue(120);
        mCircleView2.setValue(0);
        mCircleView2.setValueAnimated(24);

        //growing/rotating counter-clockwise
        mCircleView2.setDirection(Direction.CCW); // Direction import 해야 함

        //show unit
        mCircleView2.setUnit("%");
        mCircleView2.setUnitVisible(false); // true or false로 쓰면 된다.

        //text sizes
        mCircleView2.setTextSize(50); // text size set, auto text size off
        mCircleView2.setUnitSize(40); // if i set the text size i also have to set the unit size
        mCircleView2.setAutoTextSize(true); // enable auto text size, previous values are overwritten - 위에 쓸 필요 x
        //if you want the calculated text sizes to be bigger/smaller you can do so via
        mCircleView2.setUnitScale(0.9f);
        mCircleView2.setTextScale(0.9f);

        //color
        //you can use a gradient - 1개부터 4개까지 가능!
        mCircleView2.setBarColor(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.accent));

        //colors of text and unit can be set via
        mCircleView2.setTextColor(Color.RED);
        mCircleView2.setTextColor(Color.BLUE);
        //or to use the same color as in the gradient
        mCircleView2.setTextColorAuto(true); //previous set values are ignored - 역시 true의 경우엔 쓸 필요 x

        //text mode
        mCircleView2.setText("걸음 수"); //shows the given text in the circle view
        mCircleView2.setTextMode(TextMode.TEXT); // Set text mode to text to show text

        //in the following text modes, the text is ignored
        mCircleView2.setTextMode(TextMode.VALUE); // Shows the current value
        mCircleView2.setTextMode(TextMode.PERCENT); // Shows current percent of the current value from the max value

        //spinning - 스핀 모드 안쓰면 안넣어도 될 내용!!!
        mCircleView2.spin(); // start spinning
        mCircleView2.stopSpinning(); // stops spinning. Spinner gets shorter until it disappears.
        mCircleView2.setValueAnimated(kcal); // stops spinning. Spinner spins until on top. Then fills to set value.
    }

    private BroadcastReceiver gyroMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message_gyro");
            String gyro = message;

            count=Integer.parseInt(gyro);
            //cal = (int)((count * 1.144)/1000 * 120);
            kcal = count * 12;
            //https://www.clien.net/service/board/lecture/6589936
            //Log.d("KcalActivity gyro값",gyro);
            //Log.d("KcalActivity count값",String.valueOf(count));
            //Log.d("KcalActivity kcal값",String.valueOf(kcal));
        }
    };
}
