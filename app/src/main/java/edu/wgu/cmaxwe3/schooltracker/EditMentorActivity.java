package edu.wgu.cmaxwe3.schooltracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.model.Mentor;

public class EditMentorActivity extends AppCompatActivity {

    DatabaseHelper db;
    String mentorID;


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


        FloatingActionButton fab = findViewById(R.id.fab);
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
                Mentor newMentor = getMentor();
                System.out.println("*** MENTOR ID IS: " + mentorID);
                //insert mentor
                long mentor_id = db.updateMentor(Integer.valueOf(mentorID), newMentor);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


}
