package edu.wgu.cmaxwe3.schooltracker;

import android.os.Bundle;
import android.app.Activity;

public class AddMentorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mentor);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
