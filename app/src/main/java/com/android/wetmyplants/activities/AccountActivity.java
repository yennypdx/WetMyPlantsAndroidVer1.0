package com.android.wetmyplants.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wetmyplants.helperClasses.DbHelper;
import com.android.wetmyplants.model.UserCredentials;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.android.wetmyplants.R;
import com.android.wetmyplants.model.Account;
import com.android.wetmyplants.restAdapter.Communicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    private Communicator communicator;
    private DbHelper database;
    private Gson gson;

    TextView displayFirstName, displayLastName, displayPhone;
    TextView displayEmail, displayPlantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        communicator = new Communicator();
        database = new DbHelper(getApplicationContext());
        gson = new Gson();

        displayFirstName = findViewById(R.id.firstTextView);
        displayLastName = findViewById(R.id.lastTextView);
        displayPhone = findViewById(R.id.phoneTextView);
        displayEmail = findViewById(R.id.emailTextView);
        displayPlantId = findViewById(R.id.plantIdTextView);

        Intent getEmail = getIntent();
        final String userEmail = getEmail.getStringExtra("userEmail");
        UserCredentials user = database.getUserCredential(userEmail);
        pullUserDataFromServer(user.getToken());

        FloatingActionButton editAccFab = findViewById(R.id.edit_fab);
        editAccFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, AccountEditActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });
    }

    public void pullUserDataFromServer(String inToken){
        communicator.userDetailGet(inToken, new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                if(response.isSuccessful()) {
                    String UserObject = response.body().getAsJsonObject().toString();
                    Account userAccount = gson.fromJson(UserObject, Account.class);

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
