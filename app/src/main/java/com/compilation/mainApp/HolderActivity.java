package com.compilation.mainApp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.compilation.R;

public class HolderActivity extends AppCompatActivity {

    /**
     * Activity used for each demo activity.
     * It gets the demo and sets the toolbar
     */

    public void setContentView(int id){

        super.setContentView(id);
        Model demo = getIntent().getParcelableExtra("demo");

        //toolbar

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(demo.getTitle());
    }
}
