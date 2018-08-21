package com.example.sofia.psptsp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import static com.example.sofia.psptsp.App.CHANNEL;

public class ServiceApi extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        String artefcto;
        int actividad= intent.getIntExtra("actividad",1);
        Intent intentNotification;

        if(actividad==1){
            intentNotification= new Intent(this, TimeLog.class);
            artefcto="Time Log";
        } else {
            intentNotification= new Intent(this, DefectLog.class);
            artefcto="Defect Log";
        }

        PendingIntent pendingIntent= PendingIntent.getActivity(this, 0, intentNotification, 0);

        @SuppressLint("ResourceAsColor") Notification notification= new NotificationCompat.Builder(this, CHANNEL)
                .setContentTitle(artefcto).setColor(R.color.azul).setContentText("Iniciaste una fase en "+artefcto)
                .setSmallIcon(R.drawable.none_document).setContentIntent(pendingIntent).build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
