package com.compilation.mainApp;

import java.util.ArrayList;

/**
 * Created by Darius on 12/6/2016.
 */
public class DummyData {


    private static ArrayList<Model> list = new ArrayList<>();

    DummyData() {

        list.add(new Model("Model 1", true));
        list.add(new Model("Model 2", true));
        list.add(new Model("Model 3", false));
        list.add(new Model("Model 4", false));
        list.add(new Model("Model 5", false));
        list.add(new Model("Model 6", false));

    }

    public ArrayList<Model> getList() {
        return list;
    }
}
