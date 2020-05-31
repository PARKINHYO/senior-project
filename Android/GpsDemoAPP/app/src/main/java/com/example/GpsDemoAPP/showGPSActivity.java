package com.example.GpsDemoAPP;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.nio.file.Files;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_gps);

        intent = getIntent();

        latList = (ArrayList<String>) intent.getSerializableExtra("latList");
        curLat = latList.get(latList.size() - 1);
        curDLat = Double.parseDouble(curLat);

        lngList = (ArrayList<String>) intent.getSerializableExtra("lngList");
        curLng = lngList.get(lngList.size() - 1);
        curDLng = Double.parseDouble(curLng);
        tmp = 1;

        Button curLoc = (Button) findViewById(R.id.curLoc);
        Button movingRoute = (Button) findViewById(R.id.movingRoute);

        Toolbar toolbar = (Toolbar) findViewById(R.id.locBar);
        setSupportActionBar(toolbar);
        setTitle("             내 아이 위치 조회");
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.locMap);
        mapFragment.getMapAsync(this);

        curLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp = 1;
                mMap.clear();
                onMapReady(mMap);
            }
        });

        movingRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp = 2;
                mMap.clear();
                onMapReady(mMap);
            }
        });

//        safetyRange.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                showCustomDialog();
////            }
////        });
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
                Double markerLat = latLng.latitude; // 위도
                Double markerLng = latLng.longitude; // 경도
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
            double firstLat, firstLng;
            firstLat = Double.parseDouble(latList.get(0));
            firstLng = Double.parseDouble(lngList.get(0));
            LatLng firstLoc = new LatLng(firstLat, firstLng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLoc, 16));

            for (int i = 0; i < latList.size() - 1; i++) {

                double startLat, startLng, endLat, endLng;
                startLat = Double.parseDouble(latList.get(i));
                startLng = Double.parseDouble(lngList.get(i));
                endLat = Double.parseDouble(latList.get(i + 1));
                endLng = Double.parseDouble(lngList.get(i + 1));

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
                mMap.addMarker(markerOptions);

                markerOptions.position(endLoc);
                markerOptions.title((i + 2) + "번째 아이의 위치");
                markerOptions.snippet(listEnd.get(0).getAddressLine(0));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}