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
import android.widget.TextView;

import java.util.List;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.model.Course;
import edu.wgu.cmaxwe3.schooltracker.model.Mentor;

public class EditMentorActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private String mentorID;


    private Mentor getMentor() {

        EditText nameInput = findViewById(R.id.editTextName);
        EditText phoneInput = findViewById(R.id.editTextPhone);
        EditText emailInput = findViewById(R.id.editTextEmail);

        String name = nameInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String email = emailInput.getText().toString();

        return new Mentor(name, phone, email);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mentor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mentorID = getIntent().getStringExtra(ViewMentorsActivity.MENTOR_ID);
        EditText nameInput = findViewById(R.id.editTextName);
        EditText phoneInput = findViewById(R.id.editTextPhone);
        EditText emailInput = findViewById(R.id.editTextEmail);

        System.out.println("**** MENTOR ID: " + mentorID);

        db = new DatabaseHelper(getApplicationContext());
        Mentor mentor = db.getMentor(Integer.valueOf(mentorID));

        nameInput.setText(mentor.getName());
        phoneInput.setText(mentor.getPhone());
        emailInput.setText(mentor.getEmail());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // save button
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuilder warning = new StringBuilder();
                EditText nameInput = findViewById(R.id.editTextName);
                if (nameInput.getText().toString().length() == 0) {
                    warning.append(" [Name]");
                }

                if (warning.toString().length() == 0) {


                    db = new DatabaseHelper(getApplicationContext());
                    Mentor newMentor = getMentor();
                    System.out.println("*** MENTOR ID IS: " + mentorID);
                    //insert mentor
                    long mentor_id = db.updateMentor(Integer.valueOf(mentorID), newMentor);
                    finish();
                } else {
                    Snackbar.make(view, "To save, you must provide values for the following fields:" + warning.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

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
        db = new DatabaseHelper(getApplicationContext());

        switch (id) {

            case R.id.delete:
                System.out.println("*** YOU CLICKED DELETE");
                db = new DatabaseHelper(getApplicationContext());
                List<Course> coursesWithThisMentor = db.getCoursesForMentor(Integer.valueOf(mentorID));

                // set the mentorid of all courses with this mentor to 0
                if (coursesWithThisMentor.size() > 0) {
                    for (Course course : coursesWithThisMentor) {
                        db.updateCourseMentorID(course.getId(), 0);
                    }
                } else {
                    System.out.println("CANNOT DELETE MENTOR WHILE COURSES ARE ASSIGNED TO IT");
                }

                db.deleteMentor(Integer.valueOf(mentorID));
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
