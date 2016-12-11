package com.compilation.mainApp;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Model implements Parcelable{

    private String title;
    private String description;
    private String packageName;
    private boolean filtered;
    private ArrayList<Model> models;
    private double value;



    public Model(JSONObject jsonObject) throws JSONException {
        title = jsonObject.getString("title");
        description = jsonObject.getString("description");
        packageName = "com.compilation.demos." + jsonObject.getString("packageName") + ".Activity";
    }

    public Model(String title, boolean filtered, double value) {
        this.title = title;
        this.filtered = filtered;
        this.value = value;
    }

    public Model(String title){
        this.title = title;
    }


    private Model(Parcel in) {
        title = in.readString();
        filtered = in.readByte() != 0;
        description = in.readString();
        packageName = in.readString();
        value = in.readDouble();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeByte((byte) (filtered ? 1 : 0));
        parcel.writeString(description);
        parcel.writeString(packageName);
        parcel.writeDouble(value);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setModels(ArrayList<Model> models) {
        this.models = models;
    }

    public ArrayList<Model> getModels() {
        return models;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
