package com.wmp.android.wetmyplants.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Species {
    @SerializedName("id")
    @Expose
    private int Id;

    @SerializedName("latname")
    @Expose
    private String LatinName;

    @SerializedName("comname")
    @Expose
    private String CommonName;

    @SerializedName("wtrvarmax")
    @Expose
    private double WaterVarMax;

    @SerializedName("wtrvarmin")
    @Expose
    private double WaterVarMin;

    @SerializedName("lghtmx")
    @Expose
    private double LightMax;

    @SerializedName("lgthmn")
    @Expose
    private double LightMin;

}
