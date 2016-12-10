package com.compilation.demos.coverImages;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.compilation.R;
import com.facebook.drawee.backends.pipeline.Fresco;

public class Activity extends AppCompatActivity {

    //TODO add more comments

    //size of elements to complete with covers
    int size = 69;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_images);
        Context context = this;
        Fresco.initialize(context);
        initViews();

    }

    public void initViews() {
        initToolBar();
        initRecyclerView();
    }

    public void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void initRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        CoverAdapter adapter  = new CoverAdapter(size);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
