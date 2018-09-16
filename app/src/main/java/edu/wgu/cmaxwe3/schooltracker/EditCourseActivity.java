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
import android.view.Menu;
import android.view.MenuItem;
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

public class EditCourseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private DatabaseHelper db;

    private String startDate;
    private String endDate;
    private boolean pickingStartDate;

    String courseID;


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

        courseID = getIntent().getStringExtra(ViewCoursesActivity.COURSE_ID);

        EditText titleInput = findViewById(R.id.editTextTitle);
        EditText statusInput = findViewById(R.id.editTextStatus);
        TextView startDateInput = findViewById(R.id.textViewStartDate);
        ToggleButton startAlertInput = findViewById(R.id.toggleButtonStartDateAlert);
        TextView endDateInput = findViewById(R.id.textViewEndDate);
        ToggleButton endAlertInput = findViewById(R.id.toggleButtonEndDateAlert);
        EditText notesInput = findViewById(R.id.editTextNotes);
        ListView mentorsInput = findViewById(R.id.listViewMentors);
        ListView assessmentsInput = findViewById(R.id.listViewAssessments);

        System.out.println("**** COURSE ID: " + courseID);

        db = new DatabaseHelper(getApplicationContext());
        Course course = db.getCourse(Integer.valueOf(courseID));

        titleInput.setText(course.getTitle());
        statusInput.setText(course.getStatus());
        startDateInput.setText(course.getStartDate());

        if (course.getStartAlert() == 1) {
            startAlertInput.setChecked(true);
        } else {
            startAlertInput.setChecked(false);
        }

        endDateInput.setText(course.getEndDate());

        if (course.getEndAlert() == 1) {
            endAlertInput.setChecked(true);
        } else {
            endAlertInput.setChecked(false);
        }

        notesInput.setText(course.getNotes());

        //todo add assessments and mentors to the lists



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


                /// handle mentor selections
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
                for (Mentor mentor : selectedMentors) {
                    System.out.println("MENTOR CHECKED: " + mentor.getName());
                    System.out.println("MENTOR ID: " + mentor.getId());
//                    mentor.setCourseId((int) course_id);
                    db.updateMentor(mentor.getId(), mentor);
                }


                // handle assessment selections
                List<Assessment> assessments = db.getAllUnassignedAssessments();

                System.out.println("********** assessments length: " + assessments.size());

                ListView listViewAssessments = findViewById(R.id.listViewAssessments);

                List<Assessment> selectedAssessments = new ArrayList<>();
                int len2 = listViewAssessments.getCount();
                SparseBooleanArray checked2 = listViewAssessments.getCheckedItemPositions();
                for (int i = 0; i < len2; i++)
                    if (checked2.get(i)) {
                        Assessment assessment = assessments.get(i);
                        selectedAssessments.add(assessment);
                    }
                for (Assessment assessment : selectedAssessments) {
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
        System.out.println("******* mentors size is: " + mentors.size());
        ArrayAdapter<Mentor> adapter = new ArrayAdapter<Mentor>(this,
                android.R.layout.simple_list_item_checked, android.R.id.text1, mentors);
        ListView lv = findViewById(R.id.listViewMentors);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // todo change this from multi to single
        lv.setAdapter(adapter);


        // todo make mentor checked if course id is the same as courseID
        // just kidding that is changing

        // populate list view of assessments
        List<Assessment> assessments = getAssessments();
        System.out.println("******* assessments size is: " + assessments.size());
        ArrayAdapter<Assessment> adapterAssessments = new ArrayAdapter<Assessment>(this,
                android.R.layout.simple_list_item_checked, android.R.id.text1, assessments);
        ListView lv2 = findViewById(R.id.listViewAssessments);
        lv2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv2.setAdapter(adapterAssessments);


        //todo remove this test code
//        lv2.setItemChecked(1, true);




        super.onResume();
    }


    private List<Mentor> getMentors() {
        db = new DatabaseHelper(getApplicationContext());
        return db.getAllAvailableMentorsForCourse(Integer.valueOf(courseID));
    }

    private List<Assessment> getAssessments() {
        db = new DatabaseHelper(getApplicationContext());
        return db.getAllAvailableAssessmentsForCourse(Integer.valueOf(courseID));
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_mentor, menu);
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
                db.deleteCourse(Integer.valueOf(courseID));
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
