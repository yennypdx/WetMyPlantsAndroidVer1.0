package com.wmp.android.wetmyplants.model;

public class Plant {

    private String SpeciesId;
    private String Nickname;
    private Double CurrentWater;
    private Double CurrentLight;

    public Plant(String inSpeciesId, String inName, Double inCurrWater, Double inCurrLight)
    {
        this.SpeciesId = inSpeciesId;
        this.Nickname = inName;
        this.CurrentWater = inCurrWater;
        this.CurrentLight = inCurrLight;
    }

    public String getSpeciesId() { return SpeciesId; }
    public void setSpeciesId(String inSpeciesId) { this.SpeciesId = inSpeciesId; }

    public String getNickname() { return Nickname; }
    public void setNickname(String inName) { this.Nickname = inName; }

    public Double getCurrentWater() { return CurrentWater; }
    public void setCurrentWater(Double inCurrWater) { this.CurrentWater = inCurrWater; }

    public Double getCurrentLight() { return CurrentLight; }
    public void setCurrentLight(Double inCurrLight) { this.CurrentLight = inCurrLight; }
}
