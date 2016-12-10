package com.compilation.demos.horizontalViews;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.compilation.mainApp.HolderActivity;
import com.compilation.mainApp.MyApplication;
import com.compilation.R;
import com.compilation.mainApp.Model;
import java.util.List;

public class Activity extends HolderActivity {

    /**
     * This demo contains a horizontal listview.
     * I load the data, and for each model I add it to the view, creating the illusion of a horizontal listView
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_views);
        setViews();
    }

    public void setViews(){
        //load the dummy data (for demo only)
        List<Model> list = MyApplication.getDummyData().getList();
        LinearLayout container = (LinearLayout) findViewById(R.id.container);

        //get width of the display
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        //for each model, inflate and add the view to the container
        for (int i = 0; i < list.size(); i++){
            final Model model = list.get(i);

            //inflate new view
            View view = getLayoutInflater().inflate(R.layout.model_list_item, null);

            //set the width of the display as the view width
            view.setLayoutParams(new FrameLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));

            //set title for each view
            TextView title = (TextView) view.findViewById(R.id.model_title);
            title.setText(model.getTitle());

            //finally add the view
            container.addView(view);
        }
    }
}
