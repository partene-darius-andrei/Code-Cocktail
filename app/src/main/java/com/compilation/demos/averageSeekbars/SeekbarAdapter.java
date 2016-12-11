package com.compilation.demos.averageSeekbars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.compilation.R;
import com.compilation.mainApp.Model;

import java.util.ArrayList;
import java.util.List;

class SeekbarAdapter extends BaseAdapter {

    /**
     * The adapter which holds the seekbars and values and calls refresh() from our activity
     */
    private List<Model> models = new ArrayList<>();
    private Context context;
    private List<SeekBar> seekBars = new ArrayList<>();
    private List<Integer> values = new ArrayList<>();


    SeekbarAdapter(Context context, List<Model> models) {
        this.models = models;
        this.context = context;

        //initialize lists with empty elements so we can set later
        for (int i = 0; i < models.size(); i++){
            seekBars.add(null);
            values.add(null);
        }
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public SeekBar getItem(int position) {
        return seekBars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.seekbar_list_item, null);
        final Model model = models.get(position);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        TextView valueTextView = (TextView) view.findViewById(R.id.value);

        double value = model.getValue();

        //if there isn't a seekbar, this is the first time the view is created. set everything to middle and update values
        if (seekBars.get(position) == null) {
            seekBars.set(position, seekBar);
            values.set(position, (int) value / 2);
            seekBar.setProgress((int) (value / 2));
            valueTextView.setText(String.valueOf((int) (value / 2)));
        }
        else {
            //we have values, update seekbar
            valueTextView.setText(String.valueOf(values.get(position)));
            seekBar.setProgress(values.get(position));
        }

        seekBar.setMax((int) value);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;
            int initialProgress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                initialProgress = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //the difference from the beginning and end of change
                int difference = progressChanged - initialProgress;
                //update the other ones, except from this position
                refresh(Math.abs(difference), difference > 0, position);


            }
        });
        return view;
    }

    //this function is called when a seekbar changes it's value
    private void refresh(int difference, boolean positive, int position) {


        //the new value to set for each seekbar
        int newValue;

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
        notifyDataSetChanged();

        //update text with corrected values
        sum = 0;
        if (values != null) {
            for (int i = 0; i < values.size(); i++) {
                sum += values.get(i);
            }
        }
        Activity.waiting.setVisibility(View.GONE);
        Activity.result.setText(String.valueOf(sum));


    }

    private void reCheck(int sum, double totalPoints) {
        int difference;
        //see if difference is positive or negative
        if (sum < totalPoints) {
            difference = (int) totalPoints - sum;
            loop:
            for (int i = 0; i <= difference; i++) {
                for (int j = 0; j < values.size(); j++) {
                    //check if the new value isn't greater than our max
                    if (values.get(j) + 1 <= seekBars.get(j).getMax()) {
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
