package com.wmp.android.wetmyplants.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.model.Plant;

public class PlantAddActivity extends AppCompatActivity {

    Plant newPlant;
    String inPlantName;
    String inSpeciesName;
    String inSensorSerial;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantadd);

        inPlantName = findViewById(R.id.plantAddNameInput).toString();
        inSpeciesName = findViewById(R.id.speciesAddInput).toString();
        inSensorSerial = findViewById(R.id.sensorSerialInput).toString();

        Button submitNewPlantBtn = findViewById(R.id.newPlantButton);
        submitNewPlantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPlant = new Plant(inPlantName, inSpeciesName, inSensorSerial);
                Toast.makeText(PlantAddActivity.this,"Your new plant is already added",
                        Toast.LENGTH_LONG).show();

                Intent toPlantList = new Intent(
                        PlantAddActivity.this, PlantsActivity.class);
                startActivity(toPlantList);
            }
        });
    }
}
