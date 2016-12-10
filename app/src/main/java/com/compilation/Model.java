package com.compilation;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Model implements Parcelable{

    //TODO maybe make it global, available for all demos

    private String title;
    private String description;
    private String className;
    private boolean filtered;



    public Model(JSONObject jsonObject) throws JSONException {
        title = jsonObject.getString("title");
        description = jsonObject.getString("description");
        className = "com.compilation.demos." + jsonObject.getString("class") + ".Activity";
    }

    public Model(String title, boolean filtered) {
        this.title = title;
        this.filtered = filtered;
    }


    private Model(Parcel in) {
        title = in.readString();
        filtered = in.readByte() != 0;
        description = in.readString();
        className = in.readString();
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
        parcel.writeString(className);
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
