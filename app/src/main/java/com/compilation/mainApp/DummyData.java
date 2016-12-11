package com.compilation.mainApp;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

/**
 * Created by Darius on 12/6/2016.
 */
public class DummyData {

    DummyData() {
    }

    public ArrayList<Model> getList() {

        ArrayList<Model> list = new ArrayList<>();

        for (int i = 0; i < 6; i ++){
            if (i < 3){
                list.add(new Model("Model " + i, true));
            }
            else {
                list.add(new Model("Model " + i, false));
            }
        }

        return list;
    }

    public LinkedTreeMap<Integer, ArrayList<Model>> getMap(){

        LinkedTreeMap<Integer, ArrayList<Model>> map = new LinkedTreeMap<>();

        for (int i = 0; i < 3; i++){
            ArrayList<Model> models = new ArrayList<>();
            for (int j = 0; j < 4; j++){
                models.add(new Model("Model " + j));
            }
            map.put(i, models);
        }

        return map;
    }
}
