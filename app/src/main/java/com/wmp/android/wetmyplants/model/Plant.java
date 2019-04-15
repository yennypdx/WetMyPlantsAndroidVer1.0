package com.wmp.android.wetmyplants.model;

public class Plant {

    private String Nickname;
    private String SpeciesId;
    private String SensorSerial;
    private Double CurrentWater;
    private Double CurrentLight;

    public Plant(String inName, String inSpeciesId, String inSerial,
                 Double inCurrWater, Double inCurrLight)
    {
        this.Nickname = inName;
        this.SpeciesId = inSpeciesId;
        this.SensorSerial = inSerial;
        this.CurrentWater = inCurrWater;
        this.CurrentLight = inCurrLight;
    }

    public Plant(String inSpeciesId, String inName, String inSerial)
    {
        this.Nickname = inName;
        this.SpeciesId = inSpeciesId;
        this.SensorSerial = inSerial;
    }

    public String getSpeciesId() { return SpeciesId; }
    public void setSpeciesId(String inSpeciesId) { this.SpeciesId = inSpeciesId; }

    public String getNickname() { return Nickname; }
    public void setNickname(String inName) { this.Nickname = inName; }

    public String getSensorSerial() { return SensorSerial; }
    public void setSensorSerial(String inSerial) { this.SensorSerial = inSerial; }

    public Double getCurrentWater() { return CurrentWater; }
    public void setCurrentWater(Double inCurrWater) { this.CurrentWater = inCurrWater; }

    public Double getCurrentLight() { return CurrentLight; }
    public void setCurrentLight(Double inCurrLight) { this.CurrentLight = inCurrLight; }
}
