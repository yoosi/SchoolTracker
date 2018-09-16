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
import edu.wgu.cmaxwe3.schooltracker.model.Mentor;

public class SelectCourseAssessmentsActivity extends AppCompatActivity {
    private DatabaseHelper db;
    public static String ASSESSMENT_IDS = "ASSESSMENT_IDS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course_assignments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // populate listview of assessments
        db = new DatabaseHelper(getApplicationContext());

//        final List<Assessment> assessments = db.getAllUnassignedAssessments();
        final List<Assessment> assessments = db.getAllAssessments();


        System.out.println("********** assessments length: " + assessments.size());
        final ListView listViewAssessments = findViewById(R.id.listViewAssessments);

        ArrayAdapter<Assessment> adapterAssessments = new ArrayAdapter<Assessment>(this,
                android.R.layout.simple_list_item_checked, android.R.id.text1, assessments);
        listViewAssessments.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewAssessments.setAdapter(adapterAssessments);






        // done button
        Button doneButton = findViewById(R.id.buttonDone);


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Assessment> selectedAssessments = new ArrayList<>();
                List<String> assessmentIDsList = new ArrayList<String>();
                int len = listViewAssessments.getCount();
                SparseBooleanArray checked = listViewAssessments.getCheckedItemPositions();
                for (int i = 0; i < len; i++)
                    if (checked.get(i)) {
                        Assessment assessment = assessments.get(i);
                        selectedAssessments.add(assessment);
                    }
                for (Assessment assessment : selectedAssessments) {
//                    assessment.setCourseId((int) course_id);
//                    db.updateAssessment(assessment.getId(), assessment);
                    assessmentIDsList.add(Integer.toString(assessment.getId()));
                }

//                Integer[] assessmentIDs = new Integer[assessmentIDsList.size()];
//                assessmentIDs = assessmentIDsList.toArray(assessmentIDs);

                Intent intent = new Intent();
//                intent.putIntegerArrayListExtra(ASSESSMENT_IDS, (ArrayList<Integer>) assessmentIDs);
                intent.putStringArrayListExtra(ASSESSMENT_IDS, (ArrayList<String>) assessmentIDsList);
                setResult(RESULT_OK, intent);
                finish();

            }
        });


    }

}
