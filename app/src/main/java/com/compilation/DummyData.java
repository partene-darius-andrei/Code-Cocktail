package com.compilation;

import com.compilation.demos.adapterToggle.Model;

import java.util.ArrayList;

/**
 * Created by Darius on 12/6/2016.
 */
public class DummyData {

    //TODO add comments for each object (which demo)

    private static ArrayList<Model> list = new ArrayList<>();

    DummyData() {

        list.add(new Model("Filtered 1", true));
        list.add(new Model("Filtered 2", true));
        list.add(new Model("Unfiltered 3", false));
        list.add(new Model("Unfiltered 4", false));

    }

    public ArrayList<Model> getList() {
        return list;
    }
}
