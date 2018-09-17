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

    private ArrayList<String> assessmentIDs;
    public static String COURSE_ID = "COURSE_ID";

    private String courseId;


    private String startDate;
    private String endDate;
    private boolean pickingStartDate;
    public static String MENTOR_ID = "MENTOR_ID";
    public static String ASSESSMENT_IDS = "ASSESSMENT_IDS";
    private int mentorId;


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
        course.setMentorId(mentorId);
        return course;
    }


    private void loadCourse(Course course) {
        EditText titleInput = findViewById(R.id.editTextTitle);
        EditText statusInput = findViewById(R.id.editTextStatus);
        TextView startDateInput = findViewById(R.id.textViewStartDate);
        ToggleButton startDateAlertInput = findViewById(R.id.toggleButtonStartDateAlert);
        TextView endDateInput = findViewById(R.id.textViewEndDate);
        ToggleButton endDateAlertInput = findViewById(R.id.toggleButtonEndDateAlert);
        EditText notesInput = findViewById(R.id.editTextNotes);
        TextView mentorInput = findViewById(R.id.textViewMentor);

        titleInput.setText(course.getTitle());
        statusInput.setText(course.getStatus());
        startDateInput.setText(course.getStartDate());
        if (course.getStartAlert() == 1) {
            startDateAlertInput.setChecked(true);
        } else {
            startDateAlertInput.setChecked(false);
        }
        endDateInput.setText(course.getEndDate());
        if (course.getEndAlert() == 1) {
            endDateAlertInput.setChecked(true);
        } else {
            endDateAlertInput.setChecked(false);
        }
        notesInput.setText(course.getNotes());
        mentorId = course.getMentorId();

        db = new DatabaseHelper(getApplicationContext());

        if (mentorId != 0) {
            Mentor mentor = db.getMentor(mentorId);
            mentorInput.setText(mentor.getName());
        }


        db = new DatabaseHelper(getApplicationContext());
        List<Assessment> assessments = db.getCourseAssessments(Integer.valueOf(courseId));
        ArrayAdapter<Assessment> adapterAssessments = new ArrayAdapter<Assessment>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, assessments);
        ListView lv = findViewById(R.id.listViewAssessments);
        lv.setAdapter(adapterAssessments);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseId = getIntent().getStringExtra(ViewCoursesActivity.COURSE_ID);

        System.out.println("**** COURSE ID: " + courseId);

        System.out.println("**** EDIT COURSE ON CREATE RUN");

        db = new DatabaseHelper(getApplicationContext());
        Course course = db.getCourse(Integer.valueOf(courseId));

        loadCourse(course);

        Button shareNotesButton = findViewById(R.id.buttonShareNotes);
        shareNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareNotes();
            }
        });


        // select mentors button
        Button selectMentorButton = findViewById(R.id.buttonPickMentor);
        selectMentorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSelectCourseMentorForResult();
            }
        });


        Button selectAssessmentsButton = findViewById(R.id.buttonPickAssessments);
        selectAssessmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSelectCourseAssessnmentsForResult();
            }
        });


        // save button
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuilder warning = new StringBuilder();
                EditText titleInput = findViewById(R.id.editTextTitle);
                EditText statusInput = findViewById(R.id.editTextStatus);


                if (titleInput.getText().toString().length() == 0) {
                    warning.append("[Title] ");
                }
                if (statusInput.getText().toString().length() == 0) {
                    warning.append("[Status] ");
                }


                if (warning.toString().length() == 0) {


                    db = new DatabaseHelper(getApplicationContext());
                    Course newCourse = getCourse();


                    //insert course

                    long course_id = db.updateCourse(Integer.valueOf(courseId), newCourse);


                    //todo PUT YOUR CODE THAT UPDATES THE MENTORS AND ASSESSMENTS TO POINT TO THIS COURSE ID HERE

                    //todo pass selectedMentors to another


                    // update assessments


                    if (assessmentIDs != null) {
                        // first clear all assessments from the course
                        List<Assessment> previouslyAssignedAssessments = db.getCourseAssessments(Integer.valueOf(courseId));
                        for (Assessment assessment : previouslyAssignedAssessments) {
                            db.updateAssessmentCourseId(assessment.getId(), 0);
                        }

                        // then re-add only the ones that are selected
                        Integer i = (int) (long) course_id;
                        for (String assessmentID : assessmentIDs) {
                            long l = db.updateAssessmentCourseId(Integer.valueOf(assessmentID), i);
                        }
                    } else {
                        System.out.println("assessmentIDs is NULL");
                    }
                    finish();
                } else {
                    Snackbar.make(view, "To save, you must provide values for the following fields:" + warning.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
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
        super.onResume();
    }


    private List<Mentor> getMentors() {
        db = new DatabaseHelper(getApplicationContext());
        return db.getAllUnassignedMentors();
    }

    private List<Assessment> getAssessments() {
        db = new DatabaseHelper(getApplicationContext());
        return db.getAllUnassignedAssessments();
    }


    public void openSelectCourseMentorForResult() {
        Intent intent = new Intent(this, SelectCourseMentorActivity.class);
        startActivityForResult(intent, 1);
    }

    public void openSelectCourseAssessnmentsForResult() {
        Intent intent = new Intent(this, SelectCourseAssessmentsActivity.class);
        startActivityForResult(intent, 2);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String mentorId = data.getStringExtra(MENTOR_ID);
                applyMentorChoice(Integer.valueOf(mentorId));
            }
        }

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                assessmentIDs = data.getStringArrayListExtra(ASSESSMENT_IDS);
                populateAssessmentsList(assessmentIDs);
            }
        }
    }


    private void populateAssessmentsList(ArrayList<String> assessmentIDs) {
        db = new DatabaseHelper(getApplicationContext());
        List<Assessment> assessments = new ArrayList<Assessment>();
        for (String assessmentID : assessmentIDs) {
            Assessment assessment = db.getAssessment(Integer.valueOf(assessmentID));
            assessments.add(assessment);
        }

        ArrayAdapter<Assessment> adapterAssessments = new ArrayAdapter<Assessment>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, assessments);
        ListView lv = findViewById(R.id.listViewAssessments);
        lv.setAdapter(adapterAssessments);
    }

    private void applyMentorChoice(int newMentorId) {
        db = new DatabaseHelper(getApplicationContext());
        mentorId = newMentorId;
        TextView mentorTextView = findViewById(R.id.textViewMentor);
        Mentor mentor = db.getMentor(newMentorId);
        mentorTextView.setText(mentor.getName());
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
                db.deleteCourse(Integer.valueOf(courseId));
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
