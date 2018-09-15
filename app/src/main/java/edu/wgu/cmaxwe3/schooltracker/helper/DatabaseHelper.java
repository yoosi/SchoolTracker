package edu.wgu.cmaxwe3.schooltracker.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.wgu.cmaxwe3.schooltracker.model.Assessment;
import edu.wgu.cmaxwe3.schooltracker.model.Course;
import edu.wgu.cmaxwe3.schooltracker.model.Mentor;
import edu.wgu.cmaxwe3.schooltracker.model.Term;

public class DatabaseHelper extends SQLiteOpenHelper {

    // logcat tag
    private static final String LOG = "DatabaseHelper";

    // database version
    private static final int VERSION = 1;

    // database name
    private static final String DATABASE_NAME = "SchoolTracker";

    // table names
    private static final String TABLE_ASSESSMENT = "assessment";
    private static final String TABLE_COURSE = "course";
    private static final String TABLE_MENTOR = "mentor";
    private static final String TABLE_TERM = "term";


//    // common column names
//    private static final String KEY_ID = "id";
//    private static final String KEY_CREATED_AT = "created_at"; // i'm not sure my implementation needs this

    // Assessment table - column names

    // Assessment table - column names
    public static final String KEY_ASSESSMENT_ID = "id";
    public static final String KEY_ASSESSMENT_TYPE = "type";
    public static final String KEY_ASSESSMENT_TITLE = "title";
    public static final String KEY_ASSESSMENT_DUE_DATE = "due_date";
    public static final String KEY_ASSESSMENT_GOAL_DATE = "goal_date";
    public static final String KEY_ASSESSMENT_GOAL_DATE_ALERT = "goal_date_alert";
    public static final String KEY_ASSESSMENT_COURSE_ID = "course_id";

    // course table - column names
    public static final String KEY_COURSE_ID = "id";
    public static final String KEY_COURSE_TITLE = "title";
    public static final String KEY_COURSE_STATUS = "status";
    public static final String KEY_COURSE_START_DATE = "start_date";
    public static final String KEY_COURSE_START_DATE_ALERT = "start_date_alert";
    public static final String KEY_COURSE_END_DATE = "end_date";
    public static final String KEY_COURSE_END_DATE_ALERT = "end_date_alert";
    public static final String KEY_COURSE_NOTES = "notes";
    public static final String KEY_COURSE_TERM_ID = "term_id";
    public static final String KEY_COURSE_MENTOR_ID = "mentor_id";

    // Mentor table - column names
    public static final String KEY_MENTOR_ID = "id";
    public static final String KEY_MENTOR_NAME = "name";
    public static final String KEY_MENTOR_PHONE = "phone";
    public static final String KEY_MENTOR_EMAIL = "email";
    public static final String KEY_MENTOR_COURSE_ID = "course_id";

    // Term table - column names
    public static final String KEY_TERM_ID = "id";
    public static final String KEY_TERM_TITLE = "title";
    public static final String KEY_TERM_START_DATE = "start_date";
    public static final String KEY_TERM_END_DATE = "end_date";


    // SQL to create tables

    // STEP 1
    private static final String CREATE_TABLE_TERM =
            "CREATE TABLE " + TABLE_TERM + " (" +
//                    KEY_TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_TERM_ID + " INTEGER PRIMARY KEY, " +
                    KEY_TERM_TITLE + " TEXT, " +
                    KEY_TERM_START_DATE + " DATETIME, " +
                    KEY_TERM_END_DATE + " DATETIME" +
                    ")";

    // STEP 2
    private static final String CREATE_TABLE_COURSE =
            "CREATE TABLE " + TABLE_COURSE + " (" +
//                    KEY_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_COURSE_ID + " INTEGER PRIMARY KEY, " +
                    KEY_COURSE_TITLE + " TEXT, " +
                    KEY_COURSE_STATUS + " TEXT, " +
                    KEY_COURSE_START_DATE + " DATETIME, " +
                    KEY_COURSE_START_DATE_ALERT + " INTEGER, " +
                    KEY_COURSE_END_DATE + " DATETIME, " +
                    KEY_COURSE_END_DATE_ALERT + " INTEGER, " +
                    KEY_COURSE_NOTES + " TEXT, " +
                    KEY_COURSE_TERM_ID + " INTEGER, " +
                    "FOREIGN KEY (" + KEY_COURSE_TERM_ID + " ) REFERENCES "
                    + TABLE_TERM + "(" + KEY_TERM_ID + ")" +
                    ")";

    // STEP 3
    private static final String CREATE_TABLE_ASSESSMENT =
            "CREATE TABLE " + TABLE_ASSESSMENT + " (" +
//                    KEY_ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_ASSESSMENT_ID + " INTEGER PRIMARY KEY, " +
                    KEY_ASSESSMENT_TYPE + " INTEGER, " +
                    KEY_ASSESSMENT_TITLE + " TEXT, " +
                    KEY_ASSESSMENT_DUE_DATE + " DATETIME, " +
                    KEY_ASSESSMENT_GOAL_DATE + " DATETIME, " +
                    KEY_ASSESSMENT_GOAL_DATE_ALERT + " INTEGER, " +
                    KEY_ASSESSMENT_COURSE_ID + " INTEGER, " +
                    "FOREIGN KEY (" + KEY_ASSESSMENT_COURSE_ID + " ) REFERENCES "
                    + TABLE_COURSE + "(" + KEY_COURSE_ID + ")" +
                    ")";

    // STEP 4
    private static final String CREATE_TABLE_MENTOR =
            "CREATE TABLE " + TABLE_MENTOR + " (" +
//                    KEY_MENTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_MENTOR_ID + " INTEGER PRIMARY KEY, " +
                    KEY_MENTOR_NAME + " TEXT, " +
                    KEY_MENTOR_PHONE + " TEXT, " +
                    KEY_MENTOR_EMAIL + " TEXT, " +
                    KEY_MENTOR_COURSE_ID + " INTEGER, " +
                    "FOREIGN KEY (" + KEY_MENTOR_COURSE_ID + ") REFERENCES "
                    + TABLE_COURSE + "(" + KEY_COURSE_ID + ")" +
                    ")";


    // constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TERM);
        db.execSQL(CREATE_TABLE_COURSE);
        db.execSQL(CREATE_TABLE_ASSESSMENT);
        db.execSQL(CREATE_TABLE_MENTOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop all tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERM);

        // create new tables
        onCreate(db);

    }

    public long createMentor(Mentor mentor) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MENTOR_NAME, mentor.getName());
        values.put(KEY_MENTOR_EMAIL, mentor.getEmail());
        values.put(KEY_MENTOR_PHONE, mentor.getPhone());

        // insert row
        long mentor_id = db.insert(TABLE_MENTOR, null, values);

        Log.d("createMentor", "mentor added at id: " + mentor_id);

        return mentor_id;
    }

    public void deleteMentor(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENTOR, KEY_MENTOR_ID + " = ?",
                new String[]{String.valueOf(id)});

    }


    public void deleteMentor(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENTOR, KEY_MENTOR_ID + " = ?",
                new String[]{String.valueOf(id)});

    }

    public long updateMentor(int id, Mentor mentor) {


        deleteMentor(id);
        Log.d("updateMentor", "mentor removed at id: " + id);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MENTOR_ID, id);
        values.put(KEY_MENTOR_NAME, mentor.getName());
        values.put(KEY_MENTOR_EMAIL, mentor.getEmail());
        values.put(KEY_MENTOR_PHONE, mentor.getPhone());

        // insert row
        long mentor_id = db.insert(TABLE_MENTOR, null, values);

        Log.d("updateMentor", "mentor added at id: " + mentor_id);

        return mentor_id;

    }


    public void deleteAllMentors() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_MENTOR);
    }



    public List<Mentor> getAllMentors() {
        List<Mentor> mentors = new ArrayList<Mentor>();
        String selectQuery = "SELECT  * FROM " + TABLE_MENTOR;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Mentor mentor = new Mentor();
                mentor.setId(c.getInt((c.getColumnIndex(KEY_MENTOR_ID))));
                mentor.setName((c.getString(c.getColumnIndex(KEY_MENTOR_NAME))));
                mentor.setPhone(c.getString(c.getColumnIndex(KEY_MENTOR_PHONE)));
                mentor.setEmail(c.getString(c.getColumnIndex(KEY_MENTOR_EMAIL)));


                // adding to mentors list
                mentors.add(mentor);
            } while (c.moveToNext());
        }

        return mentors;
    }

    public List<Mentor> getAllUnassignedMentors() {
        List<Mentor> mentors = new ArrayList<Mentor>();
        String selectQuery = "SELECT  * FROM " + TABLE_MENTOR + " WHERE " + KEY_MENTOR_COURSE_ID
                + " is null or " + KEY_MENTOR_COURSE_ID + " = ''"
                + " or " + KEY_MENTOR_COURSE_ID + " is 0";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Mentor mentor = new Mentor();
                mentor.setId(c.getInt((c.getColumnIndex(KEY_MENTOR_ID))));
                mentor.setName((c.getString(c.getColumnIndex(KEY_MENTOR_NAME))));
                mentor.setPhone(c.getString(c.getColumnIndex(KEY_MENTOR_PHONE)));
                mentor.setEmail(c.getString(c.getColumnIndex(KEY_MENTOR_EMAIL)));


                // adding to mentors list
                mentors.add(mentor);
            } while (c.moveToNext());
        }

        return mentors;
    }

    public List<Mentor> getAllAvailableMentorsForCourse(long course_id) {
        List<Mentor> mentors = new ArrayList<Mentor>();
        String selectQuery = "SELECT  * FROM " + TABLE_MENTOR + " WHERE " + KEY_MENTOR_COURSE_ID
                + " is null or " + KEY_MENTOR_COURSE_ID + " = " + course_id
                + " or " + KEY_MENTOR_COURSE_ID + " =  0";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Mentor mentor = new Mentor();
                mentor.setId(c.getInt((c.getColumnIndex(KEY_MENTOR_ID))));
                mentor.setName((c.getString(c.getColumnIndex(KEY_MENTOR_NAME))));
                mentor.setPhone(c.getString(c.getColumnIndex(KEY_MENTOR_PHONE)));
                mentor.setEmail(c.getString(c.getColumnIndex(KEY_MENTOR_EMAIL)));


                // adding to mentors list
                mentors.add(mentor);
            } while (c.moveToNext());
        }

        return mentors;
    }


    public Mentor getMentor(long mentor_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_MENTOR + " WHERE "
                + KEY_MENTOR_ID + " = " + mentor_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Mentor mentor = new Mentor();

        mentor.setId(c.getInt(c.getColumnIndex(KEY_MENTOR_ID)));
        mentor.setName(c.getString(c.getColumnIndex(KEY_MENTOR_NAME)));
        mentor.setPhone(c.getString(c.getColumnIndex(KEY_MENTOR_PHONE)));
        mentor.setEmail(c.getString(c.getColumnIndex(KEY_MENTOR_EMAIL)));

        return mentor;
    }


    public Assessment getAssessment(long assessment_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_ASSESSMENT + " WHERE "
                + KEY_ASSESSMENT_ID + " = " + assessment_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        String[] columnNames = c.getColumnNames();
        for (String columnName : columnNames) {
            System.out.println("*** COLUMN NAME: " + columnName);
        }


        Assessment assessment = new Assessment();
        assessment.setId(c.getInt(c.getColumnIndex(KEY_ASSESSMENT_ID)));
        assessment.setTitle(c.getString(c.getColumnIndex(KEY_ASSESSMENT_TITLE)));
        assessment.setType(c.getString(c.getColumnIndex(KEY_ASSESSMENT_TYPE)));
        assessment.setDueDate(c.getString(c.getColumnIndex(KEY_ASSESSMENT_DUE_DATE)));
        assessment.setGoalDate(c.getString(c.getColumnIndex(KEY_ASSESSMENT_GOAL_DATE)));
        assessment.setGoalDateAlert(c.getInt(c.getColumnIndex(KEY_ASSESSMENT_GOAL_DATE_ALERT)));
        assessment.setCourseId(c.getInt(c.getColumnIndex(KEY_ASSESSMENT_COURSE_ID)));

        return assessment;


    }


//    public Assessment getAssessment(long assessment_id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String selectQuery = "SELECT  * FROM " + TABLE_MENTOR + " WHERE "
//                + KEY_ASSESSMENT_ID + " = " + assessment_id;
//
//        Log.e(LOG, selectQuery);
//
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c != null)
//            c.moveToFirst();
//
//        Assessment assessment = new Assessment();
//        assessment.setId(c.getInt((c.getColumnIndex(KEY_ASSESSMENT_ID))));
//        assessment.setType((c.getString(c.getColumnIndex(KEY_ASSESSMENT_TYPE))));
//        assessment.setTitle(c.getString(c.getColumnIndex(KEY_ASSESSMENT_TITLE)));
//        assessment.setDueDate(c.getString(c.getColumnIndex(KEY_ASSESSMENT_DUE_DATE)));
//        assessment.setGoalDate(c.getString(c.getColumnIndex(KEY_ASSESSMENT_COURSE_ID)));
//        assessment.setGoalDateAlert(c.getInt(c.getColumnIndex(KEY_ASSESSMENT_COURSE_ID)));
//        assessment.setCourseId(c.getInt(c.getColumnIndex(KEY_ASSESSMENT_COURSE_ID)));
//
//        return assessment;
//    }


    public long createAssessment(Assessment assessment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ASSESSMENT_TYPE, assessment.getType());
        values.put(KEY_ASSESSMENT_TITLE, assessment.getTitle());
        values.put(KEY_ASSESSMENT_DUE_DATE, assessment.getDueDate());
        values.put(KEY_ASSESSMENT_GOAL_DATE, assessment.getGoalDate());
        values.put(KEY_ASSESSMENT_GOAL_DATE_ALERT, assessment.getGoalDateAlert());
        values.put(KEY_ASSESSMENT_COURSE_ID, assessment.getCourseId());


        // insert row
        long assessment_id = db.insert(TABLE_ASSESSMENT, null, values);

        Log.d("createAssessment", "assessment added at id: " + assessment_id);

        return assessment_id;
    }


    public long createAssessment(Assessment assessment, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ASSESSMENT_ID, id);
        values.put(KEY_ASSESSMENT_TYPE, assessment.getType());
        values.put(KEY_ASSESSMENT_TITLE, assessment.getTitle());
        values.put(KEY_ASSESSMENT_DUE_DATE, assessment.getDueDate());
        values.put(KEY_ASSESSMENT_GOAL_DATE, assessment.getGoalDate());
        values.put(KEY_ASSESSMENT_GOAL_DATE_ALERT, assessment.getGoalDateAlert());
        values.put(KEY_ASSESSMENT_COURSE_ID, assessment.getCourseId());


        // insert row
        long assessment_id = db.insert(TABLE_ASSESSMENT, null, values);

        Log.d("createAssessment", "assessment added at id: " + assessment_id);

        return assessment_id;
    }

    public long updateAssessment(int id, Assessment assessment) {
        deleteAssessment(id);
        Log.d("updateAssessment", "assessment removed at id: " + id);

        long assessmentId = createAssessment(assessment, id);
        Log.d("updateAssessment", "assessment added at id: " + assessmentId);

        return assessmentId;
    }


    public void deleteAssessment(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ASSESSMENT, KEY_ASSESSMENT_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void deleteAllAssessments() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ASSESSMENT);
    }

    public List<Assessment> getAllAssessments() {
        List<Assessment> assessments = new ArrayList<Assessment>();
        String selectQuery = "SELECT * FROM " + TABLE_ASSESSMENT;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Assessment assessment = new Assessment();
                assessment.setId(c.getInt((c.getColumnIndex(KEY_ASSESSMENT_ID))));
                assessment.setType((c.getString(c.getColumnIndex(KEY_ASSESSMENT_TYPE))));
                assessment.setTitle(c.getString(c.getColumnIndex(KEY_ASSESSMENT_TITLE)));
                assessment.setDueDate(c.getString(c.getColumnIndex(KEY_ASSESSMENT_DUE_DATE)));
                assessment.setGoalDate(c.getString(c.getColumnIndex(KEY_ASSESSMENT_COURSE_ID)));
                assessment.setGoalDateAlert(c.getInt(c.getColumnIndex(KEY_ASSESSMENT_COURSE_ID)));
                assessment.setCourseId(c.getInt(c.getColumnIndex(KEY_ASSESSMENT_COURSE_ID)));

                // adding to mentors list
                assessments.add(assessment);
            } while (c.moveToNext());
        }

        System.out.println("******** from within getAllAssessments assessments size is: " + assessments.size());

        return assessments;
    }


    public List<Assessment> getAllUnassignedAssessments() {
        List<Assessment> assessments = new ArrayList<Assessment>();
        String selectQuery = "SELECT * FROM " + TABLE_ASSESSMENT + " WHERE " + KEY_MENTOR_COURSE_ID
                + " is null or " + KEY_MENTOR_COURSE_ID + " = ''"
                + " or " + KEY_MENTOR_COURSE_ID + " is 0";


        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Assessment assessment = new Assessment();
                assessment.setId(c.getInt((c.getColumnIndex(KEY_ASSESSMENT_ID))));
                assessment.setType((c.getString(c.getColumnIndex(KEY_ASSESSMENT_TYPE))));
                assessment.setTitle(c.getString(c.getColumnIndex(KEY_ASSESSMENT_TITLE)));
                assessment.setDueDate(c.getString(c.getColumnIndex(KEY_ASSESSMENT_DUE_DATE)));
                assessment.setGoalDate(c.getString(c.getColumnIndex(KEY_ASSESSMENT_COURSE_ID)));
                assessment.setGoalDateAlert(c.getInt(c.getColumnIndex(KEY_ASSESSMENT_COURSE_ID)));
                assessment.setCourseId(c.getInt(c.getColumnIndex(KEY_ASSESSMENT_COURSE_ID)));

                // adding to mentors list
                assessments.add(assessment);
            } while (c.moveToNext());
        }

        System.out.println("******** from within getAllAssessments assessments size is: " + assessments.size());

        return assessments;
    }

    public List<Assessment> getAllAvailableAssessmentsForCourse(long course_id) {
        List<Assessment> assessments = new ArrayList<Assessment>();
        String selectQuery = "SELECT * FROM " + TABLE_ASSESSMENT + " WHERE " + KEY_MENTOR_COURSE_ID
                + " is null or " + KEY_MENTOR_COURSE_ID + " = " + course_id
                + " or " + KEY_MENTOR_COURSE_ID + " is 0";


        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Assessment assessment = new Assessment();
                assessment.setId(c.getInt((c.getColumnIndex(KEY_ASSESSMENT_ID))));
                assessment.setType((c.getString(c.getColumnIndex(KEY_ASSESSMENT_TYPE))));
                assessment.setTitle(c.getString(c.getColumnIndex(KEY_ASSESSMENT_TITLE)));
                assessment.setDueDate(c.getString(c.getColumnIndex(KEY_ASSESSMENT_DUE_DATE)));
                assessment.setGoalDate(c.getString(c.getColumnIndex(KEY_ASSESSMENT_COURSE_ID)));
                assessment.setGoalDateAlert(c.getInt(c.getColumnIndex(KEY_ASSESSMENT_COURSE_ID)));
                assessment.setCourseId(c.getInt(c.getColumnIndex(KEY_ASSESSMENT_COURSE_ID)));

                // adding to mentors list
                assessments.add(assessment);
            } while (c.moveToNext());
        }

        System.out.println("******** from within getAllAssessments assessments size is: " + assessments.size());

        return assessments;
    }






    public long createCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COURSE_TITLE, course.getTitle());
        values.put(KEY_COURSE_STATUS, course.getStatus());
        values.put(KEY_COURSE_START_DATE, course.getStartDate());
        values.put(KEY_COURSE_START_DATE_ALERT, course.getStartAlert());
        values.put(KEY_COURSE_END_DATE, course.getEndDate());
        values.put(KEY_COURSE_END_DATE_ALERT, course.getEndAlert());
        values.put(KEY_COURSE_NOTES, course.getNotes());
        values.put(KEY_COURSE_TERM_ID, course.getTermId());
        values.put(KEY_COURSE_MENTOR_ID, course.getMentorId());


        // insert row
        long mentor_id = db.insert(TABLE_COURSE, null, values);

        Log.d("createCourse", "course added at id: " + mentor_id);

        return mentor_id;
    }

    public void deleteCourse(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSE, KEY_ASSESSMENT_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.delete(TABLE_MENTOR, KEY_COURSE_ID + " = ?", //todo decide if this is the best way to handle this
                new String[]{String.valueOf(id)});
    }

    public void deleteAllCourses() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_COURSE);
    }




    public Course getCourse(long course_id) {
        SQLiteDatabase db = this.getReadableDatabase();
                String selectQuery = "SELECT  * FROM " + TABLE_COURSE + " WHERE "
                + KEY_COURSE_ID + " = " + course_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Course course = new Course();

        course.setId(c.getInt(c.getColumnIndex(KEY_COURSE_ID)));
        course.setTitle(c.getString(c.getColumnIndex(KEY_COURSE_TITLE)));
        course.setStatus(c.getString(c.getColumnIndex(KEY_COURSE_STATUS)));
        course.setStartDate(c.getString(c.getColumnIndex(KEY_COURSE_START_DATE)));
        course.setStartAlert(c.getInt(c.getColumnIndex(KEY_COURSE_START_DATE_ALERT)));
        course.setEndDate(c.getString(c.getColumnIndex(KEY_COURSE_END_DATE)));
        course.setEndAlert(c.getInt(c.getColumnIndex(KEY_COURSE_END_DATE_ALERT)));
        course.setNotes(c.getString(c.getColumnIndex(KEY_COURSE_NOTES)));
        course.setTermId(c.getInt(c.getColumnIndex(KEY_COURSE_TERM_ID)));
        course.setMentorId(c.getInt(c.getColumnIndex(KEY_COURSE_MENTOR_ID)));

        return course;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<Course>();
        String selectQuery = "SELECT * FROM " + TABLE_COURSE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Course course = new Course();
                course.setId(c.getInt(c.getColumnIndex(KEY_COURSE_ID)));
                course.setTitle(c.getString(c.getColumnIndex(KEY_COURSE_TITLE)));
                course.setStatus(c.getString(c.getColumnIndex(KEY_COURSE_STATUS)));
                course.setStartDate(c.getString(c.getColumnIndex(KEY_COURSE_START_DATE)));
                course.setStartAlert(c.getInt(c.getColumnIndex(KEY_COURSE_START_DATE_ALERT)));
                course.setEndDate(c.getString(c.getColumnIndex(KEY_COURSE_END_DATE)));
                course.setEndAlert(c.getInt(c.getColumnIndex(KEY_COURSE_END_DATE_ALERT)));
                course.setNotes(c.getString(c.getColumnIndex(KEY_COURSE_NOTES)));
                course.setTermId(c.getInt(c.getColumnIndex(KEY_COURSE_TERM_ID)));
                course.setMentorId(c.getInt(c.getColumnIndex(KEY_COURSE_MENTOR_ID)));

                // adding course to list
                courses.add(course);
            } while (c.moveToNext());
        }

        return courses;
    }

        public List<Course> getAllUnassignedCourses() {
        List<Course> courses = new ArrayList<Course>();
        String selectQuery = "SELECT * FROM " + TABLE_COURSE +
                " WHERE " + KEY_COURSE_TERM_ID
                + " is null or " + KEY_COURSE_TERM_ID + " = ''"
                + " or " + KEY_COURSE_TERM_ID + " is 0";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Course course = new Course();
                course.setId(c.getInt(c.getColumnIndex(KEY_COURSE_ID)));
                course.setTitle(c.getString(c.getColumnIndex(KEY_COURSE_TITLE)));
                course.setStatus(c.getString(c.getColumnIndex(KEY_COURSE_STATUS)));
                course.setStartDate(c.getString(c.getColumnIndex(KEY_COURSE_START_DATE)));
                course.setStartAlert(c.getInt(c.getColumnIndex(KEY_COURSE_START_DATE_ALERT)));
                course.setEndDate(c.getString(c.getColumnIndex(KEY_COURSE_END_DATE)));
                course.setEndAlert(c.getInt(c.getColumnIndex(KEY_COURSE_END_DATE_ALERT)));
                course.setNotes(c.getString(c.getColumnIndex(KEY_COURSE_NOTES)));
                course.setTermId(c.getInt(c.getColumnIndex(KEY_COURSE_TERM_ID)));
                course.setMentorId(c.getInt(c.getColumnIndex(KEY_COURSE_MENTOR_ID)));

                // adding course to list
                courses.add(course);
            } while (c.moveToNext());
        }

        return courses;
    }



    public long createTerm(Term term) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TERM_TITLE, term.getTitle());
        values.put(KEY_TERM_START_DATE, term.getStartDate());
        values.put(KEY_TERM_END_DATE, term.getEndDate());

        // insert row
        long term_id = db.insert(TABLE_TERM, null, values);

        Log.d("createTerm", "term added at id: " + term_id);

        return term_id;
    }

    public void deleteTerm(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TERM, KEY_ASSESSMENT_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void deleteAllTerms() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_TERM);
    }

    public List<Term> getAllTerms() {
        List<Term> terms = new ArrayList<Term>();
        String selectQuery = "SELECT * FROM " + TABLE_TERM;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Term term = new Term();
                term.setId(c.getInt(c.getColumnIndex(KEY_TERM_ID)));
                term.setTitle(c.getString(c.getColumnIndex(KEY_TERM_TITLE)));
                term.setStartDate(c.getString(c.getColumnIndex(KEY_TERM_START_DATE)));
                term.setEndDate(c.getString(c.getColumnIndex(KEY_TERM_END_DATE)));

                // adding term to list
                terms.add(term);
            } while (c.moveToNext());
        }

        return terms;
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    // probably not going to use this
    // get datetime
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}