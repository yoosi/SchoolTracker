package edu.wgu.cmaxwe3.schooltracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.model.Assessment;


public class AddAssessmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private DatabaseHelper db;

    private String dueDate;
    private String goalDate;
    private boolean pickingDueDate;


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
        return String.valueOf(dueDate);
    }

    private String getGoalDate() {
        return String.valueOf(goalDate);
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
        setContentView(R.layout.activity_add_assessment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        Button pickDueDateButton = findViewById(R.id.buttonDueDate);
//        pickDueDateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment datePicker = new DatePickerFragment();
//                pickingDueDate = true;
//                datePicker.show(getSupportFragmentManager(), "date picker");
//
//
//            }
//        });

        Button pickGoalDateButton = findViewById(R.id.buttonGoalDate);
        pickGoalDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                pickingDueDate = false;
                datePicker.show(getSupportFragmentManager(), "date picker");


            }
        });


        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuilder warning = new StringBuilder();

                if (getAssessmentTitle().length() == 0) {
                    warning.append(" [Title]");
                }

//                if (dueDate == null) {
//                    warning.append(" [Due Date]");
//                }

                if (goalDate == null) {
                    warning.append(" [Goal Date]");
                }


                if (warning.toString().length() == 0) {


                    db = new DatabaseHelper(getApplicationContext());
                    Assessment newAssessment = getAssessment();
                    // insert assessment
                    long assessment_id = db.createAssessment(newAssessment);
                    finish();

                } else {
                    Snackbar.make(view, "To save, you must provide values for the following fields:" + warning.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


            }
        });


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateStringPretty = DateFormat.getDateInstance().format(c.getTime());

        if (pickingDueDate) {
//            TextView textView = findViewById(R.id.textViewDueDate);
//            textView.setText(currentDateStringPretty);
//            dueDate = currentDateStringPretty;
        } else {
            TextView textView = findViewById(R.id.textViewGoalDate);
            textView.setText(currentDateStringPretty);
            goalDate = currentDateStringPretty;
        }


    }


}
