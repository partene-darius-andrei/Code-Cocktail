package com.compilation.demos.averageSeekbars;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.compilation.R;
import com.compilation.mainApp.HolderActivity;
import com.compilation.mainApp.MyApplication;

public class Activity extends HolderActivity {

    /**
     * This is an activity which contains a list of seekbars. If you change any of the seekbar's value, the other ones adjust so that the final sum remains the same
     */

    static TextView result;
    static ProgressBar waiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbars);

        //just to demo the final result after refreshing
        result = (TextView) findViewById(R.id.result);
        waiting = (ProgressBar) findViewById(R.id.waiting);

        ListView listView = (ListView) findViewById(R.id.listViewSlider);

        SeekbarAdapter adapter = new SeekbarAdapter(this, MyApplication.getDummyData().getList());
        listView.setAdapter(adapter);

    }
}
