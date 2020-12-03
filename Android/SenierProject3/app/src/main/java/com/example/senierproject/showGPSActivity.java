package com.example.senierproject;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class showGPSActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Address> list = new ArrayList<>(50);
    private List<Address> listStart = new ArrayList<>(50);
    private List<Address> listEnd = new ArrayList<>(50);
    private Intent intent;
    private ArrayList<String> latList, lngList;
    private String curLat, curLng;
    private double curDLat, curDLng;
    private LatLng curLoc = new LatLng(0, 0);
    private int tmp;
    private LatLng startLoc = new LatLng(0, 0);
    private LatLng endLoc = new LatLng(0, 0);
    private List<Polyline> polylines = new ArrayList<>();

    double startLat, startLng, endLat, endLng;

    Double markerLat;
    Double markerLng;

    int radi;
    CircleOptions circle1KM;

    int flag = 0;
    int token = 0;

    // 쉐어드 프리퍼런스 키와 맥주소 출력
    String key = "gps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_gps);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver( mMessageReceiver, new IntentFilter("custom-event-name"));
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        Geocoder geocoder = new Geocoder(this);
        MarkerOptions markerOptions = new MarkerOptions();
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("마커 좌표");
                markerLat = latLng.latitude; // 위도
                markerLng = latLng.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.snippet(markerLat.toString() + ", " + markerLng.toString());
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(markerLat, markerLng));
                // 마커(핀) 추가
                mMap.addMarker(mOptions);
            }
        });

        if (tmp == 1) {

            curLoc = new LatLng(curDLat, curDLng);
            System.out.println(curDLat + ", " + curDLng);
            try {
                list = geocoder.getFromLocation(curDLat, curDLng, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            markerOptions.position(curLoc);
            markerOptions.title("아이의 현재위치는");
            markerOptions.snippet(list.get(0).getAddressLine(0));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLoc, 16));

        } else if (tmp == 2) {
            double lastLat, lastLng;
            lastLat = Double.parseDouble(latList.get(latList.size()-1));    // 마지막 위치
            lastLng = Double.parseDouble(lngList.get(lngList.size()-1));    // 마지막 위치
            LatLng firstLoc = new LatLng(lastLat, lastLng);                 // 처음 위치
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLoc, 16));

            for (int i = 0; i < latList.size() - 1; i++) {

                if((i) <= latList.size() - 1)
                {
                    if(flag == 0)
                    {
                        startLat = Double.parseDouble(latList.get(i ));
                        startLng = Double.parseDouble(lngList.get(i ));
                        flag = 1;
                    }
                    else
                    {
                        startLat = endLat;
                        startLng = endLng;
                    }
                }
                if(((i*token) + 1) <= latList.size() - 1)
                {
                    endLat = Double.parseDouble(latList.get((i) + 1));
                    endLng = Double.parseDouble(lngList.get((i) + 1));
                }
                else
                {
                    flag = 0;
                }

                try {
                    listStart = geocoder.getFromLocation(startLat, startLng, 500000);
                    listEnd = geocoder.getFromLocation(endLat, endLng, 500000);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                startLoc = new LatLng(startLat, startLng);
                endLoc = new LatLng(endLat, endLng);

                markerOptions.position(startLoc);
                markerOptions.title((i + 1) + "번째 아이의 위치");
                markerOptions.snippet(listStart.get(0).getAddressLine(0));
                if(i != 0)
                    markerOptions.visible(false);
                mMap.addMarker(markerOptions);

                markerOptions.position(endLoc);
                markerOptions.title((i + 2) + "번째 아이의 위치");
                markerOptions.snippet(listEnd.get(0).getAddressLine(0));
                markerOptions.visible(false);
                mMap.addMarker(markerOptions);

                drawPath(startLoc, endLoc);
            }
        }
    }

    void drawPath(LatLng startLoc, LatLng endLoc) {

        PolylineOptions options = new PolylineOptions();
        options.color(Color.BLUE);
        options.width(15);
        options.add(startLoc);
        options.add(endLoc);
        polylines.add(mMap.addPolyline(options));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");

            String curLat = message.substring(0, message.lastIndexOf("/"));
            String curLng = message.substring(message.lastIndexOf("/") + 1,message.length() -1);

            Log.d("curLat: ", curLat);
            Log.d("curLng: ", curLng);

            if(curLat != "" && curLng!="") {
                latList.add(curLat);
                lngList.add(curLng);
                curDLat = Double.parseDouble(curLat);
                curDLng = Double.parseDouble(curLng);

                //Log.d("receiver", "Got message: " + curLat + "," + curLng);
                //Log.d("receiver2", "cur: " + curDLat + "," + curDLng);
                //Log.d("receiver2", "List: " + latList + "," + lngList);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        LinearLayout backbtn = findViewById(R.id.back_btn);
        intent = getIntent();
        latList = (ArrayList<String>) intent.getSerializableExtra("latList");
        //curLat = "36.666666";
        curLat = latList.get(latList.size() - 1);
        curDLat = Double.parseDouble(curLat);

        lngList = (ArrayList<String>) intent.getSerializableExtra("lngList");
        //curLng = "126.666666";
        curLng = lngList.get(lngList.size() - 1);
        curDLng = Double.parseDouble(curLng);

        tmp = 1;

        Button curLoc = (Button) findViewById(R.id.curLoc);
        Button movingRoute = (Button) findViewById(R.id.movingRoute);
        Button babyzone = (Button)findViewById(R.id.baby_zone);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.locMap);
        mapFragment.getMapAsync(this);

        curLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp = 1;
                mMap.clear();
                onMapReady(mMap);
                mMap.addCircle(circle1KM);
            }
        });

        movingRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp = 2;
                mMap.clear();
                onMapReady(mMap);
                mMap.addCircle(circle1KM);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        babyzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(markerLat.toString().equals("") || markerLng.toString().equals("")){
                    Toast.makeText(getApplicationContext(), "지정할 위치를 선택한 후 설정해주세요!" , Toast.LENGTH_LONG).show();
                }
                else {
                    set_zone();
                }
            }
        });
    }

    void set_zone()
    {
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("5m");
        ListItems.add("50m");
        ListItems.add("100m");
        ListItems.add("200m");
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        final List SelectedItems  = new ArrayList();
        int defaultItem = 0;
        SelectedItems.add(defaultItem);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("현재 선택한 지점에서 몇m 까지 설정할까요?");
        builder.setSingleChoiceItems(items, defaultItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SelectedItems.clear();
                        SelectedItems.add(which);
                    }
                });
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String msg="";

                        if (!SelectedItems.isEmpty()) {
                            int index = (int) SelectedItems.get(0);
                            msg = ListItems.get(index);
                        }

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();

                        JSONArray jsonArray = new JSONArray();

                        jsonArray.put(markerLat.toString());
                        jsonArray.put(markerLng.toString());

                        Log.d("lat", markerLat.toString());
                        Log.d("lng", markerLng.toString());

                        if(msg.equals("5m")){
                            jsonArray.put("5");
                            radi = 5;
                            Log.d("distance", "5");
                        }
                        else if(msg.equals("50m")){
                            jsonArray.put("50");
                            radi = 50;
                            Log.d("distance", "50");
                        }
                        else if(msg.equals("100m")){
                            jsonArray.put("100");
                            radi = 100;
                            Log.d("distance", "100");
                        }
                        else if(msg.equals("200m")){
                            jsonArray.put("200");
                            radi = 200;
                            Log.d("distance", "200");
                        }

                        editor.putString(key, jsonArray.toString());
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), RealService.class);

                        LatLng position = new LatLng(Double.parseDouble(markerLat.toString()) , Double.parseDouble(markerLng.toString()));

                        circle1KM = new CircleOptions().center(position) //원점
                                .radius(radi)      //반지름 단위 : m
                                .strokeWidth(0f)  //선너비 0f : 선없음
                                .fillColor(Color.parseColor("#880000ff")); //배경색

                        mMap.addCircle(circle1KM);

                        stopService(intent);
                        //startService(intent);

                        Toast.makeText(getApplicationContext(), "선택한 지점에서 "+ msg + "까지 설정하였습니다." , Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}