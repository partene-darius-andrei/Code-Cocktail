package com.compilation;

import com.compilation.demos.adapterToggle.Model;

import java.util.ArrayList;

/**
 * Created by Darius on 12/6/2016.
 */
public class DummyData {
    private static ArrayList<Model> list = new ArrayList<>();
    private static Model m1 = new Model("Filtered 1", true);
    private static Model m2 = new Model("Filtered 2", true);
    private static Model m3 = new Model("Unfiltered 3", false);
    private static Model m4 = new Model("Unfiltered 4", false);

    DummyData() {
        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);
    }

    public ArrayList<Model> getList() {
        return list;
    }
}
