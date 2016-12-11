package com.compilation.demos.averageSeekbars;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.compilation.R;
import com.compilation.mainApp.HolderActivity;
import com.compilation.mainApp.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class Activity extends HolderActivity {

    private static SeekbarAdapter adapter;
    private static List<Integer> values = new ArrayList<>();
    private static TextView result;
    private static double totalPoints = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbars);
        result = (TextView) findViewById(R.id.result);
        initListView();
    }

    public void initListView() {
        ListView listView = (ListView) findViewById(R.id.listViewSlider);

        adapter = new SeekbarAdapter(this, MyApplication.getDummyData().getList());
        listView.setAdapter(adapter);
    }

    public static void refresh(int difference, boolean positive, int position) {
        List<SeekBar> seekBars = new ArrayList<>(adapter.getSeekBars());
        adapter.setValues(new ArrayList<>(updateSeekbars(seekBars, values, position, difference, positive, totalPoints)));
        int sum = 0;
        if (values != null){
            for (int i = 0; i < values.size(); i++){
                sum += values.get(i);
            }
        }
        result.setText(String.valueOf(sum));
        adapter.notifyDataSetChanged();

    }

    public static List<Integer> updateSeekbars(List<SeekBar> seekBars, List<Integer> values, int position, int difference, boolean positive, double totalPoints) {

        int newValue, oldValue, sum = 0, temp;

        for (int i = 0; i < seekBars.size(); i++) {
            SeekBar seekBar = seekBars.get(i);
            oldValue = (values.size() == seekBars.size() ? values.get(i) : seekBar.getProgress());
            if (i != position) {
                if (positive) {
                    newValue = (oldValue - (difference / (seekBars.size() - 1)));
                    if (newValue < 0) {
                        newValue = 0;
                    } else if (newValue > seekBar.getMax()) {
                        newValue = seekBar.getMax();
                    }
                } else {
                    newValue = (oldValue + difference / (seekBars.size() - 1));

                }
            } else {
                if (values.size() != seekBars.size()) {
                    if (totalPoints < oldValue)
                        newValue = (int) totalPoints;
                    else newValue = oldValue;
                } else {
                    if (totalPoints < oldValue) {
                        newValue = (int) totalPoints;
                    } else if (difference != oldValue)
                        if (positive) {
                            newValue = oldValue + difference;
                        } else {
                            newValue = oldValue - difference;
                        }
                    else newValue = oldValue;
                }
            }

            if (values.size() == seekBars.size()) {
                values.set(i, newValue);
            } else values.add(newValue);
        }

        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i);
        }

        if (sum < totalPoints) {
            temp = (int) (totalPoints - sum);
            loop:
            for (int i = 0; i <= temp; i++) {
                for (int j = 0; j < values.size(); j++) {
                    if (values.get(j) + 1 <= seekBars.get(j).getMax()) {
                        values.set(j, values.get(j) + 1);
                        temp -= 1;
                        if (temp == 0) {
                            break loop;
                        }
                    }
                }
            }
        }
        else {
            temp = (int) (sum - totalPoints);
            loop:
            for (int i = 0; i <= temp; i++) {
                for (int j = 0; j < values.size(); j++) {
                    if (values.get(j) - 1 >= 0) {
                        values.set(j, values.get(j) - 1);
                        temp -= 1;
                        if (temp == 0) {
                            break loop;
                        }
                    }
                }
            }
        }
        return values;
    }
}
