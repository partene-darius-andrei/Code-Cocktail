package com.compilation.demos.horizontalViews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.compilation.mainApp.MyApplication;
import com.compilation.R;
import com.compilation.mainApp.Model;

import java.util.List;

public class HorizontalViews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_views);
        setViews();
    }

    public void setViews(){
        List<Model> list = MyApplication.getDummyData().getList();
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        for (int i = 0; i < list.size(); i++){
            final Model model = list.get(i);
            View view = getLayoutInflater().inflate(R.layout.model_list_item, null);

            TextView title = (TextView) view.findViewById(R.id.model_title);
            title.setText(model.getTitle());

            container.addView(view);
        }
    }
}
