package com.android.wetmyplants.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private DbHelper database;

    SwipeRefreshLayout pullToRefresh;
    String storedToken;
    List<Plant> plantList;
    ListView plantListView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        communicator = new Communicator();
        database = new DbHelper(getApplicationContext());
        plantListView = findViewById(R.id.plantListView);
        plantList = new ArrayList<>();
        pullToRefresh = findViewById(R.id.pullToRefresh);

        Intent getEmail = getIntent();
        final String userEmail = getEmail.getStringExtra("userEmail");
        final UserCredentials user = database.getUserCredential(userEmail);
        storedToken = user.getToken();

        pullPlantDataFromServer(storedToken, userEmail);
        plantList = database.getAllPlants(userEmail);

        final PlantRowAdapter plantRowAdapter = new PlantRowAdapter(getApplicationContext(), plantList);
        plantListView.setAdapter(plantRowAdapter);
        plantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView temp = view.findViewById(R.id.plantNameTextView);
                final String plantName = temp.getText().toString();

                Intent intent = new Intent(PlantsActivity.this, PlantDetailActivity.class);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("plantName", plantName);
                startActivity(intent);
            }
        });

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                plantList = pullPlantDataFromServer(storedToken, userEmail);
                plantRowAdapter.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);
            }
        });

        FloatingActionButton addPlantBtn = findViewById(R.id.fabplant_add);
        addPlantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PlantsActivity.this, PlantAddActivity.class);
                intent1.putExtra("userEmail", userEmail);
                startActivity(intent1);
            }
        });

        FloatingActionButton homePlantFab = findViewById(R.id.plant_homeFab);
        homePlantFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(PlantsActivity.this, DashboardActivity.class);
                intent2.putExtra("userEmail", userEmail);
                startActivity(intent2);
            }
        });
    }

    public List<Plant> pullPlantDataFromServer(String inToken, final String userEmail){
        final List<Plant> outPlantList = new ArrayList<>();
        communicator.plantListGet(inToken, new Callback<List<Plant>>(){
            @Override
            public void onResponse(Call<List<Plant>> call, Response<List<Plant>> response){
                if(response.isSuccessful()) {
                    List<Plant> plantsNew = response.body();
                    int listLength = plantsNew.size();

                    database.deletePlants(userEmail);
                    for(int p = 0; p < listLength; p++){
                        database.insertPlant(plantsNew.get(p), userEmail);
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
        return outPlantList;
    }
}
