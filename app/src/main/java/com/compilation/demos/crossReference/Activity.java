package com.compilation.demos.crossReference;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.compilation.R;
import com.compilation.mainApp.HolderActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Activity extends HolderActivity {

    /**
     * This demos illustrates how we can have multiple apps (in this case 4) and create references to each other
     * We load the config from a json which contains a packagename (the icon has the same name for simplicity
     */

    //helper class to keep all the functions separate
    Utils utils = new Utils(this);

    JSONArray appIDs;
    ProgressBar progressBar;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_reference);

        TextView ignored = (TextView) findViewById(R.id.textView);
        ignored.setText("Select an app");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        container = (LinearLayout) findViewById(R.id.iconsContainer);

        //read the json
        try {
            JSONObject obj = new JSONObject(utils.loadJSONFromAsset());
            appIDs = obj.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Usually we just call createCrossReference once with the current app packagename,
     * but for this demo, we use radio buttons to demo the functionality
     */

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        progressBar.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);

        // Check which radio button was clicked
        if (checked)
        switch(view.getId()) {
            case R.id.app1:
                    createCrossReference("com.app1");
                    break;
            case R.id.app2:
                    createCrossReference("com.app2");
                    break;
            case R.id.app3:
                    createCrossReference("com.app3");
                    break;
            case R.id.app4:
                    createCrossReference("com.app4");
                    break;
        }
    }



    //create the list of appIDs

    public void createCrossReference(String currentPackage){
        ImageView icon1 = (ImageView) findViewById(R.id.imageView1);
        ImageView icon2 = (ImageView) findViewById(R.id.imageView2);
        ImageView icon3 = (ImageView) findViewById(R.id.imageView3);

        final List<String> appIDs = new ArrayList<>();

            for (int i = 0; i < this.appIDs.length(); i++) {
                String appID = null;
                try {
                    appID = this.appIDs.getJSONObject(i).getString("appID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //add the appIDs to the list if it's not the current one

                if (!currentPackage.equals(appID)) {
                    appIDs.add(appID);
                }
                else {
                    TextView ignored = (TextView) findViewById(R.id.textView);
                    ignored.setText("This demos illustrates how we can have multiple apps (in this case 4) and create references to each other\n" +
                            "We load the config from a json which contains a packagename (the icon has the same name for simplicity)");
                }
            }

        //set the icon and onClick behaviour

        utils.createIcon(icon1, appIDs.get(0));
        utils.createIcon(icon2, appIDs.get(1));
        utils.createIcon(icon3, appIDs.get(2));

    }
}
