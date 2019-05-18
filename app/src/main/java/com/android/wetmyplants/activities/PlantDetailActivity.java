package com.android.wetmyplants.activities;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wetmyplants.helperClasses.DbHelper;
import com.android.wetmyplants.model.UserCredentials;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.android.wetmyplants.R;
import com.android.wetmyplants.model.Plant;
import com.android.wetmyplants.restAdapter.Communicator;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantDetailActivity extends AppCompatActivity {

    private Communicator communicator;
    private DbHelper database;
    String storedToken;

    FloatingActionButton fab, fabedit, fabdel;
    LinearLayout fablayoutEdit, fablayoutDel;
    TextView menuFabLabel;
    View fabBGLayout;
    boolean isFABOpen = false;

    String sensorId;
    String storedPlantId;
    TextView displayPlantName;
    TextView displaySensorId;
    TextView displayHumidity;
    TextView displayLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantdetail);
        communicator = new Communicator();
        database = new DbHelper(getApplicationContext());

        displayPlantName = findViewById(R.id.plantDetail_nameTextOut);
        displaySensorId = findViewById(R.id.plantDetail_plantIdTextOut);
        displayHumidity = findViewById(R.id.plantDetail_waterTextOut);
        displayLight = findViewById(R.id.plantDetail_lightTextOut);

        Intent getExtras = getIntent();
        final String userEmail = getExtras.getStringExtra("userEmail");
        final String plantName = getExtras.getStringExtra("plantName");

        pullPlantDetailInformation(plantName);

        fablayoutEdit = findViewById(R.id.fabLayoutEdit);
        fabedit = findViewById(R.id.fabEdit);
        fablayoutDel = findViewById(R.id.fabLayoutDelete);
        fabdel = findViewById(R.id.fabDelete);
        fab = findViewById(R.id.fab);
        fabBGLayout = findViewById(R.id.fabBGLayout);
        menuFabLabel = findViewById(R.id.fabmenu);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    menuFabLabel.setVisibility(View.GONE);
                    showFabMenu();
                } else {
                    menuFabLabel.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            menuFabLabel.setVisibility(View.VISIBLE);
                        }
                    }, 500);
                    closeFabMenu();
                }
            }
        });

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFabMenu();
            }
        });

        fabedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        PlantDetailActivity.this, PlantEditActivity.class);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("plantName", plantName);
                intent.putExtra("sensorId", sensorId);
                startActivity(intent);
            }
        });

        fabdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        attemptDeletePlant(storedToken, storedPlantId);

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        startActivity(new Intent(
                                PlantDetailActivity.this, PlantsActivity.class));
                        break;
                }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(PlantDetailActivity.this);
            builder.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
            }
        });

        FloatingActionButton homePlantEditFab = findViewById(R.id.plantDetail_homeFab);
        homePlantEditFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(PlantDetailActivity.this, DashboardActivity.class);
                intent2.putExtra("userEmail", userEmail);
                startActivity(intent2);
            }
        });
    }

    private void pullPlantDetailInformation(String inName){
        Plant plant = database.getPlant(inName);

        displayPlantName.setText(inName);
        String id = plant.getId();
        sensorId = id;
        displaySensorId.setText(id);
        Double tempWater = plant.getCurrentWater();
        String.format("%1$.2f", tempWater);
        String water = Double.toString(tempWater);
        displayHumidity.setText(water);
        Double tempLight = plant.getCurrentLight();
        String light = Double.toString(tempLight);
        displayLight.setText(light);

        database.close();
    }

    public void attemptDeletePlant(String inToken, String plantId){
        communicator.plantDelete(inToken, plantId, new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Plant deleted",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Log.e("Error Code", String.valueOf(response.code()));
                    Log.e("Error Body", response.errorBody().toString());
                    Toast.makeText(getApplicationContext(),
                            "Unable to connect to Server. Please try again later.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t){
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showFabMenu(){
        isFABOpen = true;
        fablayoutEdit.setVisibility(View.VISIBLE);
        fablayoutDel.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                if(fab.getRotation() != 180){
                    fab.setRotation(180);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        fablayoutEdit.animate().translationY(-getResources().getDimension(R.dimen.stand_55));
        fablayoutDel.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
    }

    private void closeFabMenu(){
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        fablayoutEdit.animate().translationY(0);
        fablayoutDel.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                if(!isFABOpen){
                    fablayoutEdit.setVisibility(View.GONE);
                    fablayoutDel.setVisibility(View.GONE);
                }

                if(fab.getRotation() != -180){
                    fab.setRotation(-180);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }

    @Override
    public void onBackPressed(){
        if(isFABOpen){
            closeFabMenu();
        } else {
            super.onBackPressed();
        }
    }
}
