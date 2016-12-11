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

/**
 * Created by Darius on 9/24/2016.
 */
public class SeekbarAdapter extends BaseAdapter {
    private List<Model> models = new ArrayList<>();
    private Context context;
    private List<SeekBar> seekBars = new ArrayList<>();
    private List<Integer> values;


    public SeekbarAdapter(Context context, List<Model> models) {
        this.models = models;
        this.context = context;
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

        double value = model.getValue();
        if (seekBars.size() < models.size()){
            seekBars.add(seekBar);
        }

        TextView valueTextView = (TextView) view.findViewById(R.id.value);
        seekBar.setMax((int)value);
        if (values == null){
            seekBar.setProgress((int) (value / 2));
            valueTextView.setText(String.valueOf((int) (value / 2)));
        }
        else {
            valueTextView.setText(String.valueOf(values.get(position)));
            seekBar.setProgress(values.get(position));
        }




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
                int difference = progressChanged - initialProgress;
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
}
