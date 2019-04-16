package com.wmp.android.wetmyplants.activities;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.model.Plant;
import com.wmp.android.wetmyplants.restAdapter.Communicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantDetailActivity extends AppCompatActivity {

    private Communicator communicator;
    SharedPreferences sharedpref;
    String storedToken;
    Gson gson;

    FloatingActionButton fab, fabedit, fabdel;
    LinearLayout fablayoutEdit, fablayoutDel;
    TextView menuFabLabel;
    View fabBGLayout;
    boolean isFABOpen = false;
    
    TextView displayPlantName;
    TextView displaySpecies;
    TextView displayHumidity;
    TextView displayLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantdetail);
        communicator = new Communicator();
        gson = new Gson();

        storedToken = sharedpref.getString("Token", "");
        pullPlantDetailInformation(storedToken);

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
                Intent toPlantEdit = new Intent(
                        PlantDetailActivity.this, PlantEditActivity.class);
                startActivity(toPlantEdit);
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
                        //TODO: proceed to delete data via Retrofit!

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        Intent goToPlantList = new Intent(
                                PlantDetailActivity.this, PlantsActivity.class);
                        startActivity(goToPlantList);
                        break;
                }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(PlantDetailActivity.this);
            builder.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
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

    private void pullPlantDetailInformation(String inToken){
        communicator.userDetailGet(inToken, new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                if(response.isSuccessful()) {
                    String PlantObject = response.body().getAsJsonObject().toString();
                    Plant plantData = gson.fromJson(PlantObject, Plant.class);

                    //display the properties to content view
                    displayData(plantData);
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

    public void displayData(Plant inPlantData){
        displayPlantName.setText(inPlantData.getNickname());
        displaySpecies.setText(inPlantData.getSpecies());
        displayHumidity.setText(inPlantData.getCurrentWater().toString());
        displayLight.setText(inPlantData.getCurrentLight().toString());
    }

}
