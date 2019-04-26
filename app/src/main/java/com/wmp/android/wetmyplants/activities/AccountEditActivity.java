package com.wmp.android.wetmyplants.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.model.Account;
import com.wmp.android.wetmyplants.restAdapter.Communicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountEditActivity extends AppCompatActivity {

    private Communicator communicator;
    SharedPreferences sharedpref;
    String storedToken;
    Gson gson;

    EditText inputFirstName;
    EditText inputLastName;
    EditText inputPhone;
    EditText inputEmail;
    EditText inputPlantId;

    String defaultFirstName;
    String defaultLastName;
    String defaultPhone;
    String defaultEmail;
    String defaultPlantId;

    String finalFirstName;
    String finalLastName;
    String finalPhone;
    String finalEmail;
    String finalPlantId;

    Account updatedUser;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountedit);
        gson = new Gson();
        communicator = new Communicator();

        storedToken = sharedpref.getString("Token", "");
        pullUserInformation(storedToken);

        inputFirstName = findViewById(R.id.firstEditText);
        inputFirstName.setEnabled(false);
        inputLastName = findViewById(R.id.lastEditText);
        inputLastName.setEnabled(false);
        inputPhone = findViewById(R.id.phoneEditText);
        inputPhone.setEnabled(false);
        inputEmail = findViewById(R.id.emailEditText);
        inputEmail.setEnabled(false);
        inputPlantId = findViewById(R.id.plantIdEditText);
        inputPlantId.setEnabled(false);

        ImageView editFistNameBtn = findViewById(R.id.editFistNameBtn);
        editFistNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputFirstName.setEnabled(true);
                finalFirstName = inputFirstName.getText().toString();
            }
        });

        ImageView editLastNameBtn = findViewById(R.id.editLastNameBtn);
        editLastNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputLastName.setEnabled(true);
                finalLastName = inputLastName.getText().toString();
            }
        });

        ImageView editPhoneBtn = findViewById(R.id.editPhoneBtn);
        editPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputPhone.setEnabled(true);
                finalPhone = inputPhone.getText().toString();
            }
        });

        ImageView editEmailBtn = findViewById(R.id.editEmailBtn);
        editEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEmail.setEnabled(true);
                finalEmail = inputEmail.getText().toString();
            }
        });

        ImageView editPlantIdBtn = findViewById(R.id.editPlantIdBtn);
        editPlantIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputPlantId.setEnabled(true);
                finalPlantId = inputPlantId.getText().toString();
            }
        });

        Button accountEditBtn = findViewById(R.id.updateUserBtn);
        accountEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptEditAccount(storedToken,updatedUser);
            }
        });

        Button updatePasswordBtn = findViewById(R.id.updatePasswordBtn);
        updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        AccountEditActivity.this, UpdatePasswordActivity.class));
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
                    updatedUser = getDefaultValue(userAccount);
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
                        AccountEditActivity.this, DashboardActivity.class));
            }
        });
    }

    public Account getDefaultValue(Account inUserData){
        inputFirstName.setText(inUserData.getFirstName());
        defaultFirstName = inputFirstName.getText().toString();
        finalFirstName = defaultFirstName;

        inputLastName.setText(inUserData.getLastName());
        defaultLastName = inputLastName.getText().toString();
        finalLastName = defaultLastName;

        inputPhone.setText(inUserData.getPhoneNumber());
        defaultPhone = inputPhone.getText().toString();
        finalPhone = defaultPhone;

        inputEmail.setText(inUserData.getEmail());
        defaultEmail = inputEmail.getText().toString();
        finalEmail = defaultEmail;

        inputPlantId.setText(inUserData.getId());
        defaultPlantId = inputPlantId.getText().toString();
        finalPlantId = defaultPlantId;

        Account outAccount = new Account(finalPlantId, finalFirstName, finalLastName,
                finalPhone, finalEmail);
        return outAccount;
    }

    public void attemptEditAccount(String inToken, Account inUser){
        communicator.userUpdatePut(inToken, inUser, new Callback<okhttp3.Response>(){
            @Override
            public void onResponse(Call<okhttp3.Response> call, Response<okhttp3.Response> response){
                if(response.isSuccessful()) {
                    startActivity(new Intent(AccountEditActivity.this, AccountActivity.class));
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
