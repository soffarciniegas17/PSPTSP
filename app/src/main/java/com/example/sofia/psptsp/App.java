package com.example.sofia.psptsp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {


    public static final String CHANNEL= "Example Service Channel";

    @Override
    public void onCreate() {
        super.onCreate();

        createServiceChannel();
    }

    private void createServiceChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL, "CHANNEL PSP", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager= getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }
}
