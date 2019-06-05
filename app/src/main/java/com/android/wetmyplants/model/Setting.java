package com.android.wetmyplants.model;

public class Setting {

    private int MessageType;
    //private String ColorTheme;

    public Setting() {}

    public Setting(int inType)
    {
        this.MessageType = inType;
        //this.ColorTheme = inColor;
    }

    public int getMessageType() { return MessageType; }
    public void setMessageType(int intType) { this.MessageType = intType; }

    //public String getColorTheme() { return ColorTheme; }
    //public void setColorTheme(String inColor) { this.ColorTheme = inColor; }
}
