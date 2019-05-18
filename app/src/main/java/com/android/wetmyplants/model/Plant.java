package com.android.wetmyplants.model;

import android.telecom.Call;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Callback;

public class Plant {

    @SerializedName("Id")
    @Expose
    private String Id;

    @SerializedName("Nickname")
    @Expose
    private String Nickname;

    @SerializedName("SpeciesId")
    @Expose
    private int SpeciesId;

    @SerializedName("CurrentWater")
    @Expose
    private Double CurrentWater;

    @SerializedName("CurrentLight")
    @Expose
    private Double CurrentLight;

    private String emailRef;

    public Plant() {}

    public Plant(String inNickname, int inSpeciesId)
    {
        this.Nickname = inNickname;
        this.SpeciesId = inSpeciesId;
    }

    public Plant(String inNickname, int inSpeciesId,
                 Double inCurrWater, Double inCurrLight)
    {
        this.Nickname = inNickname;
        this.SpeciesId = inSpeciesId;
        this.CurrentWater = inCurrWater;
        this.CurrentLight = inCurrLight;
    }

    public Plant(String inId, String inNickname, int inSpeciesId,
                 Double inCurrWater, Double inCurrLight)
    {
        this.Id = inId;
        this.Nickname = inNickname;
        this.SpeciesId = inSpeciesId;
        this.CurrentWater = inCurrWater;
        this.CurrentLight = inCurrLight;
    }

    public Plant(String inId, String inNickname, Double inCurrWater, Double inCurrLight)
    {
        this.Id = inId;
        this.Nickname = inNickname;
        this.CurrentWater = inCurrWater;
        this.CurrentLight = inCurrLight;
    }

    public Plant(String inId, String inNickname, int inSpeciesId,
                 Double inCurrWater, Double inCurrLight, String inEmail)
    {
        this.Id = inId;
        this.Nickname = inNickname;
        this.SpeciesId = inSpeciesId;
        this.CurrentWater = inCurrWater;
        this.CurrentLight = inCurrLight;
        this.emailRef = inEmail;
    }

    public Plant(String inId, String inName)
    {
        this.Id = inId;
        this.Nickname = inName;
    }

    public String getId() { return Id; }
    public void setId(String id) {Id = id; }

    public String getNickname() { return Nickname; }
    public void setNickname(String inName) { this.Nickname = inName; }

    public int getSpeciesId() { return SpeciesId; }
    public void setSpeciesId(int speciesId) { SpeciesId = speciesId; }

    public Double getCurrentWater() { return CurrentWater; }
    public void setCurrentWater(Double inCurrWater) { this.CurrentWater = inCurrWater; }

    public Double getCurrentLight() { return CurrentLight; }
    public void setCurrentLight(Double inCurrLight) { this.CurrentLight = inCurrLight; }

    public String getEmailRef() {
        return emailRef;
    }

    public void setEmailRef(String emailRef) {
        this.emailRef = emailRef;
    }
}