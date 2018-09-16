package edu.wgu.cmaxwe3.schooltracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.model.Course;
import edu.wgu.cmaxwe3.schooltracker.model.Term;

public class EditTermActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private DatabaseHelper db;

    public static String COURSE_IDS = "COURSE_IDS";

    private String startDate;
    private String endDate;
    private boolean pickingStartDate;
    private String termID;

    private ArrayList<String> courseIDs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        termID = getIntent().getStringExtra(ViewTermsActivity.TERM_ID);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //////////


        Button pickDueDateButton = findViewById(R.id.buttonStartDate);
        pickDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                pickingStartDate = true;
                datePicker.show(getSupportFragmentManager(), "date picker");


            }
        });

        // pick end date button
        Button pickEndDateButton = findViewById(R.id.buttonEndDate);
        pickEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                pickingStartDate = false;
                datePicker.show(getSupportFragmentManager(), "date picker");


            }
        });


        // save button
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Term term = getTerm();
                db = new DatabaseHelper(getApplicationContext());


                if(courseIDs != null) {

                    for (String courseID : courseIDs) {
                        long l = db.updateCourseTermID(Integer.valueOf(courseID),
                                Integer.valueOf(termID));
                    }
                }

                finish();
            }
        });





        Button pickCoursesButton = findViewById(R.id.buttonPickCourses);
        pickCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTermActivity.this, SelectTermCoursesActivity.class);
                startActivityForResult(intent, 1);

            }
        });

        ///////////


//         populate courses list
        db = new DatabaseHelper(getApplicationContext());
        List<Course> courses = db.getTermCourses(Integer.valueOf(termID));

        ArrayAdapter<Course> adapterCourses = new ArrayAdapter<Course>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, courses);
        ListView lv = findViewById(R.id.listViewCourses);
        lv.setAdapter(adapterCourses);

        Term term = db.getTerm(Integer.valueOf(termID));
        System.out.println("*** ID OF TERM CLICKED IS: " + termID);
        loadTerm(term);




    }

    private void loadTerm(Term term){
        TextView titleInput = findViewById(R.id.editTextTermTitle);
        TextView startDateInput = findViewById(R.id.textViewStartDate);
        TextView endDateInput = findViewById(R.id.textViewEndDate);

        titleInput.setText(term.getTitle());
        startDateInput.setText(term.getStartDate());
        endDateInput.setText(term.getEndDate());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                courseIDs = data.getStringArrayListExtra(COURSE_IDS);
                populateCoursesList(courseIDs);
            }
        }
    }

    private void populateCoursesList(ArrayList<String> courseIDs) {
        db = new DatabaseHelper(getApplicationContext());
        List<Course> courses = new ArrayList<Course>();
        for (String courseID : courseIDs) {
            Course course = db.getCourse(Integer.valueOf(courseID));
            courses.add(course);
        }

        ArrayAdapter<Course> adapterCourses = new ArrayAdapter<Course>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, courses);
        ListView lv = findViewById(R.id.listViewCourses);
        lv.setAdapter(adapterCourses);
    }

    private Term getTerm(){
        EditText titleInput = findViewById(R.id.editTextTermTitle);
        TextView startDateInput = findViewById(R.id.textViewStartDate);
        TextView endDateInput = findViewById(R.id.textViewEndDate);


        Term term = new Term();
        term.setTitle(titleInput.getText().toString());
        term.setStartDate(startDateInput.getText().toString());
        term.setEndDate(endDateInput.getText().toString());

        return term;

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private List<Course> getCourses() {
        db = new DatabaseHelper(getApplicationContext());
        return db.getAllUnassignedCourses();
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
                db.deleteTerm(Integer.valueOf(termID));
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
