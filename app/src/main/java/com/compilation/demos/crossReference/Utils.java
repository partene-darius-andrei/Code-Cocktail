package com.compilation.demos.crossReference;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

class Utils {

    private static Context context;

    Utils(Context context) {
        Utils.context = context;
    }


    static String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = context.getAssets().open("appIDs");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private static boolean isPackageInstalled(String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    static void createIcon(ImageView icon, final String packageName) {
        try {
            // get input stream
            InputStream ims = context.getAssets().open(packageName + ".png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            icon.setImageDrawable(d);
        } catch (IOException ex) {
            return;
        }
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check to see if app is installed or not
                if (isPackageInstalled(packageName)) {
                    //launch it
                    Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
                    context.startActivity(launchIntent);
                } else {
                    //go to play store
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?name=" + packageName));
                    context.startActivity(intent);
                }
            }
        });
    }
}
