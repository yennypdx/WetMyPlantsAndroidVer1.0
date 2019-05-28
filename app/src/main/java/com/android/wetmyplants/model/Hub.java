package com.android.wetmyplants.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hub {

    @SerializedName("Id")
    @Expose
    private String Id;

    @SerializedName("Status")
    @Expose
    private boolean Status;

    public Hub(){}

    public Hub(String newId, boolean newStatus){
        this.Id = newId;
        this.Status = newStatus;
    }

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
