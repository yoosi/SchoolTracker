package edu.wgu.cmaxwe3.schooltracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.model.Assessment;
import edu.wgu.cmaxwe3.schooltracker.model.Mentor;

public class AddAssessmentActivity extends AppCompatActivity {
    DatabaseHelper db;


    private String getAssessmentType(){
        // todo implement this
        return "Objective";
    }

    private String getAssessmentTitle(){
        EditText titleInput = (EditText) findViewById(R.id.editTextTitle);
        return titleInput.getText().toString();
    }

    private String getDueDate(){
        EditText dueDateInputYear = findViewById(R.id.editTextDueDateYear);
        EditText dueDateInputMonth = findViewById(R.id.editTextDueDateMonth);
        EditText dueDateInputDay = findViewById(R.id.editTextDueDateDay);
        return dueDateInputYear + "-" + dueDateInputMonth + "-" + dueDateInputDay;
    }

     private String getGoalDate(){
        EditText goalDateInputYear = findViewById(R.id.editTextGoalDateYear);
        EditText goalDateInputMonth = findViewById(R.id.editTextGoalDateMonth);
        EditText goalDateInputDay = findViewById(R.id.editTextGoalDateDay);
        return goalDateInputYear + "-" + goalDateInputMonth + "-" + goalDateInputDay;
    }

    private int getGoalDateAlert(){
        ToggleButton goalDateAlertInput = findViewById(R.id.toggleButtonGoalDateAlert);
        if(goalDateAlertInput.isChecked()){
            return 1;
        } else{
            return 0;
        }
    }

    private Assessment getAssessment(){

        Assessment assessment = new Assessment(getAssessmentType(), getAssessmentTitle(),
                getDueDate(), getGoalDate(), getGoalDateAlert());

        return assessment;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHelper(getApplicationContext());
                Assessment newAssessment = getAssessment();
                // insert assessment
                long assessment_id = db.createAssessment(newAssessment);
                finish();
            }
        });




    }


}
