package com.android.wetmyplants.model;

import java.util.ArrayList;
import java.util.List;

public class Plants {

    private List<Plant> plantList;

    public Plants(){
        plantList = new ArrayList<>();
    }

    public List<Plant> getPlantList() {
        return plantList;
    }

    public void setPlantList(List<Plant> newPlants) {
        plantList.clear();
        plantList.addAll(newPlants);

    }
}
