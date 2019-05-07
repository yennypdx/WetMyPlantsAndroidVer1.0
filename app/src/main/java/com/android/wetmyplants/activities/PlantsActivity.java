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

import com.android.wetmyplants.helperClasses.DbHelper;
import com.android.wetmyplants.model.UserCredentials;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.android.wetmyplants.R;
import com.android.wetmyplants.helperClasses.PlantsAdapter;
import com.android.wetmyplants.model.Plant;
import com.android.wetmyplants.restAdapter.Communicator;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantsActivity extends AppCompatActivity {

    private Communicator communicator;
    private PlantsAdapter pAdapter;
    private DbHelper database;
    String storedToken;
    Gson gson;

    ArrayList<Plant> plantList;
    Plant plant;
    ListView listView;
    FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        communicator = new Communicator();
        database = new DbHelper(getApplicationContext());
        listView = findViewById(R.id.plantListView);
        gson = new Gson();

        Intent getEmail = getIntent();
        final String userEmail = getEmail.getStringExtra("userEmail");
        UserCredentials user = database.getUserCredential(userEmail);
        storedToken = user.getToken();

        plantList = new ArrayList<>();
        plant = new Plant();
        pAdapter = new PlantsAdapter(this, plantList);

        listView.setAdapter(pAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView idText = findViewById(R.id.plantIdTextView);
                final String plantId = idText.getText().toString();

                plantList = pullPlantDataFromServer(storedToken);
                // push to temp db
                for(Plant p : plantList){
                    database.insertPlant(p, userEmail);
                }

                Intent intent = new Intent(PlantsActivity.this, PlantDetailActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        addBtn = findViewById(R.id.fabplant_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlantsActivity.this, PlantAddActivity.class));
            }
        });
    }

    public ArrayList<Plant> pullPlantDataFromServer(String inToken){
        final ArrayList<Plant> plantDataList = new ArrayList<>();

        communicator.plantListGet(inToken, new Callback<ArrayList<Plant>>(){
            @Override
            public void onResponse(Call<ArrayList<Plant>> call, Response<ArrayList<Plant>> response){
                if(response.isSuccessful()) {

                    ArrayList<Plant> arrayFromServer = response.body();
                    if(arrayFromServer != null){
                        int size = arrayFromServer.size();
                        for(int i = 0; i < size; i++){
                            plantDataList.add(arrayFromServer.get(i));
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
            public void onFailure(Call<ArrayList<Plant>> call, Throwable t){
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return plantDataList;
    }
}
