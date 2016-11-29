package com.compilation.demos.downloadImageTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.compilation.R;

public class DownloadImageTask extends AppCompatActivity {

    String url = "http://www.newyorker.com/wp-content/uploads/2016/01/Borowitz-Donald-Trump-1200.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image_task);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        new Task(imageView).execute(url);
    }
}
