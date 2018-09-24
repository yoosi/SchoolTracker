package edu.wgu.cmaxwe3.schooltracker;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.List;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.model.Assessment;
import edu.wgu.cmaxwe3.schooltracker.model.Course;

import static android.content.ContentValues.TAG;
import static edu.wgu.cmaxwe3.schooltracker.ViewAssessmentsActivity.ASSESSMENT_ID;
import static edu.wgu.cmaxwe3.schooltracker.ViewCoursesActivity.COURSE_ID;

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


    @Override
    protected void onHandleIntent(@Nullable Intent workIntent) {

        int counter = 1;


        while (true) {
            System.out.println("PASS: " + counter);
            mManager = getManager();

            db = new DatabaseHelper(getApplicationContext());


            // assessments

            List<Assessment> assessments = db.getAllAssessments();
            System.out.println("Assessments size is: " + assessments.size());
            if (assessments.size() > 0) {
                for (Assessment assessment : assessments) {
                    if (assessment.getGoalDateAlert() == 1) {

                        System.out.println("ASSESSMENT ID: " + assessment.getId() + ", TITLE: " + assessment.getTitle());

                        // Create an explicit intent for an Activity in your app
                        Intent intent = new Intent(this, EditAssessmentActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra(ASSESSMENT_ID, String.valueOf(assessment.getId()));
                        System.out.println("added assessment id of: " + String.valueOf(assessment.getId()));
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                        // Display a notification
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(BackgroundAlertsService.this, getString(R.string.channel_id))
                                .setSmallIcon(R.drawable.wgu_logo)
                                .setContentTitle("Assessment Due Alert")
                                .setContentText(assessment.getTitle() + " is due " + assessment.getGoalDate())
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(assessment.getTitle() + " is due " + assessment.getGoalDate()))
                                .setContentIntent(pendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                        // notificationId is a unique int for each notification that you must define
                        int notificationId = assessment.getId() + 1000;
                        notificationManager.notify(notificationId, mBuilder.build());
                        System.out.println("NOTIFICATION ID: " + notificationId);

                    } else {
                        // alert was not set to on so we disregard
                    }
                }
            }

            // courses
            // start date alerts

            List<Course> courses = db.getAllCourses();
            System.out.println("Courses size is: " + courses.size());
            if (courses.size() > 0) {
                for (Course course : courses) {
                    if (course.getStartAlert() == 1) {
                        System.out.println("COURSE ID: " + course.getId() + ", TITLE: " + course.getTitle());

                        // Create an explicit intent for an Activity in your app
                        Intent intent = new Intent(this, EditCourseActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra(COURSE_ID, String.valueOf(course.getId()));
                        System.out.println("added course id of: " + String.valueOf(course.getId()));
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        // Display a notification
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(BackgroundAlertsService.this, getString(R.string.channel_id))
                                .setSmallIcon(R.drawable.wgu_logo)
                                .setContentTitle("Course Start Alert")
                                .setContentText(course.getTitle() + " starts " + course.getStartDate())
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(course.getTitle() + " starts " + course.getStartDate()))
                                .setContentIntent(pendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                        // notificationId is a unique int for each notification that you must define
                        int notificationId = course.getId() + 2000;
                        notificationManager.notify(notificationId, mBuilder.build());
                        System.out.println("NOTIFICATION ID: " + notificationId);

                    } else {
                        // alert was not set to on so we disregard
                    }
                }
            }


            // end date alerts

            System.out.println("Courses size is: " + courses.size());
            if (courses.size() > 0) {
                for (Course course : courses) {
                    if (course.getEndAlert() == 1) {
                        System.out.println("COURSE ID: " + course.getId() + ", TITLE: " + course.getTitle());

                        // Create an explicit intent for an Activity in your app
                        Intent intent = new Intent(this, EditCourseActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra(COURSE_ID, String.valueOf(course.getId()));
                        System.out.println("added course id of: " + String.valueOf(course.getId()));
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        // Display a notification
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(BackgroundAlertsService.this, getString(R.string.channel_id))
                                .setSmallIcon(R.drawable.wgu_logo)
                                .setContentTitle("Course End Alert")
                                .setContentText(course.getTitle() + " ends " + course.getEndDate())
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(course.getTitle() + " ends" + course.getEndDate()))
                                .setContentIntent(pendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                        // notificationId is a unique int for each notification that you must define
                        int notificationId = course.getId() + 3000;
                        notificationManager.notify(notificationId, mBuilder.build());
                        System.out.println("NOTIFICATION ID: " + notificationId);

                    } else {
                        // alert was not set to on so we disregard
                    }
                }
            }


            // sleep for 10 seconds
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            counter++;
        }

    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }


}