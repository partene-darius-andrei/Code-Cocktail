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

        //initialize lists with empty elements so we can add
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
                Activity.refresh(Math.abs(difference), difference > 0, position);


            }
        });
        return view;
    }

    public List<SeekBar> getSeekBars() {
        return seekBars;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    public List<Integer> getValues() {
        return values;
    }
}
