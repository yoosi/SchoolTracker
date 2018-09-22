package edu.wgu.cmaxwe3.schooltracker;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.text.ParseException;

import edu.wgu.cmaxwe3.schooltracker.helper.Tools;

import static android.content.ContentValues.TAG;

public class BackgroundAlertsService extends IntentService {

    private final String CHANNEL_ID = "channel_147029";

    public BackgroundAlertsService(){
        super("BackgroundAlertsThread");
    }


    @Override
    public void onCreate(){
        super.onCreate();
        Log.i(TAG, "onCreate, Thread Name: " + Thread.currentThread().getName());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy, Thread Name: " + Thread.currentThread().getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent workIntent) {

        // infinite loop
        while (true) {
            System.out.println(" ############ BACKGROUND SERVICE IS RUNNING!!");
            try {
                boolean hasPassed = Tools.hasPassed("Mar 09, 2020");
                if (hasPassed){
                    System.out.println("###### DATE IS PASSED");
                } else {
                    System.out.println("###### DATE HAS NOT PASSED");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }




            int counter = 1;
            int durationInMinutes = 5;
            int duration = durationInMinutes * 60;

            // dummy long operation:
            while (counter <= duration) {
                Log.i(TAG, "Time elapsed: " + counter + " seconds");
                try {
                    // sleep for one second
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter++;
            }

            // todo implement this better
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.wgu_logo)
                    .setContentTitle("My notification")
                    .setContentText("Much longer text that cannot fit one line...")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Much longer text that cannot fit one line..."))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);



            createNotificationChannel();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            // notificationId is a unique int for each notification that you must define
            int notificationId = (int) Math.random();
            notificationManager.notify(notificationId, mBuilder.build());
        }

    }

    private void createNotificationChannel() {

        //channel name
        String channelName = "MyChannel";
        String channelDescription = "Alerts";


        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
            CharSequence name = channelName;
//            String description = getString(R.string.channel_description);
            String description = channelDescription;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // optional





}
