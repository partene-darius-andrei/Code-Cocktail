package com.compilation.mainApp;

import android.app.Application;
import android.content.Context;

/**
 * Created by Darius on 11/29/2016.
 */

public class MyApplication extends Application{

    static DummyData dummyData;

    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        dummyData = new DummyData();


    }

    //context used through the whole app
    public static Context getContext() {
        if (context == null)
            throw new RuntimeException("The application must have extend MyApplication. To do this add android:name=\".MyApplication\" to your <Application /> tag in AndroidManifest.xml");

        return context;
    }

    //dummyData used through the demos
    public static DummyData getDummyData() {
        return dummyData;
    }
}
