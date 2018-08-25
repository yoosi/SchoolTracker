package edu.wgu.cmaxwe3.schooltracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.model.Mentor;

public class AddMentorActivity extends AppCompatActivity {
    DatabaseHelper db;

    public void openViewMentors(){
        Intent intent = new Intent(this, ViewMentorsActivity.class);
        startActivity(intent);
    }

    private Mentor getMentor(){
        EditText nameInput = findViewById(R.id.editTextName);
        EditText phoneInput = findViewById(R.id.editTextPhone);
        EditText emailInput = findViewById(R.id.editTextEmail);

        String name = nameInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String email = emailInput.getText().toString();

        System.out.println("Name length is: " + name.length());

        Mentor mentor = new Mentor(name, phone, email);

        return mentor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mentor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        Button saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHelper(getApplicationContext());
                Mentor newMentor = getMentor();
                //insert mentor
                long mentor_id = db.createMentor(newMentor);
//                Snackbar.make(view, "Mentor created with name: " + newMentor.getName(), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
