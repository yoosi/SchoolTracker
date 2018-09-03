package edu.wgu.cmaxwe3.schooltracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.helper.Tools;
import edu.wgu.cmaxwe3.schooltracker.model.Assessment;

public class EditAssessmentActivity extends AppCompatActivity {
    DatabaseHelper db;
    String assessmentID;


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
//        EditText dueDateInputYear = findViewById(R.id.editTextDueDateYear);
//        EditText dueDateInputMonth = findViewById(R.id.editTextDueDateMonth);
//        EditText dueDateInputDay = findViewById(R.id.editTextDueDateDay);
//        return dueDateInputYear.getText() + "-" + dueDateInputMonth.getText() + "-"
//                + dueDateInputDay.getText();
        return "foo"; // todo this
    }

    private String getGoalDate() {
//        EditText goalDateInputYear = findViewById(R.id.editTextGoalDateYear);
//        EditText goalDateInputMonth = findViewById(R.id.editTextGoalDateMonth);
//        EditText goalDateInputDay = findViewById(R.id.editTextGoalDateDay);
//        return goalDateInputYear.getText() + "-" + goalDateInputMonth.getText() + "-"
//                + goalDateInputDay.getText();
        return "foo"; // todo this
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
//        EditText dueDateInputYear = findViewById(R.id.editTextDueDateYear);
//        EditText dueDateInputMonth = findViewById(R.id.editTextDueDateMonth);
//        EditText dueDateInputDay = findViewById(R.id.editTextDueDateDay);
//        dueDateInputYear.setText(year);
//        dueDateInputMonth.setText(month);
//        dueDateInputDay.setText(day);
    }

    private void setGoalDate(String year, String month, String day) {
//        EditText goalDateInputYear = findViewById(R.id.editTextGoalDateYear);
//        EditText goalDateInputMonth = findViewById(R.id.editTextGoalDateMonth);
//        EditText goalDateInputDay = findViewById(R.id.editTextGoalDateDay);
//        goalDateInputYear.setText(year);
//        goalDateInputMonth.setText(month);
//        goalDateInputDay.setText(day);
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
        for (Assessment assessment : assessmentList
                ) {
            System.out.println("*** ASSESSMENT ID: " + assessment.getId());
            System.out.println("*** ASSESSMENT TITLE: " + assessment.getTitle());
        }

        Assessment assessment = db.getAssessment(Integer.valueOf(assessmentID));
        System.out.println(assessment.getTitle());
        Switch typeInput = findViewById(R.id.switchType);
        EditText titleInput = findViewById(R.id.editTextTitle);
        TextView dueDate = findViewById(R.id.textViewDueDate);
        TextView goalDate = findViewById(R.id.textViewGoalDate);
        ToggleButton toggleButtonGoalDateAlert = findViewById(R.id.toggleButtonGoalDateAlert);


        // set inputs to assessment's values

        // type
        if (assessment.getType().equals("PERFORMANCE")) {
            typeInput.setChecked(true);
        } else {
            typeInput.setChecked(false);
        }

        // title
        titleInput.setText(assessment.getTitle());

        // due date
        dueDate.setText(assessment.getDueDate());

        // goal date
        goalDate.setText(assessment.getGoalDate());

        // alert
        if (assessment.getGoalDateAlert() == 1) {
            toggleButtonGoalDateAlert.setChecked(true);
        } else {
            toggleButtonGoalDateAlert.setChecked(false);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // save button
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHelper(getApplicationContext());
                Assessment newAssessment = getAssessment();
                // insert assessment
                long assessment_id = db.updateAssessment(Integer.valueOf(assessmentID), newAssessment);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_assessment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.delete:
                System.out.println("*** YOU CLICKED DELETE");
                db = new DatabaseHelper(getApplicationContext());
                db.deleteAssessment(Integer.valueOf(assessmentID));
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
