package com.compilation.demos.downloadImageTask;

import android.os.Bundle;
import android.widget.ImageView;
import com.compilation.R;
import com.compilation.mainApp.HolderActivity;

public class Activity extends HolderActivity {

    /**
     * This activity downloads an image from a url and loads it in a imageView with an Async task
     * It also saves it in a cache
     */

    String url = "http://www.newyorker.com/wp-content/uploads/2016/01/Borowitz-Donald-Trump-1200.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image_task);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        new Task(imageView, this).execute(url);
    }
}
