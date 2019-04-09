package com.wmp.android.wetmyplants.model;

public class Sensor {

    private String SensorId;
    private String PairPlant;
    private int PowerStat;

    public Sensor(String inSensorId, String inPair, int inStat)
    {
        this.SensorId = inSensorId;
        this.PairPlant = inPair;
        this.PowerStat = inStat;
    }

    public String getSensorId() { return SensorId; }
    public void setSensorId(String inSensorId) { this.SensorId = inSensorId; }

    public String getPairPlant() { return PairPlant; }
    public void setPairPlant(String inPair) { this.PairPlant = inPair; }

    public int getPowerStat() { return PowerStat; }
    public void setPowerStat(int inStat) { this.PowerStat = inStat; }

}
