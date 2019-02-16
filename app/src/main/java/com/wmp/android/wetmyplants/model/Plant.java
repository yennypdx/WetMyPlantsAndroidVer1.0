package com.wmp.android.wetmyplants.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plant {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("Nicknm")
    @Expose
    private String nickname;

    @SerializedName("SpeciesId")
    @Expose
    private int speciesId;

    @SerializedName("CurrWtr")
    @Expose
    private double CurrentWater;

    @SerializedName("CurrLgt")
    @Expose
    private double CurrentLight;

}
