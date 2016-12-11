package com.compilation.demos.dualViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.compilation.R;
import com.compilation.mainApp.Model;
import java.util.ArrayList;
import java.util.List;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * Holder fragment which contains an arrayList of Main fragments and the find() function
 */

public class HolderFragment extends Fragment {

    //we need the both viewpagers to access their current index
    private ViewPager horizontal;
    private VerticalViewPager vertical = Activity.viewPager;

    //position is used to select the current visible fragment from fragmentManager
    private int position;

    public static Fragment newInstance() {
        return new HolderFragment();
    }

    public HolderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.holder_fragment, container, false);

        //get the list of models passed through the adapter
        ArrayList<Model> mainList = getArguments().getParcelableArrayList("mainList");

        //adapter
        Adapter adapter = new Adapter(getChildFragmentManager(), mainList);
        horizontal = (ViewPager) rootView.findViewById(R.id.horizontal_view_pager);
        horizontal.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //flag used to stop after first use of find()
            boolean flag = true;

            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {

                //find is called here because we have to set the textView from the start or else we have to change page to be called
                if (flag) {
                    position = pos;
                    find();
                    flag = false;
                }
            }

            @Override
            public void onPageSelected(int pos) {
                position = pos;
                find();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        horizontal.setAdapter(adapter);
        return rootView;
    }

    //this function is called when the position is changed
    public void find() {

        List<Fragment> fragments = getChildFragmentManager().getFragments();
        MainFragment mainFragment = (MainFragment) fragments.get(position);
        Model model = mainFragment.getModel();
        TextView textView = mainFragment.getTextView();

        //the positions of the fragments passed through the adapter
        int verticalPosition = getArguments().getInt("position");
        int horizontalPosition = mainFragment.getPosition();

        //check if it matches with our current viewpagers index
        if (horizontalPosition == horizontal.getCurrentItem() && verticalPosition == vertical.getCurrentItem()) {

            //here we have access to the textview in each main fragment
            textView.setText(model.getTitle() + "\nVertical: " + verticalPosition + "\nHorizontal: " + horizontalPosition);

            //and the textview from the activity
            Activity.current.setText("Current position: " + verticalPosition + " " + horizontalPosition);

            //for demo only
            if (verticalPosition == 0){
                switch (horizontalPosition){
                    case 0:
                        textView.setText("Swipe left");
                        break;
                    case 1:
                        textView.setText("Here, we would have 6 fragments starting their job in the same time");
                        break;
                    case 2:
                        textView.setText("In this way, we have control over just one fragment");
                        break;
                    case 3:
                        textView.setText("Now swipe down");
                        break;
                }
            }

        }
    }

}