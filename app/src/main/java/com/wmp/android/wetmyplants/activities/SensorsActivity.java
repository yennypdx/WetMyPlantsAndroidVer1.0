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

public class SensorsActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        String[] sensorModelTest = new String[] { "Sensor 1: 23AEX-6T579", "Sensor 2: 5RTY7-98UIJ",
                "Sensor 3: GHJ7-Y789G", "Sensor 4: 76HG-YTJ55" };

        listView = findViewById(R.id.sensorListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SensorsActivity.this,
                R.layout.row_sensor, R.id.sensor_desc, sensorModelTest);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textOut = (TextView) view;
                Toast.makeText(SensorsActivity.this,
                        textOut.getText() + " " + position, Toast.LENGTH_LONG).show();
                //TODO: create intent to SensorDetail activity
            }
        });
    }
}
