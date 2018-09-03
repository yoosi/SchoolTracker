package edu.wgu.cmaxwe3.schooltracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.helper.Tools;
import edu.wgu.cmaxwe3.schooltracker.model.Assessment;

public class EditAssessmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    DatabaseHelper db;
    private String assessmentID;
    private boolean pickingDueDate;
    private String dueDate;
    private String goalDate;

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
        TextView dueDate = findViewById(R.id.textViewDueDate);
        return dueDate.getText().toString();
    }

    private String getGoalDate() {
        TextView goalDate = findViewById(R.id.textViewGoalDate);
        return goalDate.getText().toString();
    }

    private int getGoalDateAlert() {
        ToggleButton goalDateAlertInput = findViewById(R.id.toggleButtonGoalDateAlert);
        if (goalDateAlertInput.isChecked()) {
            return 1;
        } else {
            return 0;
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


        Button pickDueDateButton = findViewById(R.id.buttonDueDate);
        pickDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                pickingDueDate = true;
                datePicker.show(getSupportFragmentManager(), "date picker");


            }
        });

        Button pickGoalDateButton = findViewById(R.id.buttonGoalDate);
        pickGoalDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                pickingDueDate = false;
                datePicker.show(getSupportFragmentManager(), "date picker");


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


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateStringPretty = DateFormat.getDateInstance().format(c.getTime());

        if (pickingDueDate) {
            TextView textView = findViewById(R.id.textViewDueDate);
            textView.setText(currentDateStringPretty);
            dueDate = currentDateStringPretty;
        } else {
            TextView textView = findViewById(R.id.textViewGoalDate);
            textView.setText(currentDateStringPretty);
            goalDate = currentDateStringPretty;
        }

    }
}
