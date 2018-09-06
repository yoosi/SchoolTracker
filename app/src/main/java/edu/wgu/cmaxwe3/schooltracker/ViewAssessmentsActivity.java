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
import edu.wgu.cmaxwe3.schooltracker.model.Assessment;
import edu.wgu.cmaxwe3.schooltracker.model.Mentor;

public class ViewAssessmentsActivity extends AppCompatActivity {

    DatabaseHelper db;
    public static String ASSESSMENT_ID = "ASSESSMENT_ID";

    public void openAddAssessment() {
        Intent intent = new Intent(this, AddAssessmentActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessments);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddAssessment();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onResume() {
        final List<Assessment> assessments = getAssessments();
        ArrayAdapter<Assessment> adapter = new ArrayAdapter<Assessment>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, assessments);


        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ViewAssessmentsActivity.this, EditAssessmentActivity.class);

                Assessment assessment = assessments.get(position);
                System.out.println("*** PUTTING ASSESSMENT ID: " + assessment.getId());
                intent.putExtra(ASSESSMENT_ID, String.valueOf(assessment.getId()));
                startActivity(intent);
            }
        });


        super.onResume();
    }

    private List<Assessment> getAssessments() {
        db = new DatabaseHelper(getApplicationContext());
        return db.getAllAssessments();
    }
}
