package me.littlecheesecake.cropimagelayout;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class searchid implements Serializable {


    @SerializedName("id")
    String id;

    @SerializedName("data")
    double [][][] pixels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double[][][] getPixels() {
        return pixels;
    }

    public void setPixels(double[][][] pixels) {
        this.pixels = pixels;
    }
}
