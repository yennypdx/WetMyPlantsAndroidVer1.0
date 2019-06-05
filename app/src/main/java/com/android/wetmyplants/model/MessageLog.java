package com.android.wetmyplants.model;

import java.sql.Date;
import java.sql.Timestamp;

public class MessageLog {

    private String PlantId;
    private String SensorId;
    private Date SentDate;
    private Timestamp SentTime;

    public MessageLog(String inPlantId, String inSensorId,
                      Date inDate, Timestamp inTime)
    {
        this.PlantId = inPlantId;
        this.SensorId = inSensorId;
        this.SentDate = inDate;
        this.SentTime = inTime;
    }

    public String getPlantId() { return PlantId; }
    public void setPlantId(String inPlantId) { this.PlantId = inPlantId; }

    public String getSensorId() { return SensorId; }
    public void setSensorId(String inSensorId) { this.SensorId = inSensorId; }

    public Date getSentDate() { return SentDate; }
    public void setSentDate(Date inDate) { this.SentDate = inDate; }

    public Timestamp getSentTime() { return SentTime; }
    public void setSentTime(Timestamp inTime) { this.SentTime = inTime; }
}
