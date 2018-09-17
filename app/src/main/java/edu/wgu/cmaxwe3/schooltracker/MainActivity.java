package edu.wgu.cmaxwe3.schooltracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.model.Assessment;
import edu.wgu.cmaxwe3.schooltracker.model.Course;
import edu.wgu.cmaxwe3.schooltracker.model.Mentor;
import edu.wgu.cmaxwe3.schooltracker.model.Term;

public class MainActivity extends AppCompatActivity {

    // Database Helper
    DatabaseHelper db;


    private void nukeEverything() {
        db = new DatabaseHelper(getApplicationContext());
        db.deleteAllAssessments();
        db.deleteAllCourses();
        db.deleteAllMentors();
        db.deleteAllTerms();
    }

    public void openAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void openViewMentors() {
        Intent intent = new Intent(this, ViewMentorsActivity.class);
        startActivity(intent);
    }

    public void openViewAssessments() {
        Intent intent = new Intent(this, ViewAssessmentsActivity.class);
        startActivity(intent);
    }

    public void openViewCourses() {
        Intent intent = new Intent(this, ViewCoursesActivity.class);
        startActivity(intent);
    }

    public void openViewTerms() {
        Intent intent = new Intent(this, ViewTermsActivity.class);
        startActivity(intent);
    }

    private void generateSampleData() {
        db = new DatabaseHelper(getApplicationContext());


        Mentor mentor1 = new Mentor("Jim Halpert", "206-555-1234", "j.halpert@dundermifflin.co");
        Mentor mentor2 = new Mentor("Pam Beasley", "206-555-1212", "p.beasley@dundermifflin.co");
        Mentor mentor3 = new Mentor("Michael Scott", "206-555-9876", "m.scott@dundermifflin.co");
        Mentor mentor4 = new Mentor("Dwight Schrute", "206-555-4567", "d.schrute@dundermifflin.co");

        List<Mentor> mentors = new ArrayList<>();
        mentors.add(mentor1);
        mentors.add(mentor2);
        mentors.add(mentor3);
        mentors.add(mentor4);

        for (Mentor mentor : mentors) {
            db.createMentor(mentor);
        }

        Term term1 = new Term("First Term", "Jan 1, 2018", "Jun 30, 2018");
        Term term2 = new Term("Second Term", "Jul 1, 2018", "Dec 31, 2018");
        Term term3 = new Term("Third Term", "Jan 1, 2019", "Jun 30, 2019");
        Term term4 = new Term("Second Term", "Jul 1, 2019", "Dec 31, 2019");

        List<Term> terms = new ArrayList<>();
        terms.add(term1);
        terms.add(term2);
        terms.add(term3);
        terms.add(term4);

        for (Term term : terms) {
            db.createTerm(term);
        }


//    public Course(String title, String status, String startDate, int startAlert,
//        String endDate, int endAlert, String notes, int termId) {

        Course course1 = new Course("Intro to IT", "completed", "Jan 1, 2018", 0, "Feb 25, 2018", 0, "This class was very fun", 1, 1);
        Course course2 = new Course("Intro to Programming", "completed", "Jul 1, 2018", 0, "Aug 16, 2018", 0, "I learned a lot in this class", 2, 2);
        Course course3 = new Course("Intro to Data Mgmt", "completed", "Feb 1, 2019", 0, "Mar 9, 2019", 0, "lots of database stuff in this class\n this part is on an new line", 3);
        Course course4 = new Course("Android App Dev", "in progress", "Jul 1, 2019", 1, "Dec 31, 2019", 0, "remember to coment your code!", 4);

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);

        for (Course course : courses) {
            db.createCourse(course);
        }

        Assessment assessment1 = new Assessment("OBJECTIVE", "Intro to IT Midterm", "Feb 1, 2018", "Jan 25, 2018", 0, 1);
        Assessment assessment2 = new Assessment("PERFORMANCE", "Make Inventory App", "Jul 25, 2018", "Jul 20, 2018", 0, 2);
        Assessment assessment3 = new Assessment("OBJECTIVE", "Data Midterm Exam", "Feb 19, 2019", "Feb 15, 2019", 0, 3);
        Assessment assessment4 = new Assessment("PERFORMANCE", "Code Android App", "Sep 30, 2019", "Sep 15, 2019", 1, 4);

        List<Assessment> assessments = new ArrayList<>();
        assessments.add(assessment1);
        assessments.add(assessment2);
        assessments.add(assessment3);
        assessments.add(assessment4);


        for (Assessment assessment : assessments) {
            db.createAssessment(assessment);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        nukeEverything();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // button view mentors
        Button buttonViewMentors = findViewById(R.id.buttonViewMentors);
        buttonViewMentors.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                System.out.println("pressed mentors");
                openViewMentors();
            }
        });

        // button view assessments
        Button buttonViewAssessments = findViewById(R.id.buttonViewAssessments);
        buttonViewAssessments.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                System.out.println("pressed view assignments");
                openViewAssessments();
            }
        });

        // button view courses
        Button buttonViewCourses = findViewById(R.id.buttonViewCourses);
        buttonViewCourses.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                System.out.println("pressed view courses");
                openViewCourses();
            }
        });

        // button view terms
        Button buttonViewTerms = findViewById(R.id.buttonViewTerms);
        buttonViewTerms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                System.out.println("pressed view terms");
                openViewTerms();
            }
        });

        // button generate sample data
        Button buttonGenerateSampleData = findViewById(R.id.buttonGenerateSampleData);
        buttonGenerateSampleData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // Do something in response to button click
                Snackbar.make(view, "Sample Data Generated", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                generateSampleData();
            }

        });

        // button delete all data
        Button buttonNuke = findViewById(R.id.buttonNuke);
        buttonNuke.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Do something in response to button click
                Snackbar.make(view, "All Data Deleted", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                nukeEverything();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.about:
                System.out.println("*** you clicked about");
                openAbout();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
