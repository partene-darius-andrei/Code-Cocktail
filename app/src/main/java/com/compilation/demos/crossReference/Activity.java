package com.compilation.demos.crossReference;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.compilation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Activity extends AppCompatActivity {

    //TODO separate classes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_reference);

        //TODO radio buttons to change current appID and refresh layout (better demo)
        try {
            createCrossReference();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("deeplink");
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

    public void createCrossReference() throws JSONException {
        ImageView icon1 = (ImageView) findViewById(R.id.imageView1);
        ImageView icon2 = (ImageView) findViewById(R.id.imageView2);
        ImageView icon3 = (ImageView) findViewById(R.id.imageView3);

        //usually you call getPackageName, but for this demo, we'll hardcode it
//        String currentPackage = getPackageName();

        String currentPackage = "com.app2";
        final List<String> appIDs = new ArrayList<>();
        JSONObject obj = new JSONObject(loadJSONFromAsset());
        JSONArray array = obj.getJSONArray("data");

        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonOBject = array.getJSONObject(i);
            if (!currentPackage.equals(jsonOBject.getString("appID"))) {
                appIDs.add(jsonOBject.getString("appID"));
            }
            else {
                Toast.makeText(this, "Activity ignored: " + currentPackage, Toast.LENGTH_SHORT).show();
            }

        }
        createIcon(icon1, appIDs.get(0));
        createIcon(icon2, appIDs.get(1));
        createIcon(icon3, appIDs.get(2));
    }

    public void createIcon(ImageView icon, final String packageName) {
        try {
            // get input stream
            InputStream ims = getAssets().open(packageName + ".png");
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
                if (isPackageInstalled(packageName)) {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
                    startActivity(launchIntent);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?name=" + packageName));
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isPackageInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
