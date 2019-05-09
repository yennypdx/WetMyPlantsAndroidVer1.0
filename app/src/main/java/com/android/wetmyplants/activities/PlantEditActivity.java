package com.android.wetmyplants.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.wetmyplants.helperClasses.DbHelper;
import com.android.wetmyplants.model.UserCredentials;
import com.android.wetmyplants.R;
import com.android.wetmyplants.restAdapter.Communicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantEditActivity extends AppCompatActivity {

    private Communicator communicator;
    private DbHelper database;
    String storedToken;

    EditText inputName, inputPlantId;
    String outNewPlantName, outNewPlantId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantedit);
        communicator = new Communicator();
        database = new DbHelper(getApplicationContext());

        Intent getEmail = getIntent();
        final String userEmail = getEmail.getStringExtra("userEmail");
        UserCredentials user = database.getUserCredential(userEmail);
        storedToken = user.getToken();

        inputName = findViewById(R.id.plantEditNameInput);
        inputPlantId = findViewById(R.id.plantEditIdInput);

        Button updateBtn = findViewById(R.id.update_plant_info_button);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outNewPlantName = inputName.getText().toString();
                outNewPlantId = inputPlantId.getText().toString();

                attemptUpdatePlant(storedToken, outNewPlantId, outNewPlantName);

                startActivity(new Intent(PlantEditActivity.this, PlantsActivity.class));
            }
        });
    }

    public void attemptUpdatePlant( String inToken, String name, String sensorId){
        communicator.plantUpdatePut(inToken, name, sensorId, new Callback<okhttp3.Response>(){
            @Override
            public void onResponse(Call<okhttp3.Response> call, Response<okhttp3.Response> response){
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Plant updated", Toast.LENGTH_LONG).show();
                }
                else{
                    Log.e("Error Code", String.valueOf(response.code()));
                    Log.e("Error Body", response.errorBody().toString());
                    Toast.makeText(getApplicationContext(),
                            "Unable to connect to Server. Please try again later.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<okhttp3.Response> call, Throwable t){
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
