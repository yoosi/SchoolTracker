package edu.wgu.cmaxwe3.schooltracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.List;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.helper.Tools;
import edu.wgu.cmaxwe3.schooltracker.model.Assessment;
import edu.wgu.cmaxwe3.schooltracker.model.Course;
import edu.wgu.cmaxwe3.schooltracker.model.Mentor;
import edu.wgu.cmaxwe3.schooltracker.model.Term;

public class MainActivity extends AppCompatActivity {

    // Database Helper
    DatabaseHelper db;



    public void openAbout(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void openAddTerm(){
        Intent intent = new Intent(this, AddTermActivity.class);
        startActivity(intent);
    }

    public void openAddMentor(){
//        Intent intent = new Intent(this, AddMentorActivity.class);
//        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                System.out.println("YOU PUSHED THE BUTTON");
            }
        });


        // button view mentors
        Button buttonViewMentors = (Button) findViewById(R.id.buttonViewMentors);
        buttonViewMentors.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                System.out.println("pressed mentors");
                openAddMentor();
            }
        });

        // button view assessments
        Button buttonViewAssessments = (Button) findViewById(R.id.buttonViewAssessments);
        buttonViewAssessments.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                System.out.println("pressed view assignments");
            }
        });

        // button view courses
        Button buttonViewCourses = (Button) findViewById(R.id.buttonViewCourses);
        buttonViewCourses.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                System.out.println("pressed view courses");
            }
        });

        // button view terms
        Button buttonViewTerms = (Button) findViewById(R.id.buttonViewTerms);
        buttonViewTerms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                System.out.println("pressed view terms");

                openAddTerm();
            }
        });

        // button view about
        Button buttonViewAbout = (Button) findViewById(R.id.buttonViewAbout);
        buttonViewAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                System.out.println("pressed the view about");
                openAbout();
            }
        });




    }


    private void printSomething() {
        System.out.println("the button was pressed");
    }



    private void testStuff() {

        db = new DatabaseHelper(getApplicationContext());

        // create mentor
        Mentor m = new Mentor();
        m.setName("Jimi Hendrix");
        m.setEmail("j.hendrix@school.edu");
        m.setPhone("2065551234");

        Mentor m2 = new Mentor("Jim Halpert", "206-555-4321", "j.halpert@dundermifflin.com", 0);
        Mentor m3 = new Mentor("Pam Beasley", "206-555-6789", "p.beasley@dundermifflin.com");

        //insert mentor
        long mentor_id = db.createMentor(m);
        long mentor_id2 = db.createMentor(m3);

        Log.d("Mentor Count", String.valueOf(mentor_id));
        Log.d("Mentor Count", String.valueOf(mentor_id2));


        // get mentors from db and print
        List<Mentor> mentors = db.getAllMentors();
        for (Mentor mentor : mentors) {
            System.out.println("Mentor ID: " + mentor.getId());
            System.out.println("Mentor name: " + mentor.getName());
        }

        // create assessment
        Assessment a1 = new Assessment("type1", "midterm exam", "2018-09-01",
                "2018-09-02", 1);


        // insert assessment
        long assessment_id = db.createAssessment(a1);
        Log.d("Assessment Count", String.valueOf(assessment_id));

        // remove assessment


        // get assessments from db and print
        List<Assessment> assessments = db.getAllAssessments();
        for (Assessment assessment : assessments) {
            System.out.println("Assessment ID: " + assessment.getId());
            System.out.println("Assessment title: " + assessment.getTitle());
        }

        // create course
        Course c1 = new Course("intro to programming", "in progress", "2018-09-01", 1,
                "2018-10-31", 1, "fun class!");

        // insert course
        long course_id = db.createCourse(c1);
        Log.d("Course Count", String.valueOf(course_id));


        // get all courses and print
        List<Course> courses = db.getAllCourses();
        for (Course course : courses) {
            System.out.println("Course ID: " + course.getId());
            System.out.println("Course title: " + course.getTitle());
        }

        // create term
        Term t1 = new Term("possibly the first term", "2018-09-01",
                "2018-12-31");

        // insert term
        long term_id = db.createTerm(t1);
        Log.d("Term Count", String.valueOf(term_id));

        // delete a term
//        db.deleteTerm(1);

        // get terms from db and print
        List<Term> terms = db.getAllTerms();
        for (Term term : terms) {
            System.out.println("Term ID: " + term.getId());
            System.out.println("Term title: " + term.getTitle());
        }

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
