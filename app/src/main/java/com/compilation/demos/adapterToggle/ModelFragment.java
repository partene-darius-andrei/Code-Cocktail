package com.compilation.demos.adapterToggle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.compilation.R;

import java.util.ArrayList;

public class ModelFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout llLayout = (RelativeLayout) inflater.inflate(R.layout.questions_fragment_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) llLayout.findViewById(R.id.recycler_view);
        ArrayList<Model> models = getArguments().getParcelableArrayList("models");
        ModelsAdapter adapter  = new ModelsAdapter(models);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        return llLayout;
    }

}
