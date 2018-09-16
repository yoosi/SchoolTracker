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
import edu.wgu.cmaxwe3.schooltracker.model.Mentor;

public class SelectCourseMentorActivity extends AppCompatActivity {
    private DatabaseHelper db;
    public static String MENTOR_ID = "MENTOR_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course_mentor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // populate listView
        final List<Mentor> mentors = getMentors();
        ArrayAdapter<Mentor> adapter = new ArrayAdapter<Mentor>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mentors);
        ListView lv = findViewById(R.id.listViewMentors);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Intent intent = new Intent(SelectCourseMentorActivity.this, EditMentorActivity.class);

                Intent intent = new Intent();
                Mentor mentor = mentors.get(position);
                System.out.println("*** PUTTING MENTOR ID: " + mentor.getId());
                intent.putExtra(MENTOR_ID, String.valueOf(mentor.getId()));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        super.onResume();






//todo use the following to pass the mentor id back to the parent activity
//        Intent intent = new Intent();
//        intent.putExtra("editTextValue", "value_here")
//        setResult(RESULT_OK, intent);
//        finish();
    }


    private List<Mentor> getMentors() {
        db = new DatabaseHelper(getApplicationContext());
        return db.getAllMentors();
    }


}
