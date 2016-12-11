package com.compilation.demos.dualViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.compilation.R;
import com.compilation.mainApp.HolderActivity;
import com.compilation.mainApp.MyApplication;
import java.util.List;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class Activity extends HolderActivity {

    /**
     * A custom implementation of a 2D viewpager system. The main focus is on the find() function because that is the one which can
     * search in every fragment to find the fragment which is visible
     *
     * This is important because the viewpager keeps in memory 1-3 fragments and they are all fired in the same time
     *
     * Here, we would have a maximum of 9 fragments starting their job in the same time
     *
     * In this way, we have control over just one fragment
     */

    static VerticalViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_view_pager);
        initViewPager();
    }

    public void initViewPager() {

        Adapter adapter = new Adapter(getSupportFragmentManager(), MyApplication.getDummyData().getMap());
        viewPager = (VerticalViewPager) findViewById(R.id.view_pager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                HolderFragment holderFragment = (HolderFragment) fragments.get(position);
                holderFragment.find();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        viewPager.setAdapter(adapter);

    }
}
