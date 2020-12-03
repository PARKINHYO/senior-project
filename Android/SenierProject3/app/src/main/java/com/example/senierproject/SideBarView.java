package com.example.senierproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SideBarView extends RelativeLayout implements View.OnClickListener {

    /*
     * 메뉴버튼 클릭 이벤트 리스너
     */
    public EventListener listener;
    public void setEventListener(EventListener l) {
        listener = l;
    }

    /**
     * 메뉴버튼 클릭 이벤트 리스너 인터페이스
     */
    public interface EventListener { // 닫기 버튼 클릭 이벤트
        void btnCancel();
        void btnShow();
        void btnRevise();
        void btnGPS();
        void btnDev();
        void btnKcal();
        void btnMise();
        void btnPost();
        void btnBluetooth();
    }

    public SideBarView(Context context) {
        this(context, null);
        init();
    }

    public SideBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.sidebar, this, true);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        //findViewById(R.id.show_info).setOnClickListener(this);
        findViewById(R.id.revise_info).setOnClickListener(this);
        findViewById(R.id.gps_info).setOnClickListener(this);
        findViewById(R.id.kcal_info).setOnClickListener(this);
        findViewById(R.id.dev_info).setOnClickListener(this);
        findViewById(R.id.mise_info).setOnClickListener(this);
        //findViewById(R.id.post_info).setOnClickListener(this);
        findViewById(R.id.find_bluetooth).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                listener.btnCancel();
                break;
//            case R.id.show_info:
//                listener.btnShow();
//                break;
            case R.id.revise_info:
                listener.btnRevise();
                break;
           case R.id.gps_info:
              listener.btnGPS();
              break;
            case R.id.kcal_info:
                listener.btnKcal();
                break;
            case R.id.dev_info:
                listener.btnDev();
                break;
            case R.id.mise_info:
                listener.btnMise();
                break;
//            case R.id.post_info:
//                listener.btnPost();
//                break;
            case R.id.find_bluetooth:
                listener.btnBluetooth();
                break;
            default:
                break;
        }
    }
}
