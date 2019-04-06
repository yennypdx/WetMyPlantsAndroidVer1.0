package com.wmp.android.wetmyplants.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.helperClasses.CustomAdapter;
import com.wmp.android.wetmyplants.model.PlantTestModel;

import java.util.ArrayList;

public class PlantsActivity extends AppCompatActivity {

    String[] plantModelTest = new String[]{"Succulent", "Orchid", "Cactus", "Raflesia", "Rosemary" };
    ListView listView;
    ArrayAdapter<String> adapter;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        listView = findViewById(R.id.plantListView);
        adapter = new ArrayAdapter<String>(PlantsActivity.this, R.layout.row_plant, plantModelTest);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textOut = (TextView) view;
                Toast.makeText(PlantsActivity.this, textOut.getText() + " " + position,
                        Toast.LENGTH_LONG).show();
                //TODO: create intent to PlantDetail activity
            }
        });

    }
}
