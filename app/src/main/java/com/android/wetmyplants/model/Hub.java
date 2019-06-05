package com.android.wetmyplants.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hub {

    @SerializedName("Id")
    @Expose
    private String Id;

    @SerializedName("Address")
    @Expose
    private String Address;

    @SerializedName("PlantId")
    @Expose
    private String PlantId;

    public Hub(){}

    public Hub(String newId, String inAddress, String inPlantId){
        this.Id = newId;
        this.Address = inAddress;
        this.PlantId = inPlantId;
    }

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPlantId() {
        return PlantId;
    }

    public void setPlantId(String userId) {
        PlantId = userId;
    }
}
