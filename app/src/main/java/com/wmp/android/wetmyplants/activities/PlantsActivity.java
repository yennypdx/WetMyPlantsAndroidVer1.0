package com.wmp.android.wetmyplants.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.helperClasses.CustomAdapter;
import com.wmp.android.wetmyplants.model.Plant;
import com.wmp.android.wetmyplants.model.PlantTestModel;

import java.util.ArrayList;

public class PlantsActivity extends AppCompatActivity {

    ListView listView;
    FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        String[] plantModelTest = new String[] {"Succulent", "Orchid",
                "Cactus", "Raflesia", "Rosemary" };

        listView = findViewById(R.id.plantListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(PlantsActivity.this,
                R.layout.row_plant, R.id.plant_desc, plantModelTest);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Update this link to connect with related item
                Intent toPlantDetail = new Intent(
                        PlantsActivity.this, PlantDetailActivity.class);
                startActivity(toPlantDetail);
            }
        });

        addBtn = findViewById(R.id.fabplant_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddPlantPage = new Intent(
                        PlantsActivity.this, PlantAddActivity.class);
                startActivity(toAddPlantPage);
            }
        });

    }
}
