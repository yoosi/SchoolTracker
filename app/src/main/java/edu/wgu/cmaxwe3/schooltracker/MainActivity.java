package edu.wgu.cmaxwe3.schooltracker;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import edu.wgu.cmaxwe3.schooltracker.helper.DatabaseHelper;
import edu.wgu.cmaxwe3.schooltracker.helper.Tools;
import edu.wgu.cmaxwe3.schooltracker.model.Mentor;

public class MainActivity extends AppCompatActivity {

    // Database Helper
    DatabaseHelper db;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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




        db = new DatabaseHelper(getApplicationContext());

        // create mentor
        Mentor m = new Mentor();
        m.setName("Jimi Hendrix");
        m.setEmail("j.hendrix@school.edu");
        m.setPhone("2065551234");

        Mentor m2 = new Mentor("Jim Halpert", "206-555-4321", "j.halpert@dundermifflin.com", 0);
        Mentor m3 = new Mentor("Pam Beasley", "206-555-6789", "p.beasley@dundermifflin.com");

        //insert mentor
//        long mentor_id = db.createMentor(m);
        long mentor_id2 = db.createMentor(m3);

//        Log.d("Mentor Count", "mentor_id");
//        Log.d("Mentor Count", "mentor_id2");

        List<Mentor> mentors = db.getAllMentors();
        for (Mentor mentor: mentors) {
            System.out.println("MENTOR ID: " + mentor.getId());
            System.out.println("Mentor name: " + mentor.getName());
        }

        String birthday = Tools.buildDateString(1993, 3, 9);
        System.out.println("BIRTHDAY: " + birthday);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
