package com.example.GpsDemoAPP;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> latList = new ArrayList<>();
    private ArrayList<String> lngList = new ArrayList<>();
    private Intent bndSetIntent;
    private double setLat, setLng;
    private int movRadius;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        bndSetIntent = getIntent();
        setLat = bndSetIntent.getDoubleExtra("latitude", 0);
        setLng = bndSetIntent.getDoubleExtra("longitude",0);
        movRadius = bndSetIntent.getIntExtra("moveRadius", 100);

        System.out.println(setLat);
        System.out.println(setLng);
        System.out.println(movRadius);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(MainActivity.this,MyService.class);
        startService(intent);

        Button gpsBtn = (Button)findViewById(R.id.gpsBtn);
        //Button activityBtn = (Button)findViewById(R.id.activityBtn);
        Button settingBtn = (Button)findViewById(R.id.settingBtn);

        new JSONTask().execute("http://15.165.199.235:8080/gps_real");

        bndSetIntent = getIntent();
        setLat = bndSetIntent.getDoubleExtra("latitude", 0);
        setLng = bndSetIntent.getDoubleExtra("longitude",0);
        movRadius = bndSetIntent.getIntExtra("moveRadius", 100);

        System.out.println(setLat);
        System.out.println(setLng);
        System.out.println(movRadius);

        gpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showGpsIntent = new Intent(getApplicationContext(),showGPSActivity.class);
                showGpsIntent.putExtra("latList",latList);
                showGpsIntent.putExtra("lngList",lngList);
                startActivity(showGpsIntent);
            }
        });


        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent boundSetActivity = new Intent(getApplicationContext(), BoundSetActivity.class);
                startActivity(boundSetActivity);

            }
        });


    }

    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

                    //입력 스트림 생성
                    InputStream stream = con.getInputStream();

                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                    reader = new BufferedReader(new InputStreamReader(stream));

                    //실제 데이터를 받는곳
                    StringBuffer buffer = new StringBuffer();

                    //line별 스트링을 받기 위한 temp 변수
                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//finally 부분
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                super.onPostExecute(result);
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        latList.add(jsonObject.getString("lat"));
                        lngList.add(jsonObject.getString("lon"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}