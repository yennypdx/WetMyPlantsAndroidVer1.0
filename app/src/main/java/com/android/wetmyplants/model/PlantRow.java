package com.android.wetmyplants.model;

public class PlantRow {

    private String Id;
    private String Nickname;

    public PlantRow() {}

    public PlantRow(String inId, String inName)
    {
        this.Id = inId;
        this.Nickname = inName;
    }

    public String getId() { return Id; }
    public void setId(String id) {Id = id; }

    public String getNickname() { return Nickname; }
    public void setNickname(String inName) { this.Nickname = inName; }

}
