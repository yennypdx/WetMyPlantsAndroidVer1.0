package com.wmp.android.wetmyplants.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.helperClasses.CustomAdapter;
import com.wmp.android.wetmyplants.model.Account;
import com.wmp.android.wetmyplants.model.Plant;
import com.wmp.android.wetmyplants.model.PlantTestModel;
import com.wmp.android.wetmyplants.restAdapter.Communicator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantsActivity extends AppCompatActivity {

    private Communicator communicator;
    SharedPreferences sharedpref;
    String storedToken;
    Gson gson;

    List<Plant> plantList;
    ListView listView;
    FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        communicator = new Communicator();
        gson = new Gson();
        plantList = new ArrayList<Plant>();

        storedToken = sharedpref.getString("Token", "");
        pullPlantInformation(storedToken);

        String[] plantModelTest = new String[] { "Succulent" };

        listView = findViewById(R.id.plantListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(PlantsActivity.this,
                R.layout.row_plant, R.id.plant_desc, plantModelTest);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //TODO: open activity with detail related to the object
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

    public void pullPlantInformation(String inToken){
        communicator.plantListGet(inToken, new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                if(response.isSuccessful()) {

                    //display the properties to content view
                    displayData();
                }
                else{
                    Log.e("Error Code", String.valueOf(response.code()));
                    Log.e("Error Body", response.errorBody().toString());
                    Toast.makeText(getApplicationContext(),
                            "Unable to connect to Server. Please try again later.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t){
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void displayData(){

    }
}
