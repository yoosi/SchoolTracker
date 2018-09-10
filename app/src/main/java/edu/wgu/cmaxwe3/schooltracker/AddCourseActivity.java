package edu.wgu.cmaxwe3.schooltracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.model.Assessment;
import edu.wgu.cmaxwe3.schooltracker.model.Course;
import edu.wgu.cmaxwe3.schooltracker.model.Mentor;

public class AddCourseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private DatabaseHelper db;

    private String startDate;
    private String endDate;
    private boolean pickingStartDate;




    private Course getCourse() {
        EditText titleInput = findViewById(R.id.editTextTitle);
        EditText statusInput = findViewById(R.id.editTextStatus);
        TextView startDateInput = findViewById(R.id.textViewStartDate);
        ToggleButton startDateAlertInput = findViewById(R.id.toggleButtonStartDateAlert);
        TextView endDateInput = findViewById(R.id.textViewEndDate);
        ToggleButton endDateAlertInput = findViewById(R.id.toggleButtonEndDateAlert);
        EditText notesInput = findViewById(R.id.editTextNotes);


        Course course = new Course();
        course.setTitle(titleInput.getText().toString());
        course.setStatus(statusInput.getText().toString());
        course.setStartDate(startDateInput.getText().toString());
        if (startDateAlertInput.isChecked()) {
            course.setStartAlert(1);
        } else {
            course.setStartAlert(0);
        }
        course.setEndDate(endDateInput.getText().toString());
        if (endDateAlertInput.isChecked()) {
            course.setEndAlert(1);
        } else {
            course.setEndAlert(0);
        }
        course.setNotes(notesInput.getText().toString());
        return course;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
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

        Button shareNotesButton = findViewById(R.id.buttonShareNotes);
        shareNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareNotes();
            }
        });


        // save button
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHelper(getApplicationContext());
                Course newCourse = getCourse();
                //insert course
                long course_id = db.createCourse(newCourse);


                /// testing stuff with listview selections
                List<Mentor> mentors = db.getAllUnassignedMentors();
                ListView listViewMentorsTesting = findViewById(R.id.listViewMentors);


                List<Mentor> selectedMentors = new ArrayList<>();
                int len = listViewMentorsTesting.getCount();
                SparseBooleanArray checked = listViewMentorsTesting.getCheckedItemPositions();
                for (int i = 0; i < len; i++)
                    if (checked.get(i)) {
                        Mentor mentor = mentors.get(i);
                        /* do whatever you want with the checked item */
                        selectedMentors.add(mentor);
                    }
                for (Mentor mentor: selectedMentors) {
                    System.out.println("MENTOR CHECKED: " + mentor.getName());
                    System.out.println("MENTOR ID: " + mentor.getId());
                    mentor.setCourseId((int) course_id);
                    System.out.println("MENTOR COURSE ID SET: " + mentor.getCourseId());
                    db.updateMentor(mentor.getId(), mentor);
                }



                List<Assessment> assessments = db.getAllAssessments();
                ListView listViewAssessments = findViewById(R.id.listViewAssessments);

                List<Assessment> selectedAssessments = new ArrayList<>();
                int len2 = listViewAssessments.getCount();
                SparseBooleanArray checked2 = listViewAssessments.getCheckedItemPositions();
                for (int i = 0; i < len; i++)
                    if (checked.get(i)) {
                    Assessment assessment = assessments.get(i);
                    selectedAssessments.add(assessment);
                    }
                for (Assessment assessment: selectedAssessments){
                    assessment.setCourseId((int) course_id);
                    db.updateAssessment(assessment.getId(), assessment);
                }

                    //todo PUT YOUR CODE THAT UPDATES THE MENTORS AND ASSESSMENTS TO POINT TO THIS COURSE ID HERE

                    //todo pass selectedMentors to another

                finish();
            }
        });

        // due date button
        Button pickStartDateButton = findViewById(R.id.buttonStartDate);
        pickStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                pickingStartDate = true;
                datePicker.show(getSupportFragmentManager(), "date picker");


            }
        });


        // end date button
        Button pickEndDateButton = findViewById(R.id.buttonEndDate);
        pickEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                pickingStartDate = false;
                datePicker.show(getSupportFragmentManager(), "date picker");


            }
        });

    }


    private void shareNotes() {
        EditText notesInput = findViewById(R.id.editTextNotes);
        String notes = notesInput.getText().toString();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, notes);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    protected void onResume() {

        // populate list view of mentors
        List<Mentor> mentors = getMentors();
        ArrayAdapter<Mentor> adapter = new ArrayAdapter<Mentor>(this,
                android.R.layout.simple_list_item_checked, android.R.id.text1, mentors);
        ListView lv = findViewById(R.id.listViewMentors);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(adapter);

        // populate list view of assessments
        List<Assessment> assessments = getAssessments();
        ArrayAdapter<Assessment> adapterAssessments = new ArrayAdapter<Assessment>(this,
                android.R.layout.simple_list_item_checked, android.R.id.text1, assessments);
        ListView lv2 = findViewById(R.id.listViewAssessments);
        lv2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv2.setAdapter(adapterAssessments);


        super.onResume();
    }


    private List<Mentor> getMentors() {
        db = new DatabaseHelper(getApplicationContext());
        return db.getAllUnassignedMentors();
    }

    private List<Assessment> getAssessments(){
        db = new DatabaseHelper(getApplicationContext());
        return db.getAllAssessments();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateStringPretty = DateFormat.getDateInstance().format(c.getTime());

        if (pickingStartDate) {
            TextView textView = findViewById(R.id.textViewStartDate);
            textView.setText(currentDateStringPretty);
            startDate = currentDateStringPretty;
        } else {
            TextView textView = findViewById(R.id.textViewEndDate);
            textView.setText(currentDateStringPretty);
            endDate = currentDateStringPretty;
        }
    }

}
