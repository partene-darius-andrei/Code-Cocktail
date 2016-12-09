package com.compilation;

import android.os.Parcel;
import android.os.Parcelable;

public class Model implements Parcelable{

    //TODO maybe make it global, available for all demos

    private String title;
    private boolean filtered;

    public Model(String title, boolean filtered) {
        this.title = title;
        this.filtered = filtered;
    }


    private Model(Parcel in) {
        title = in.readString();
        filtered = in.readByte() != 0;
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
}
