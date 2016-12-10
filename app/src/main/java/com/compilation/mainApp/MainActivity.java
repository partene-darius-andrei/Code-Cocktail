package com.compilation.mainApp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.compilation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //TODO separate classes

    List<Model> demos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        demos = getDemos();

        initRecyclerView();

    }

    private List<Model> getDemos() {

        List<Model> demos = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray demosJson = jsonObject.getJSONArray("demos");
            for (int i = 0; i < demosJson.length(); i++){
                demos.add(new Model(demosJson.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return demos;
    }

    public void initRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        MainAdapter adapter = new MainAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        try {
                            Intent demo = new Intent(getApplicationContext(), Class.forName(demos.get(position).getClassName()));
                            demo.putExtra("demo", demos.get(position));
                            startActivity(demo);
                        } catch (ClassNotFoundException e) {
                            Toast.makeText(getApplicationContext(), "Class not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
        );
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        interface OnItemClickListener {
            void onItemClick(View view, int position);

        }

        GestureDetector mGestureDetector;

        RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

        @Override
        public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
    }

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;

            MyViewHolder(View view) {
                super(view);
//            description = (TextView) view.findViewById(R.id.description);
                title = (TextView) view.findViewById(R.id.title);
            }
        }

        MainAdapter() {
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_row_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.title.setText(demos.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return demos.size();
        }


    }

    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("demo_list");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
