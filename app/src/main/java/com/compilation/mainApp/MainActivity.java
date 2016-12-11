package com.compilation.mainApp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //TODO separate classes

    List<Model> demos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demos = getDemos();
        initViews();

    }


    public void initViews() {
        initToolBar();
        initDrawer();
        initRecyclerView();
    }

    public void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close
        );
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

    }

    public void initDrawer() {

        List<String> names = new ArrayList<>();
        List<String> icons = new ArrayList<>();

        names.add("Feedback");
        names.add("Rate");
        names.add("Share");
        names.add("Github");

        icons.add(Icons.sendFeedback);
        icons.add(Icons.rate);
        icons.add(Icons.share);
        icons.add(Icons.gitHub);


        ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);
        DrawerAdapter adapter = new DrawerAdapter(this, icons, names);

        // Set the adapter for the list view
        mDrawerList.setAdapter(adapter);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent;
                switch (position) {
                    case 0:
                        String version = null;
                        try {
                            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                            version = pInfo.versionName;
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }

                        intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "partene.darius@gmail.com", null));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback " + getString(R.string.app_name) + " " + (version == null ? "" : version) + " android");
                        String emailContent = createEmailContent();
                        intent.putExtra(Intent.EXTRA_TEXT, emailContent);
                        startActivity(Intent.createChooser(intent, "Feedback"));
                        break;
                    case 1:
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("market://details?id=" + getApplicationContext().getApplicationInfo().packageName));
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
                        intent.putExtra(Intent.EXTRA_TEXT, "play.google.com/store/apps/details?id=" + getApplicationContext().getApplicationInfo().packageName);
                        startActivity(Intent.createChooser(intent, "Share via"));
                        break;
                    case 3:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/partene-darius-andrei/Compilation"));
                        startActivity(browserIntent);
                }
            }
        });
    }

    public String createEmailContent() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        String osVersion = "Android SDK: " + sdkVersion + " (" + release + ")\n";
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String deviceModel = "Device Model: " + manufacturer + " " + model + "\n";
        String displayLanguage = "Display language: " + Locale.getDefault().getLanguage() + "\n";
        return osVersion + deviceModel + displayLanguage;
    }

    private List<Model> getDemos() {

        List<Model> demos = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray demosJson = jsonObject.getJSONArray("demos");
            for (int i = 0; i < demosJson.length(); i++) {
                demos.add(new Model(demosJson.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return demos;
    }

    public void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        MainAdapter adapter = new MainAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            Intent demo = new Intent(getApplicationContext(), Class.forName(demos.get(position).getPackageName()));
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

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title, description;

            MyViewHolder(View view) {
                super(view);
            description = (TextView) view.findViewById(R.id.description);
                title = (TextView) view.findViewById(R.id.title);
            }
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
            holder.description.setText(demos.get(position).getDescription());
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

    static class Icons {

        static String sendFeedback = "\uf0e0";
        static String rate = "\ue800";
        static String share = "\ue801";
        static String gitHub = "\uf09b";

    }
}
