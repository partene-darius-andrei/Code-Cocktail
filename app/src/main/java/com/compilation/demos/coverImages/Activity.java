package com.compilation.demos.coverImages;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.compilation.mainApp.HolderActivity;
import com.compilation.R;
import com.facebook.drawee.backends.pipeline.Fresco;

public class Activity extends HolderActivity {

    /**
     * This demo loads a set of images from the res folder and repeats them depending on the size of our list
     * It doesn't matter how many covers or objects we have
     *
     * The Fresco library is used for better performance while scrolling
     */

    //size of elements to complete with covers
    int size = 69;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_images);
        Fresco.initialize(this);
        initRecyclerView();

    }

    public void initRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //we just pass the size as an argument to the adapter for the simplicity of this demo

        RecyclerViewAdapter adapter  = new RecyclerViewAdapter(size);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
