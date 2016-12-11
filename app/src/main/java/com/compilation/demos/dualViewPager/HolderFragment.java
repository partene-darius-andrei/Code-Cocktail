package com.compilation.demos.dualViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.compilation.R;
import com.compilation.mainApp.Model;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * Holder fragment which contains an arrayList of Main fragments
 */

public class HolderFragment extends Fragment {

    //adapterPosition is used to determine if video can be played or not in find()
    //if adapterPosition matches the current position in the initViewPager, we play the video
    private int adapterPosition;

    private Adapter adapter;
    private ViewPager horizontal;

    public static Fragment newInstance() {
        return new HolderFragment();
    }

    public HolderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.holder_fragment, container, false);

        ArrayList<Model> mainList = getArguments().getParcelableArrayList("mainList");

        //adapter
        adapterPosition = getArguments().getInt("position");
        adapter = new Adapter(getChildFragmentManager(), mainList);
        horizontal = (ViewPager) rootView.findViewById(R.id.horizontal_view_pager);

        horizontal.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            boolean flag = true;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //find is called here because we have to play the video from the start or else we have to change page to be called

                if (flag) {
                    find();
                    flag = false;
                }
            }

            @Override
            public void onPageSelected(int position) {

                find();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        horizontal.setAdapter(adapter);
        return rootView;
    }

    public void find() {

        final VerticalViewPager vertical = Activity.getViewPager();

        List<Fragment> fragments = new ArrayList<>(getChildFragmentManager().getFragments());
        for (Fragment fragment : fragments) {
            if (fragment != null) {

                final MainFragment mainFragment = (MainFragment) fragment;
                final Model model = mainFragment.getModel();
                final TextView textView = mainFragment.getTextView();

                if (mainFragment.getAdapterPosition() == horizontal.getCurrentItem() && adapterPosition == vertical.getCurrentItem()) {

                    textView.setText(adapterPosition + " " + mainFragment.getAdapterPosition() + "\n" + model.getTitle());
                    Toast.makeText(getActivity(),adapterPosition + " " + mainFragment.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public Adapter getAdapter() {
        return adapter;
    }

}