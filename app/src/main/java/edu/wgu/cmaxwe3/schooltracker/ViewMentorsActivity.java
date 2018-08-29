package edu.wgu.cmaxwe3.schooltracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLOutput;
import java.util.List;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.model.Mentor;
import edu.wgu.cmaxwe3.schooltracker.model.Term;

public class ViewMentorsActivity extends AppCompatActivity {

    DatabaseHelper db;
    public static String MENTOR_ID = "MENTOR_ID";


    public void openAddMentor(){
        Intent intent = new Intent(this, AddMentorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mentors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                openAddMentor();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {

        // populate listView
        final List<Mentor> mentors = getMentors();
        ArrayAdapter<Mentor> adapter = new ArrayAdapter<Mentor>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mentors);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ViewMentorsActivity.this, EditMentorActivity.class);

                Mentor mentor = mentors.get(position);
                System.out.println("*** PUTTING MENTOR ID: " + mentor.getId());
                intent.putExtra(MENTOR_ID, String.valueOf(mentor.getId()));
                startActivity(intent);
            }
        });

        super.onResume();
    }

    private List<Mentor> getMentors(){
        db = new DatabaseHelper(getApplicationContext());

        List<Mentor> mentors = db.getAllMentors();

        return mentors;
    }

}
