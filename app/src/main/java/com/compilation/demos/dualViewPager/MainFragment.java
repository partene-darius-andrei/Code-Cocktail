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
 *
 */

public class MainFragment extends Fragment {

    //adapterPosition is used to determine if video can be played or not in find()
    //if adapterPosition matches the current position in the initViewPager, we play the video
    private int adapterPosition;
    private Model model;
    private TextView textView;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        model = getArguments().getParcelable("model");
        adapterPosition = getArguments().getInt("position");
        textView = (TextView) rootView.findViewById(R.id.textView);
        return rootView;
    }

    public TextView getTextView() {
        return textView;
    }

    public int getAdapterPosition() {
        return adapterPosition;
    }

    public Model getModel() {
        return model;
    }

}

