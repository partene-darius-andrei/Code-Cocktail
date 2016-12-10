package com.compilation.demos.adapterSort;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.compilation.mainApp.HolderActivity;
import com.compilation.mainApp.MyApplication;
import com.compilation.R;

public class Activity extends HolderActivity {

    /**
     * This demos is used to show the flexibility of an adapter.
     * You can pass a list of objects and the adapter sorts those objects to each tab depending on a property (in this case "filtered")
     * We can reuse the same fragment because we're just changing the data
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_sort);
        initTabLayout();
    }


    private void initTabLayout() {

        //tabLayout with 2 tabs (for simplicity)

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Filtered"));
        tabLayout.addTab(tabLayout.newTab().setText("Unfiltered"));

        //viewPager containing the 2 fragments

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), MyApplication.getDummyData().getList());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayoutOnChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
