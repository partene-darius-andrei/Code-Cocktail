package com.compilation.mainApp;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.compilation.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class HolderActivity extends AppCompatActivity {

    /**
     * Activity used for each demo activity.
     * It gets the demo, sets the toolbar, loads ads and sets portrait
     */

    public void setContentView(int id){

        super.setContentView(id);

        Model demo = getIntent().getParcelableExtra("demo");

        //toolbar

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(demo.getTitle());

        //back button

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //ads
        loadAds();

        //portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    public void loadAds(){
        AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId(getString(R.string.banner_ad_unit_id));

        // Create an ad request.
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

        // Optionally populate the ad request builder.
        adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);

        // Add the AdView to the view hierarchy.
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ad_container);
        layout.addView(mAdView);

        // Start loading the ad.
        mAdView.loadAd(adRequestBuilder.build());
    }
}
