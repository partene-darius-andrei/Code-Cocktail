package com.compilation.demos.dualViewPager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.compilation.R;
import com.compilation.mainApp.Model;

/**
 * This is the fragment which is visible to the user and contains the passed model and ui elements
 */

public class MainFragment extends Fragment {

    //position is used to check if it matches with the viewpager index
    private int position;

    //our main object with data
    private Model model;

    //simple textView for demo
    private TextView textView;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        textView = (TextView) rootView.findViewById(R.id.textView);

        //get the data
        model = getArguments().getParcelable("model");

        //get the position
        position = getArguments().getInt("position");

        return rootView;
    }

    public TextView getTextView() {
        return textView;
    }

    public int getPosition() {
        return position;
    }

    public Model getModel() {
        return model;
    }

}

