package com.compilation.demos.adapterSort;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.compilation.mainApp.Model;
import com.compilation.R;

import java.util.ArrayList;

public class MyFragment extends Fragment {

    /**
     * Fragment reused in both tabs, but with different data
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getting the sorted models depending on position

        ArrayList<Model> models = getArguments().getParcelableArrayList("models");

        //setting the recyclerView

        RelativeLayout llLayout = (RelativeLayout) inflater.inflate(R.layout.model_fragment_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) llLayout.findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter  = new RecyclerViewAdapter(models);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        return llLayout;
    }

}
