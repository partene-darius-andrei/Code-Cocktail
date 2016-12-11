package com.compilation.demos.averageSeekbars;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.compilation.R;
import com.compilation.mainApp.HolderActivity;
import com.compilation.mainApp.MyApplication;
import java.util.List;

public class Activity extends HolderActivity {

    /**
     * This is an activity which contains a list of seekbars. If you change any of the seekbar's value, the other ones adjust so that the final sum remains the same
     */

    private static SeekbarAdapter adapter;
    private static List<Integer> values;
    private static TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbars);

        //just to demo tha final result after refreshing
        result = (TextView) findViewById(R.id.result);

        ListView listView = (ListView) findViewById(R.id.listViewSlider);
        adapter = new SeekbarAdapter(this, MyApplication.getDummyData().getList());
        listView.setAdapter(adapter);

        values = adapter.getValues();
    }

    //this function is called when a seekbar changes it's value
    public static void refresh(int difference, boolean positive, int position) {

        //get the seekbars from the adapter
        List<SeekBar> seekBars = adapter.getSeekBars();

        //the new value to set for each seekbar
        int newValue = 0;

        //get the old value from the seekbar
        int oldValue;

        //sum is used to check that the result is ok
        int sum = 0;

        //final desired result
        double result = 100;

        //iterate through each seekbar
        for (int i = 0; i < seekBars.size(); i++) {
            SeekBar seekBar = seekBars.get(i);

            //get the seekbar value
            oldValue = seekBar.getProgress();

            //check to see if it's the current position which is chaning
            if (i != position) {
                //if it's negative or positive
                if (positive) {
                    //update new value
                    newValue = (oldValue - (difference / (seekBars.size() - 1)));

                    //check if it is outside or bounds
                    if (newValue < 0) {
                        newValue = 0;
                    } else if (newValue > seekBar.getMax()) {
                        newValue = seekBar.getMax();
                    }

                } else {
                    newValue = (oldValue + difference / (seekBars.size() - 1));
                }
                //current position
            } else {
                //check if it is outside of bounds
                if (result < oldValue) {
                    //set it to max
                    newValue = seekBar.getMax();
                    //check if it is the same value or not
                } else if (difference != oldValue) {
                    //if it isnt, check if it is negative or positive
                    if (positive) {
                        //update value
                        newValue = oldValue + difference;
                    } else {
                        newValue = oldValue - difference;
                    }
                    //same value
                } else newValue = oldValue;
            }
            //update the list of values for adapter
            values.set(i, newValue);
        }

        //test the outcome
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i);
        }

        //correct the difference
        reCheck(sum, result);

        //notify the adapter of changes
        adapter.setValues(values);
        adapter.notifyDataSetChanged();

        //update text with corrected values
        sum = 0;
        if (values != null) {
            for (int i = 0; i < values.size(); i++) {
                sum += values.get(i);
            }
        }
        Activity.result.setText(String.valueOf(sum));


    }

    public static void reCheck(int sum, double totalPoints) {
        int difference;
        //see if difference is positive or negative
        if (sum < totalPoints) {
            difference = (int) totalPoints - sum;
            loop:
            for (int i = 0; i <= difference; i++) {
                for (int j = 0; j < values.size(); j++) {
                    //check if the new value isn't greater than our max
                    if (values.get(j) + 1 <= adapter.getSeekBars().get(j).getMax()) {
                        //update value
                        values.set(j, values.get(j) + 1);
                        //update remaining difference
                        difference -= 1;
                        if (difference == 0) {
                            //exit if difference is 0
                            break loop;
                        }
                    }
                }
            }
        } else {
            difference = (int) (sum - totalPoints);
            loop:
            for (int i = 0; i <= difference; i++) {
                for (int j = 0; j < values.size(); j++) {
                    //check to see if new value isn't smaller than 0
                    if (values.get(j) - 1 >= 0) {
                        //update value
                        values.set(j, values.get(j) - 1);
                        //update difference
                        difference -= 1;
                        if (difference == 0) {
                            //exit if difference is 0
                            break loop;
                        }
                    }
                }
            }
        }
    }
}
