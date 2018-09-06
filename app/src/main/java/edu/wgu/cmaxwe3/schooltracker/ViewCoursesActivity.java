package edu.wgu.cmaxwe3.schooltracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.model.Course;
import edu.wgu.cmaxwe3.schooltracker.model.Mentor;

public class ViewCoursesActivity extends AppCompatActivity {
    private DatabaseHelper db;
    public static String COURSE_ID = "COURSE_ID";

    public void openAddCourse() {
        Intent intent = new Intent(this, AddCourseActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                openAddCourse();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {

        // populate listView
        final List<Course> courses = getCourses();
        ArrayAdapter<Course> adapter = new ArrayAdapter<Course>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, courses);
        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ViewCoursesActivity.this, EditCourseActivity.class);

                Course course = courses.get(position);
                System.out.println("*** PUTTING MENTOR ID: " + course.getId());
                intent.putExtra(COURSE_ID, String.valueOf(course.getId()));
                startActivity(intent);
            }
        });

        super.onResume();
    }

    private List<Course> getCourses() {
        db = new DatabaseHelper(getApplicationContext());
        return db.getAllCourses();
    }


}
