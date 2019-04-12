package com.wmp.android.wetmyplants.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.wmp.android.wetmyplants.R;

public class PlantEditActivity extends AppCompatActivity {

    String inputNewPlantName;
    String inputNewPlantSpecies;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantedit);

        inputNewPlantName = findViewById(R.id.plantEditNameInput).toString();
        inputNewPlantSpecies = findViewById(R.id.plantEditSpeciesInput).toString();

        //TODO: create Interface to ship this data via Retrofit!

    }

}
