package com.compilation.demos.downloadImageTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;


class Task extends AsyncTask<String, Void, Bitmap> {

    /**
     * Custom Async Task which downloads and saves image in cache
     */

    private Context context;
    private Bitmap bitmap;
    private ImageView imageView;

    Task(ImageView imageView, Context context) {
        this.imageView = imageView;
        this.context = context;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        bitmap = getImageByUrl(url);
        //if there isn't an image in cache, download it
        if (bitmap == null) {
            try {
                InputStream in = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            //and save it at the end with the name of the url
            saveImage(url);
        }
        return bitmap;
    }

    //at the end, set the image to the imageView
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }

    private void saveImage(String name) {
        FileOutputStream out;
        try {
            out = context.openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //load the image from cache if it exists
    private Bitmap getImageByUrl(String url) {

        try {
            FileInputStream fis = context.openFileInput(url);
            bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
            return bitmap;
        } catch (Exception ignored) {
        }
        return null;
    }
}
