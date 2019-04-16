package com.wmp.android.wetmyplants.model;

public class Plant {

    private String Nickname;
    private String Species;
    private String SensorSerial;
    private Double CurrentWater;
    private Double CurrentLight;

    public Plant(String inName, String inSpeciesId, String inSerial,
                 Double inCurrWater, Double inCurrLight)
    {
        this.Nickname = inName;
        this.Species = inSpeciesId;
        this.SensorSerial = inSerial;
        this.CurrentWater = inCurrWater;
        this.CurrentLight = inCurrLight;
    }

    public Plant(String inSpeciesId, String inName, String inSerial)
    {
        this.Nickname = inName;
        this.Species = inSpeciesId;
        this.SensorSerial = inSerial;
    }

    public String getNickname() { return Nickname; }
    public void setNickname(String inName) { this.Nickname = inName; }

    public String getSpecies() { return Species; }
    public void setSpecies(String inSpeciesId) { this.Species = inSpeciesId; }

    public String getSensorSerial() { return SensorSerial; }
    public void setSensorSerial(String inSerial) { this.SensorSerial = inSerial; }

    public Double getCurrentWater() { return CurrentWater; }
    public void setCurrentWater(Double inCurrWater) { this.CurrentWater = inCurrWater; }

    public Double getCurrentLight() { return CurrentLight; }
    public void setCurrentLight(Double inCurrLight) { this.CurrentLight = inCurrLight; }
}
