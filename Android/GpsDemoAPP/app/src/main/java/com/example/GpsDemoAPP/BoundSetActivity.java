package com.example.GpsDemoAPP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.Telephony;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class BoundSetActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private SearchView searchView;
    private String typingAddr;
    private int tmp;
    private View view;
    private  Spinner spinner;
    private  Integer[] item;
    private  int movRadius=100;
    private double setLat=0, setLng=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boundry_set);

        Toolbar toolbar = (Toolbar) findViewById(R.id.boundrySetBar);
        setSupportActionBar(toolbar);
        setTitle("             아이 이동 반경 설정");
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.boundrySetMap);
        mapFragment.getMapAsync(this);

        searchView = (SearchView) findViewById(R.id.setMapSearch);
        view = getCurrentFocus();

        spinner = (Spinner) findViewById(R.id.bdrySpner);
        item = new Integer[]{100, 300, 500, 700, 1000, 1500, 2000};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button setCompBtn = (Button)findViewById(R.id.setCompleteBtn);
        setCompBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("latitude", setLat);
                intent.putExtra("longitude",setLng );
                intent.putExtra("moveRadius", movRadius);
                startActivity(intent);

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                movRadius = item[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                typingAddr = query;
                tmp = 2;
                onMapReady(mMap);
                if (searchView != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        geocoder = new Geocoder(this);
        LatLng sampleLatLng = new LatLng(37.519104, 127.051804);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sampleLatLng, 16));

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
                setLat = markerLat;
                setLng = markerLng;
                onAddMarker(latLng);

            }
        });

        if (tmp == 2) {
            mMap.clear();

            List<Address> addressList = null;
            double searchLat = 0, searchLng = 0;
            try {
                // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                addressList = geocoder.getFromLocationName(typingAddr, 10); // 최대 검색 결과 개수
            } catch (IOException e) {
                e.printStackTrace();
            }

//            System.out.println(addressList.get(0).toString());
//            // 콤마를 기준으로 split
//            String[] splitStr = addressList.get(0).toString().split(",");
//            String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소
//            System.out.println(address);
//
//            String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
//            String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
//            System.out.println(latitude);
//            System.out.println(longitude);

            if (addressList != null) {
                if (addressList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "해당되는 주소 정보는 없습니다!", Toast.LENGTH_SHORT);
                } else {
                    Address addr = addressList.get(0);
                    searchLat = addr.getLatitude();
                    searchLng = addr.getLongitude();

                    // 좌표(위도, 경도) 생성
                    LatLng point = new LatLng(searchLat, searchLng);
                    // 마커 생성
                    MarkerOptions mOptions2 = new MarkerOptions();
                    mOptions2.title("search result");
                    String addr2 = String.format("%s", addr);
                    mOptions2.snippet(addr2);
                    mOptions2.position(point);
                    // 마커 추가
                    mMap.addMarker(mOptions2);
                    // 해당 좌표로 화면 줌

                    setLat = searchLat;
                    setLng = searchLng;

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 16));
                    onAddMarker(point);
                    tmp = 1;
                }
            }
        }
    }

    //마커 , 원추가
    public void onAddMarker(LatLng latLng) {

        // 반경 1KM원
        CircleOptions circle1KM = new CircleOptions().center(latLng) //원점
                .radius(movRadius)      //반지름 단위 : m
                .strokeWidth(0f)  //선너비 0f : 선없음
                .fillColor(Color.parseColor("#880000ff")); //배경색
        //원추가
        mMap.addCircle(circle1KM);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}