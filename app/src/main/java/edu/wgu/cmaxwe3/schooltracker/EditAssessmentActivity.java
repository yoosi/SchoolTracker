package edu.wgu.cmaxwe3.schooltracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.util.List;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.model.Assessment;

public class EditAssessmentActivity extends AppCompatActivity {
    DatabaseHelper db;
    String assessmentID;

//    EditText titleInput = findViewById(R.id.editTextTitle);

//    EditText dueDateInputYear = findViewById(R.id.editTextDueDateYear);
//    EditText dueDateInputMonth = findViewById(R.id.editTextDueDateMonth);
//    EditText dueDateInputDay = findViewById(R.id.editTextDueDateDay);

//    EditText goalDateInputYear = findViewById(R.id.editTextGoalDateYear);
//    EditText goalDateInputMonth = findViewById(R.id.editTextGoalDateMonth);
//    EditText goalDateInputDay = findViewById(R.id.editTextGoalDateDay);

//    ToggleButton goalDateAlertInput = findViewById(R.id.toggleButtonGoalDateAlert);

//    Switch typeInput = findViewById(R.id.switchType);


    private String getAssessmentType() {
        Switch typeInput = findViewById(R.id.switchType);
        if (typeInput.isChecked()) {
            return "PERFORMANCE";
        } else {
            return "OBJECTIVE";
        }
    }

    private String getAssessmentTitle() {
        EditText titleInput = findViewById(R.id.editTextTitle);
        return titleInput.getText().toString();
    }

    private String getDueDate() {
        EditText dueDateInputYear = findViewById(R.id.editTextDueDateYear);
        EditText dueDateInputMonth = findViewById(R.id.editTextDueDateMonth);
        EditText dueDateInputDay = findViewById(R.id.editTextDueDateDay);
        return dueDateInputYear + "-" + dueDateInputMonth + "-" + dueDateInputDay;
    }

    private String getGoalDate() {
        EditText goalDateInputYear = findViewById(R.id.editTextGoalDateYear);
        EditText goalDateInputMonth = findViewById(R.id.editTextGoalDateMonth);
        EditText goalDateInputDay = findViewById(R.id.editTextGoalDateDay);
        return goalDateInputYear + "-" + goalDateInputMonth + "-" + goalDateInputDay;
    }

    private int getGoalDateAlert() {
        ToggleButton goalDateAlertInput = findViewById(R.id.toggleButtonGoalDateAlert);
        if (goalDateAlertInput.isChecked()) {
            return 1;
        } else {
            return 0;
        }
    }

    private void setAssessmentType(String type) {
        Switch typeInput = findViewById(R.id.switchType);
        if (type.equals("PERFORMANCE")) {
            typeInput.setChecked(true);
        } else {
            typeInput.setChecked(false);
        }
    }

    private void setAssessmentTitle(String title) {
        EditText titleInput = findViewById(R.id.editTextTitle);
        titleInput.setText(title);
    }

    private void setDueDate(String year, String month, String day) {
        EditText dueDateInputYear = findViewById(R.id.editTextDueDateYear);
        EditText dueDateInputMonth = findViewById(R.id.editTextDueDateMonth);
        EditText dueDateInputDay = findViewById(R.id.editTextDueDateDay);
        dueDateInputYear.setText(year);
        dueDateInputMonth.setText(month);
        dueDateInputDay.setText(day);
    }

    private void setGoalDate(String year, String month, String day) {
        EditText goalDateInputYear = findViewById(R.id.editTextGoalDateYear);
        EditText goalDateInputMonth = findViewById(R.id.editTextGoalDateMonth);
        EditText goalDateInputDay = findViewById(R.id.editTextGoalDateDay);
        goalDateInputYear.setText(year);
        goalDateInputMonth.setText(month);
        goalDateInputDay.setText(day);
    }

    private void setGoalDateAlert(int alert) {
        ToggleButton goalDateAlertInput = findViewById(R.id.toggleButtonGoalDateAlert);
        if (alert == 1) {
            goalDateAlertInput.setChecked(true);
        } else {
            goalDateAlertInput.setChecked(false);
        }
    }


    private Assessment getAssessment() {

        Assessment assessment = new Assessment(getAssessmentType(), getAssessmentTitle(),
                getDueDate(), getGoalDate(), getGoalDateAlert());

        return assessment;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assessmentID = getIntent().getStringExtra(ViewAssessmentsActivity.ASSESSMENT_ID);

        db = new DatabaseHelper(getApplicationContext());

        System.out.println("**** ASSESSMENT ID: " + assessmentID);
        List<Assessment> assessmentList = db.getAllAssessments();
        for (Assessment assessment: assessmentList
             ) {
            System.out.println("*** ASSESSMENT ID: " + assessment.getId());
            System.out.println("*** ASSESSMENT TITLE: " + assessment.getTitle());
        }

        Assessment assessment = db.getAssessment(Integer.valueOf(assessmentID));
        System.out.println(assessment.getTitle());
        Switch typeInput = findViewById(R.id.switchType);
        EditText titleInput = findViewById(R.id.editTextTitle);
        EditText dueDateYearInput = findViewById(R.id.editTextDueDateYear);
        EditText dueDateMonthInput = findViewById(R.id.editTextDueDateMonth);
        EditText dueDateDayInput = findViewById(R.id.editTextDueDateDay);
        EditText goalDateYearInput = findViewById(R.id.editTextGoalDateYear);
        EditText goalDateMonthInput = findViewById(R.id.editTextGoalDateMonth);
        EditText goalDateDayInput = findViewById(R.id.editTextGoalDateDay);




        // set inputs to assessment's values

        // type
        if (assessment.getType().equals("PERFORMANCE")){
            typeInput.setChecked(true);
        } else {
            typeInput.setChecked(false);
        }

        titleInput.setText(assessment.getTitle());
//        dueDateYearInput.setText(assessment.getDueDateYear());
        dueDateYearInput.setText(assessment.getDueDate());
        goalDateYearInput.setText(assessment.getGoalDate());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHelper(getApplicationContext());
                Assessment newAssessment = getAssessment();
                // insert assessment
                long assessment_id = db.createAssessment(newAssessment);
                System.out.println("**** ASSESSMENT CREATED at ID: " + assessment_id);
                System.out.println("**** ASSESSMENT TYPE: " + newAssessment.getType());
                System.out.println("**** ASSESSMENT ALERT: " + newAssessment.getGoalDateAlert());
                finish();
            }
        });
    }

}
