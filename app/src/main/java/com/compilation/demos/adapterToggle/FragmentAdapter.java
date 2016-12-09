package com.compilation.demos.adapterToggle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.compilation.Model;

import java.util.ArrayList;

class FragmentAdapter extends FragmentStatePagerAdapter {

    //TODO add comments

    private ArrayList<Model> filtered = new ArrayList<>();
    private ArrayList<Model> unfiltered = new ArrayList<>();

    FragmentAdapter(FragmentManager fm, ArrayList<Model> models) {
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

        Fragment fragment = new ModelFragment();
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

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}