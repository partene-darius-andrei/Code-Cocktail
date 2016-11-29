package com.compilation.demos;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.compilation.MyApplication;
import com.compilation.R;

public class DownloadImageTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image_task);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        new Task(imageView, "myImage").execute("http://www.newyorker.com/wp-content/uploads/2016/01/Borowitz-Donald-Trump-1200.jpg");
    }

    public class Task extends AsyncTask<String, Void, Bitmap> {
        Context context = MyApplication.getContext();
        Bitmap bitmap;
        ImageView imageView;
        String name;

        Task(ImageView imageView, String name) {
            this.imageView = imageView;
            this.name = name;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            bitmap = getImageByName(name);
            if (bitmap == null) {
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    bitmap = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                saveImage(name);
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

        void saveImage(String name) {
            FileOutputStream out;
            try {
                out = context.openFileOutput(name, Context.MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Bitmap getImageByName(String name) {

            try {
                FileInputStream fis = context.openFileInput(name);
                bitmap = BitmapFactory.decodeStream(fis);
                fis.close();
                return bitmap;
            } catch (Exception ignored) {
            }
            return null;
        }
    }
}
