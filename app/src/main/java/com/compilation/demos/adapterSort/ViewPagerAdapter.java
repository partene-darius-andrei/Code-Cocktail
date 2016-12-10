package com.compilation.demos.adapterSort;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.compilation.mainApp.Model;
import java.util.ArrayList;

class ViewPagerAdapter extends FragmentStatePagerAdapter {

    /**
     * This is where we sort or objects and pass them as arguments for each fragment
     */

    private ArrayList<Model> filtered = new ArrayList<>();
    private ArrayList<Model> unfiltered = new ArrayList<>();

    ViewPagerAdapter(FragmentManager fm, ArrayList<Model> models) {

        //in the constructor we separate our objects depending on the "isFiltered" boolean

        super(fm);
        for (Model model : models) {
            if (model.isFiltered()) {
                filtered.add(model);
            } else {
                unfiltered.add(model);
            }
        }

    }

    @Override
    public Fragment getItem(int position) {

        //depending on the tab we have, we set the arguments respectively and return the fragment with the sorted data

        Fragment fragment = new MyFragment();
        Bundle args = new Bundle();

        switch (position) {
            case 0: {
                args.putParcelableArrayList("models", filtered);
                fragment.setArguments(args);
                return fragment;
            }
            case 1: {
                args.putParcelableArrayList("models", unfiltered);
                fragment.setArguments(args);
                return fragment;
            }
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 2;
    }

}