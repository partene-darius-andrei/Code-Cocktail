package com.compilation.demos.downloadImageTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.compilation.MyApplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Darius on 11/30/2016.
 */

class Task extends AsyncTask<String, Void, Bitmap> {

    private Context context = MyApplication.getContext();
    private Bitmap bitmap;
    private ImageView imageView;

    Task(ImageView imageView) {
        this.imageView = imageView;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        bitmap = getImageByUrl(url);
        if (bitmap == null) {
            try {
                InputStream in = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            saveImage(url);
        }
        return bitmap;
    }

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
