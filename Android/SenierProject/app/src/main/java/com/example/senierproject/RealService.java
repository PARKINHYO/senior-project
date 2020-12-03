package com.example.senierproject;

import android.app.AlarmManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RealService extends Service {

    public static final double R = 6372.8; // In kilometers

    private Thread mainThread, gyroThread;
    public static Intent serviceIntent = null;
    private ArrayList<String> latList = new ArrayList<>();
    private ArrayList<String> lngList = new ArrayList<>();
    private String gyro = "";
    private String curLat, curLng;

    ArrayList<String> valueList = new ArrayList<>();

    // 쉐어드 프리퍼런스 키와 맥주소 출력
    String key = "gps";
    String zonelat, zonelng, distance;

    public RealService() { }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        serviceIntent = intent;
        //showToast(getApplication(), "Start Service");

        // QQQ: 두번 이상 호출되지 않도록 조치해야 할 것 같다.
        Intent clsIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, clsIntent, 0);

        new JSONTask2().execute("http://52.78.141.155:8081/gyro");
        if(gyro!="") {
            sendGyro();
        }

        NotificationCompat.Builder clsBuilder;
        if( Build.VERSION.SDK_INT >= 26 )
        {
            String CHANNEL_ID = "channel_id";
            NotificationChannel clsChannel = new NotificationChannel( CHANNEL_ID, "서비스 앱", NotificationManager.IMPORTANCE_DEFAULT );
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel( clsChannel );

            clsBuilder = new NotificationCompat.Builder(this, CHANNEL_ID );

            mainThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean run = true;
                    while (run) {
                        try {
                            Thread.sleep(1000 * 10 * 1); // 10초

                            getStringArrayList(key);
                            zonelat = valueList.get(0);
                            zonelng = valueList.get(1);
                            distance = valueList.get(2);

                            System.out.println(zonelat + " / " + zonelng + " / " + distance);

                            Date date = new Date();
                            new JSONTask().execute("http://52.78.141.155:8081/gps");
                            String test = "test";

                            if (latList.size() > 0 && lngList.size() > 0) {
                                curLat = latList.get(latList.size() - 1);
                                curLng = lngList.get(lngList.size() - 1);
                                sendGPS();

                                if(!distance.equals(" "))
                                    System.out.println("Distance : " + 1000 * haversine(Double.parseDouble(curLat), Double.parseDouble(curLng), Double.parseDouble(zonelat), Double.parseDouble(zonelng)) + "m");
                                //System.out.println("Distance : " + haversine(45.7597, 4.8422, 48.8567, 2.3508));
                                // 구한 거리가 설정한 거리보다 크거나 같으면 알림 보내기
                                if(!distance.equals(" "))
                                    if(1000 * haversine(Double.parseDouble(curLat), Double.parseDouble(curLng), Double.parseDouble(zonelat), Double.parseDouble(zonelng)) >= Double.parseDouble(distance))
                                        sendNotification();
                            }
                        } catch (InterruptedException e) {
                            run = false;
                            e.printStackTrace();
                        }
                    }
                }
            });

            gyroThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    SimpleDateFormat sdf = new SimpleDateFormat("aa hh:mm");
                    boolean run = true;
                    while (run) {
                        try {
                            Thread.sleep(1000 * 5 * 1); // 5 초
                            Date date = new Date();
                            new JSONTask2().execute("http://52.78.141.155:8081/gyro");
                            if(gyro!="") {
                                sendGyro();
                            }
                        } catch (InterruptedException e) {
                            run = false;
                            e.printStackTrace();
                        }
                    }
                }
            });

        }
        else
        {
            clsBuilder = new NotificationCompat.Builder(this );
        }

        // QQQ: notification 에 보여줄 타이틀, 내용을 수정한다.
        clsBuilder.setSmallIcon( com.example.senierproject.R.drawable.icon)
                .setContentTitle( "아이의 위치를 감시 중이에요 :) " )
                .setContentText( "오늘 아이는 얼마나 활동했는지 확인해주세요!" )
                .setContentIntent( pendingIntent );

        // foreground 서비스로 실행한다.
        startForeground( 1, clsBuilder.build() );

        // QQQ: 쓰레드 등을 실행하여서 서비스에 적합한 로직을 구현한다.

        mainThread.start();
        gyroThread.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        serviceIntent = null;
        setAlarmTimer();
        Thread.currentThread().interrupt();

        if (mainThread != null) {
            mainThread.interrupt();
            mainThread = null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void showToast(final Application application, final String msg) {
        Handler h = new Handler(application.getMainLooper());
        h.post(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(application, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    // 거리 구하는 함수
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    // 쉐어드 프리퍼런스 갖고오는 함수
    public void getStringArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
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

    protected void setAlarmTimer() {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.SECOND, 1);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), sender);
    }

    private void sendNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = "fcm_default_channel";//getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(com.example.senierproject.R.mipmap.ic_launcher_round)
                        .setContentTitle("아이가 지정 반경을 벗어났어요!")
                        .setContentText("알림 탭을 눌러 어서 아이의 위치를 확인하세요! :(")
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setPriority(Notification.DEFAULT_ALL)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendGPS() {
        //Log.d("messageService", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        intent.putExtra("message",  curLat + "/" + curLng);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendGyro() {
        Intent intent = new Intent("gyro");
        intent.putExtra("message_gyro", gyro);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
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
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if (reader != null) {
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

    public class JSONTask2 extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
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
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if (reader != null) {
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
                        gyro=jsonObject.getString("count");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}