package edu.wgu.cmaxwe3.schooltracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.model.Assessment;
import edu.wgu.cmaxwe3.schooltracker.model.Course;

public class SelectTermCoursesActivity extends AppCompatActivity {
    private DatabaseHelper db;
    public static String COURSE_IDS = "COURSE_IDS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_term_courses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // populate listview of courses
        db = new DatabaseHelper(getApplicationContext());
//        final List<Course> courses = db.getAllUnassignedCourses();
        final List<Course> courses = db.getAllCourses();
        System.out.println("********** courses length: " + courses.size());
        final ListView listViewCourses = findViewById(R.id.listViewCourses);

        ArrayAdapter<Course> adapterCourses = new ArrayAdapter<Course>(this,
                android.R.layout.simple_list_item_checked, android.R.id.text1, courses);
        listViewCourses.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewCourses.setAdapter(adapterCourses);


        // done button
        Button doneButton = findViewById(R.id.buttonDone);


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Course> selectedCourses = new ArrayList<>();
                List<String> courseIDsList = new ArrayList<String>();
                int len = listViewCourses.getCount();
                SparseBooleanArray checked = listViewCourses.getCheckedItemPositions();
                for (int i = 0; i < len; i++)
                    if (checked.get(i)) {
                        Course course = courses.get(i);
                        selectedCourses.add(course);
                    }
                for (Course course : selectedCourses) {
                    courseIDsList.add(Integer.toString(course.getId()));
                }

                Intent intent = new Intent();
                intent.putStringArrayListExtra(COURSE_IDS, (ArrayList<String>) courseIDsList);
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }

}
