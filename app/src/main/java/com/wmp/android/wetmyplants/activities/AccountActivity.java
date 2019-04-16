package com.wmp.android.wetmyplants.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.model.Account;
import com.wmp.android.wetmyplants.restAdapter.Communicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    private Communicator communicator;
    SharedPreferences sharedpref;
    String storedToken;
    Gson gson;

    TextView displayFirstName;
    TextView displayLastName;
    TextView displayPhone;
    TextView displayEmail;
    TextView displayPlantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        communicator = new Communicator();
        gson = new Gson();

        displayFirstName = findViewById(R.id.firstTextView);
        displayLastName = findViewById(R.id.lastTextView);
        displayPhone = findViewById(R.id.phoneTextView);
        displayEmail = findViewById(R.id.emailTextView);
        displayPlantId = findViewById(R.id.plantIdTextView);

        storedToken = sharedpref.getString("Token", "");
        pullUserInformation(storedToken);

        FloatingActionButton editAccFab = findViewById(R.id.edit_fab);
        editAccFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(
                        AccountActivity.this, AccountEditActivity.class));
            }
        });
    }

    public void pullUserInformation(String inToken){
        communicator.userDetailGet(inToken, new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                if(response.isSuccessful()) {
                    String UserObject = response.body().getAsJsonObject().toString();
                    Account userAccount = gson.fromJson(UserObject, Account.class);

                    //display the properties to content view
                    displayData(userAccount);
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
                startActivity(new Intent(
                        AccountActivity.this, DashboardActivity.class));
            }
        });
    }

    public void displayData(Account inUserData){
        displayFirstName.setText(inUserData.getFirstName());
        displayLastName.setText(inUserData.getLastName());
        displayPhone.setText(inUserData.getPhoneNumber());
        displayEmail.setText(inUserData.getEmail());
        displayPlantId.setText(inUserData.getId());
    }
}
