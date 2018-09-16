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

    private ArrayList<String> assessmentIDs;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button shareNotesButton = findViewById(R.id.buttonShareNotes);
        shareNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareNotes();
            }
        });

        System.out.println("**** ADD COURSE ON CREATE RUN");


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
                openSelectCourseAssignmentsForResult();
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



                // handle assessment selections
//                List<Assessment> assessments = db.getAllUnassignedAssessments();
//
//                System.out.println("********** assessments length: " + assessments.size());
//
//                ListView listViewAssessments = findViewById(R.id.listViewAssessments);
//
//
//                int len2 = listViewAssessments.getCount();
//                SparseBooleanArray checked2 = listViewAssessments.getCheckedItemPositions();
//                for (int i = 0; i < len2; i++)
//                    if (checked2.get(i)) {
//                        Assessment assessment = assessments.get(i);
//                        selectedAssessments.add(assessment);
//                    }
//                for (Assessment assessment : selectedAssessments) {
//                    assessment.setCourseId((int) course_id);
//                    db.updateAssessment(assessment.getId(), assessment);
//                }
//
//                SparseBooleanArray allAssessments = listViewAssessments.getItemAtPosition()

                //todo PUT YOUR CODE THAT UPDATES THE MENTORS AND ASSESSMENTS TO POINT TO THIS COURSE ID HERE

                //todo pass selectedMentors to another


                // update assessments


                if (assessmentIDs != null) {
                    Integer i = (int) (long) course_id;
                    for (String assessmentID : assessmentIDs) {
                        long l = db.updateAssessmentCourseId(Integer.valueOf(assessmentID), i);
                    }
                } else {
                    System.out.println("assessmentIDs is NULL");
                }
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

    public void openSelectCourseAssignmentsForResult() {
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
}
