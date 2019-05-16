package com.android.wetmyplants.activities;

import android.content.Intent;
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

import com.android.wetmyplants.helperClasses.DbHelper;
import com.android.wetmyplants.model.PlantRow;
import com.android.wetmyplants.model.UserCredentials;
import com.google.gson.Gson;
import com.android.wetmyplants.R;
import com.android.wetmyplants.helperClasses.PlantRowAdapter;
import com.android.wetmyplants.model.Plant;
import com.android.wetmyplants.restAdapter.Communicator;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantsActivity extends AppCompatActivity {

    private Communicator communicator;
    private PlantRowAdapter plantRowAdapter;
    private DbHelper database;
    String storedToken;

    List<Plant> plantList;
    Plant plant;
    ListView plantListView;
    FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        communicator = new Communicator();
        database = new DbHelper(getApplicationContext());
        plantListView = findViewById(R.id.plantListView);
        plantList = new ArrayList<>();

        Intent getEmail = getIntent();
        final String userEmail = getEmail.getStringExtra("userEmail");
        UserCredentials user = database.getUserCredential(userEmail);
        storedToken = user.getToken();

        pullPlantDataFromServer(storedToken, userEmail);
        plantList = database.getAllPlants(userEmail);

        plantRowAdapter = new PlantRowAdapter(getApplicationContext(), plantList);

        plantListView.setAdapter(plantRowAdapter);
        plantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView idText = findViewById(R.id.plantIdTextView);
                final String plantId = idText.getText().toString();

                Intent intent = new Intent(PlantsActivity.this, PlantDetailActivity.class);
                intent.putExtra("plantId", plantId);
                startActivity(intent);
            }
        });

        addBtn = findViewById(R.id.fabplant_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlantsActivity.this, PlantAddActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        FloatingActionButton homeAccFab = findViewById(R.id.plant_homeFab);
        homeAccFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlantsActivity.this, DashboardActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });
    }

    public void pullPlantDataFromServer(String inToken, final String userEmail){
        final List<Plant> plantListOut = new ArrayList<>();

        communicator.plantListGet(inToken, new Callback<List<Plant>>(){
            @Override
            public void onResponse(Call<List<Plant>> call, Response<List<Plant>> response){
                if(response.isSuccessful()) {
                    List<Plant> plantsNew = response.body();
                    // email NOT found in db insert all detail
                    if(!database.isEmailExist(userEmail)) {
                        for (Plant p : plantsNew) {
                            database.insertPlant(p, userEmail);
                        }
                    }
                    else{ //email found in db only update water and light
                        for (Plant p : plantsNew) {
                            database.updatePartsOfPlantData(p, userEmail);
                        }
                    }
                }
                else{
                    Log.e("Error Code", String.valueOf(response.code()));
                    Log.e("Error Body", response.errorBody().toString());
                    Toast.makeText(getApplicationContext(),
                            "Unable to connect to Server. Please try again later.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Plant>> call, Throwable t){
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
