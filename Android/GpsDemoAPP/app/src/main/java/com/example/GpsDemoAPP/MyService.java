package com.example.GpsDemoAPP;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    private NotificationManager Notifi_M;
    private ServiceThread thread;
    private Notification Notifi;
    private String channelId = "channel";
    private String channelName = "Channel Name";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            Notifi_M.createNotificationChannel(mChannel);
        }

        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.stopForever();
        thread = null;
        System.out.println("여기여기여기");
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            Intent intent = new Intent(MyService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId);
            builder.setContentTitle("어린이 안심 지킴이") // required
                    .setContentText("아이가 반경을 이탈했어요!")  // required
                    .setDefaults(Notification.DEFAULT_ALL) // 알림, 사운드 진동 설정
                    .setAutoCancel(true) // 알림 터치시 반응 후 삭제
                    .setSound(RingtoneManager
                            .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setSmallIcon(android.R.drawable.btn_star)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.common_google_signin_btn_icon_dark))
                    .setContentIntent(pendingIntent);
            Notifi_M.notify(0, builder.build());

        }
    }

}