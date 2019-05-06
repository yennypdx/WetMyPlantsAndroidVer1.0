package com.android.wetmyplants.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.android.wetmyplants.R;
import com.android.wetmyplants.helperClasses.PlantsAdapter;
import com.android.wetmyplants.model.Plant;
import com.android.wetmyplants.restAdapter.Communicator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantsActivity extends AppCompatActivity {

    private Communicator communicator;
    private PlantsAdapter pAdapter;
    SharedPreferences sharedpref;
    String storedToken;
    Gson gson;

    ArrayList<Plant> plantList;
    ListView listView;
    FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        communicator = new Communicator();
        gson = new Gson();
        plantList = new ArrayList<>();

        storedToken = sharedpref.getString("UserCredentials", "");
        plantList = pullPlantInformation(storedToken);
        listView = findViewById(R.id.plantListView);

        pAdapter = new PlantsAdapter(this, plantList);
        listView.setAdapter(pAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: open activity with detail related to the object
                TextView selectedItem = view.findViewById(R.id.plantIdTextView);
                String itemId = selectedItem.getText().toString();


                Intent viewPlantDetail = new Intent(
                        PlantsActivity.this, PlantDetailActivity.class);
                viewPlantDetail.putExtra("id", itemId);
                startActivity(viewPlantDetail);
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

    public ArrayList<Plant> pullPlantInformation(String inToken){
        final ArrayList<Plant> listPlantData = new ArrayList<>();

        communicator.plantListGet(inToken, new Callback<JsonArray>(){
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response){
                if(response.isSuccessful()) {

                    JsonArray jArrayPlant = response.body().getAsJsonArray();
                    if(jArrayPlant != null){
                        for(int i = 0; i < jArrayPlant.size(); i++){
                            //listPlantData.add(jArrayPlant.get(i));
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
            public void onFailure(Call<JsonArray> call, Throwable t){
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return listPlantData;
    }

}
