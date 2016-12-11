package com.compilation.demos.dualViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.compilation.mainApp.Model;
import com.google.gson.internal.LinkedTreeMap;
import java.util.ArrayList;

/**
 * This adapter is used in holderFragment and mainFragment. It switches the getItem() method and getCount based on constructor
 */

class Adapter extends FragmentStatePagerAdapter {

    private ArrayList<Model> mainList;
    private LinkedTreeMap<Integer, ArrayList<Model>> holderList;

    Adapter(FragmentManager fm, LinkedTreeMap<Integer, ArrayList<Model>> holderList) {
        super(fm);
        this.holderList = holderList;
    }

    Adapter(FragmentManager fm, ArrayList<Model> mainList) {
        super(fm);
        this.mainList = mainList;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        Bundle args = new Bundle();

        if (holderList != null) {
            fragment = HolderFragment.newInstance();
            //get the mainList at current position from map
            args.putParcelableArrayList("mainList", (ArrayList<Model>) holderList.values().toArray()[position]);
        }

        if (mainList != null) {
            fragment = MainFragment.newInstance();
            args.putParcelable("model", mainList.get(position));
        }

        args.putInt("position", position);
        assert fragment != null;
        //set extra information for each fragment
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getCount() {

        if (holderList != null) {
            return holderList.keySet().size();
        }

        if (mainList != null) {
            return mainList.size();
        }

        return 0;
    }

}