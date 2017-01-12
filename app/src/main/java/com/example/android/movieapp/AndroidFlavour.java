package com.example.android.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by akash on 11/3/16.
 */
public class AndroidFlavour implements Parcelable {
    String title;
    String plot;
    String  image;

    public AndroidFlavour(String title, String plot, String image) {
        this.title =title;
        this.plot= plot;
        this.image = image;
    }

    private AndroidFlavour(Parcel in) {
        title=in.readString();
        plot=in.readString();
        image=in.readString();
    }

    public String getPlot() {
        return plot;
    }



    public String getTitle() {
        return title;
    }



    public String getImage() {
        return this.image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(plot);
        parcel.writeString(image);
    }

    public String toString() {
        return title + "--" + plot + "--" + image;
    }

    public static final Parcelable.Creator<AndroidFlavour> CREATOR = new Parcelable.Creator<AndroidFlavour>() {
        @Override
        public AndroidFlavour createFromParcel(Parcel parcel) {
            return new AndroidFlavour(parcel);
        }

        @Override
        public AndroidFlavour[] newArray(int i) {
            return new AndroidFlavour[i];

        }
    };

}