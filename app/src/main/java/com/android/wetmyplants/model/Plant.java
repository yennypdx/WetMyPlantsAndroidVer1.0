package com.android.wetmyplants.model;

public class Plant {

    private String Id;
    private String Nickname;
    private int SpeciesId;
    private Double CurrentWater;
    private Double CurrentLight;

    public Plant() {}

    public Plant(String inId, String inNickname, int inSpeciesId,
                 Double inCurrWater, Double inCurrLight)
    {
        this.Id = inId;
        this.Nickname = inNickname;
        this.SpeciesId = inSpeciesId;
        this.CurrentWater = inCurrWater;
        this.CurrentLight = inCurrLight;
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
}
