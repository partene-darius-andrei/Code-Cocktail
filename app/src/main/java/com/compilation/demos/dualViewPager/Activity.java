package com.compilation.demos.dualViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.compilation.R;
import com.compilation.mainApp.HolderActivity;
import com.compilation.mainApp.MyApplication;
import java.util.ArrayList;
import java.util.List;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class Activity extends HolderActivity {

    static VerticalViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_view_pager);
        initViewPager();
    }

    public void initViewPager() {

        viewPager = (VerticalViewPager) findViewById(R.id.view_pager);
        Adapter adapter = new Adapter(getSupportFragmentManager(), MyApplication.getDummyData().getMap());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                find();
            }
        });

        viewPager.setAdapter(adapter);

    }

    //for each Holder fragment display toast
    public void find() {
        List<Fragment> fragments = new ArrayList<>(getSupportFragmentManager().getFragments());
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                HolderFragment holderFragment = (HolderFragment) fragment;
                holderFragment.find();
            }
        }
    }

    public static VerticalViewPager getViewPager() {
        return viewPager;
    }
}
