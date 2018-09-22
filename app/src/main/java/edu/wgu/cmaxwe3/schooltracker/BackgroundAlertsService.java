package edu.wgu.cmaxwe3.schooltracker;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.text.ParseException;
import java.util.List;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.helper.Tools;
import edu.wgu.cmaxwe3.schooltracker.model.Assessment;

import static android.content.ContentValues.TAG;

public class BackgroundAlertsService extends IntentService {


    private NotificationManager mManager;

    private DatabaseHelper db;
//    private final String CHANNEL_ID = "channel_147029";

    public BackgroundAlertsService() {
        super("BackgroundAlertsThread");
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "onCreate, Thread Name: " + Thread.currentThread().getName());


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy, Thread Name: " + Thread.currentThread().getName());
    }


//    protected void oldOHandleIntent(@Nullable Intent workIntent) {
//        db = new DatabaseHelper(getApplicationContext());
//
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        // infinite loop
//        while (true) {
//
////            System.out.println(" ############ BACKGROUND SERVICE IS RUNNING!!");
////            try {
////                boolean hasPassed = Tools.hasPassed("Mar 09, 2020");
////                if (hasPassed){
////                    System.out.println("###### DATE IS PASSED");
////                } else {
////                    System.out.println("###### DATE HAS NOT PASSED");
////                }
////            } catch (ParseException e) {
////                e.printStackTrace();
////            }
//
//
//
//
//
//
//            // todo implement this better
//            List<Assessment> assessments = db.getAllAssessments();
//            for (Assessment assessment: assessments) {
//                System.out.println("ASSESSMENT: " + assessment.getTitle());
//                if (assessment.getGoalDateAlert() == 1){
//                    try {
//                        System.out.println("GOAL DATE IS: " + assessment.getGoalDateAlert());
//                        if (Tools.hasPassed(assessment.getGoalDate())){
//                            System.out.println("ALERT FOR ASSESSMENT HAS PASSED: " + assessment.getTitle());
//
//
//                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                                    .setSmallIcon(R.drawable.wgu_logo)
//                                    .setContentTitle("Assessment Due Alert")
//                                    .setContentText(assessment.getTitle() + " is due " + assessment.getGoalDate())
//                                    .setStyle(new NotificationCompat.BigTextStyle()
//                                            .bigText(assessment.getTitle() + " is due " + assessment.getGoalDate()))
//                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//
//
//                            createNotificationChannel();
//                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//                            // notificationId is a unique int for each notification that you must define
//                            int notificationId = (int) Math.random();
//                            notificationManager.notify(notificationId, mBuilder.build());
//
//
//
//
//
//
//
//                        } else {
//                            System.out.println("ALERT FOR ASSESSMENT HAS NOT PASSED: " + assessment.getTitle());
//                        }
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//
//
//
//
//
////            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
////                    .setSmallIcon(R.drawable.wgu_logo)
////                    .setContentTitle("My notification")
////                    .setContentText("Much longer text that cannot fit one line...")
////                    .setStyle(new NotificationCompat.BigTextStyle()
////                            .bigText("Much longer text that cannot fit one line..."))
////                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
////
////
////
////            createNotificationChannel();
////            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
////
////            // notificationId is a unique int for each notification that you must define
////            int notificationId = (int) Math.random();
////            notificationManager.notify(notificationId, mBuilder.build());
////
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//            // sleep the thread
//            int counter = 1;
//            int durationInMinutes = 5;
//            int duration = durationInMinutes * 60;
//
//            // dummy long operation:
//            while (counter <= duration) {
//                Log.i(TAG, "Time elapsed: " + counter + " seconds");
//                try {
//                    // sleep for one second
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                counter++;
//            }
//
//
//
//
//        }
//
//
//
//
//    }


    @Override
    protected void onHandleIntent(@Nullable Intent workIntent) {

        int counter = 1;


        while (true) {
            System.out.println("PASS: " + counter);
//        mManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            mManager = getManager();

            db = new DatabaseHelper(getApplicationContext());


            List<Assessment> assessments = db.getAllAssessments();
            System.out.println("Assessments size is: " + assessments.size());
            if (assessments.size() > 0) {
                for (Assessment assessment : assessments) {
                    System.out.println("ASSESSMENT: " + assessment.getTitle());

                    // Display a notification
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(BackgroundAlertsService.this, getString(R.string.channel_id))
                            .setSmallIcon(R.drawable.wgu_logo)
                            .setContentTitle("Assessment Due Alert")
                            .setContentText(assessment.getTitle() + " is due " + assessment.getGoalDate())
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(assessment.getTitle() + " is due " + assessment.getGoalDate()))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                    // notificationId is a unique int for each notification that you must define
                    int notificationId = assessment.getId() + 1000;
                    notificationManager.notify(notificationId, mBuilder.build());
                    System.out.println("NOTIFICATION ID: " + notificationId);
                }
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            counter++;
        }

    }

//    private void createNotificationChannel() {
//
//        //channel name
//        String channelName = "MyChannel";
//        String channelDescription = "Alerts";
//
//
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            CharSequence name = getString(R.string.channel_name);
//            CharSequence name = channelName;
////            String description = getString(R.string.channel_description);
//            String description = channelDescription;
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }


}


//package edu.wgu.cmaxwe3.schooltracker;
//
//import android.app.IntentService;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.content.Intent;
//import android.os.Build;
//import android.support.annotation.Nullable;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.app.NotificationManagerCompat;
//import android.util.Log;
//
//import java.text.ParseException;
//import java.util.List;
//
//import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
//import edu.wgu.cmaxwe3.schooltracker.helper.Tools;
//import edu.wgu.cmaxwe3.schooltracker.model.Assessment;
//
//import static android.content.ContentValues.TAG;
//
//public class BackgroundAlertsService extends IntentService {
//
//    private DatabaseHelper db;
//    private final String CHANNEL_ID = "channel_147029";
//
//    public BackgroundAlertsService(){
//        super("BackgroundAlertsThread");
//    }
//
//
//    @Override
//    public void onCreate(){
//        super.onCreate();
//
//        Log.i(TAG, "onCreate, Thread Name: " + Thread.currentThread().getName());
//    }
//
//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        Log.i(TAG, "onDestroy, Thread Name: " + Thread.currentThread().getName());
//    }
//
//    @Override
//    protected void onHandleIntent(@Nullable Intent workIntent) {
//        db = new DatabaseHelper(getApplicationContext());
//
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        // infinite loop
//        while (true) {
//
////            System.out.println(" ############ BACKGROUND SERVICE IS RUNNING!!");
////            try {
////                boolean hasPassed = Tools.hasPassed("Mar 09, 2020");
////                if (hasPassed){
////                    System.out.println("###### DATE IS PASSED");
////                } else {
////                    System.out.println("###### DATE HAS NOT PASSED");
////                }
////            } catch (ParseException e) {
////                e.printStackTrace();
////            }
//
//
//
//
//
//
//            // todo implement this better
//            List<Assessment> assessments = db.getAllAssessments();
//            for (Assessment assessment: assessments) {
//                System.out.println("ASSESSMENT: " + assessment.getTitle());
//                if (assessment.getGoalDateAlert() == 1){
//                    try {
//                        System.out.println("GOAL DATE IS: " + assessment.getGoalDateAlert());
//                        if (Tools.hasPassed(assessment.getGoalDate())){
//                            System.out.println("ALERT FOR ASSESSMENT HAS PASSED: " + assessment.getTitle());
//
//
//                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                                    .setSmallIcon(R.drawable.wgu_logo)
//                                    .setContentTitle("Assessment Due Alert")
//                                    .setContentText(assessment.getTitle() + " is due " + assessment.getGoalDate())
//                                    .setStyle(new NotificationCompat.BigTextStyle()
//                                            .bigText(assessment.getTitle() + " is due " + assessment.getGoalDate()))
//                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//
//
//                            createNotificationChannel();
//                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//                            // notificationId is a unique int for each notification that you must define
//                            int notificationId = (int) Math.random();
//                            notificationManager.notify(notificationId, mBuilder.build());
//
//
//
//
//
//
//
//                        } else {
//                            System.out.println("ALERT FOR ASSESSMENT HAS NOT PASSED: " + assessment.getTitle());
//                        }
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//
//
//
//
//
////            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
////                    .setSmallIcon(R.drawable.wgu_logo)
////                    .setContentTitle("My notification")
////                    .setContentText("Much longer text that cannot fit one line...")
////                    .setStyle(new NotificationCompat.BigTextStyle()
////                            .bigText("Much longer text that cannot fit one line..."))
////                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
////
////
////
////            createNotificationChannel();
////            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
////
////            // notificationId is a unique int for each notification that you must define
////            int notificationId = (int) Math.random();
////            notificationManager.notify(notificationId, mBuilder.build());
////
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//            // sleep the thread
//            int counter = 1;
//            int durationInMinutes = 5;
//            int duration = durationInMinutes * 60;
//
//            // dummy long operation:
//            while (counter <= duration) {
//                Log.i(TAG, "Time elapsed: " + counter + " seconds");
//                try {
//                    // sleep for one second
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                counter++;
//            }
//
//
//
//
//        }
//
//
//
//
//    }
//
//    private void createNotificationChannel() {
//
//        //channel name
//        String channelName = "MyChannel";
//        String channelDescription = "Alerts";
//
//
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            CharSequence name = getString(R.string.channel_name);
//            CharSequence name = channelName;
////            String description = getString(R.string.channel_description);
//            String description = channelDescription;
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//
//    // optional
//
//
//
//
//
//
//}
//
//
//
//
//
//
//
