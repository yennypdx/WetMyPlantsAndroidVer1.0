package com.android.wetmyplants.model;

public class Setting {

    private String MessageType;     //TODO: create ENUM
    private String ColorTheme;      //TODO: create Theme Object

    public Setting(String inType, String inColor)
    {
        this.MessageType = inType;
        this.ColorTheme = inColor;
    }

    public String getMessageType() { return MessageType; }
    public void setMessageType(String intType) { this.MessageType = intType; }

    public String getColorTheme() { return ColorTheme; }
    public void setColorTheme(String inColor) { this.ColorTheme = inColor; }
}
