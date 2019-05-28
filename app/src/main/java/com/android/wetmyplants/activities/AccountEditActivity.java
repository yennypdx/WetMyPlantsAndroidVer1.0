package com.android.wetmyplants.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.wetmyplants.helperClasses.DbHelper;
import com.android.wetmyplants.model.User;
import com.android.wetmyplants.model.UserCredentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.android.wetmyplants.R;
import com.android.wetmyplants.model.User;
import com.android.wetmyplants.restAdapter.Communicator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountEditActivity extends AppCompatActivity {
    private Communicator communicator;
    private DbHelper database;
    private Gson gson;

    EditText inputFirstName, inputLastName, inputPhone;
    EditText inputEmail, inputPass;
    String defaultFirstName, defaultLastName, defaultPhone, defaultEmail;
    String finalFirstName, finalLastName, finalPhone, finalEmail;
    User updatedUser;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountedit);
        communicator = new Communicator();
        database = new DbHelper(getApplicationContext());
        gson = new Gson();

        Intent getExtras = getIntent();
        final String userEmail = getExtras.getStringExtra("userEmail");
        final int finalUserId = getExtras.getIntExtra("userId", 0);

        UserCredentials user = database.getUserCredential(userEmail);
        final String storedToken = user.getToken();
        pullUserDataFromServer(storedToken);

        inputFirstName = findViewById(R.id.firstEditText);
        inputFirstName.setEnabled(false);
        inputLastName = findViewById(R.id.lastEditText);
        inputLastName.setEnabled(false);
        inputPhone = findViewById(R.id.phoneEditText);
        inputPhone.setEnabled(false);
        inputEmail = findViewById(R.id.emailEditText);
        inputEmail.setEnabled(false);

        ImageView editFistNameBtn = findViewById(R.id.editFistNameBtn);
        editFistNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputFirstName.setEnabled(true);
                inputLastName.setEnabled(false);
                inputPhone.setEnabled(false);
                inputEmail.setEnabled(false);
            }
        });

        ImageView editLastNameBtn = findViewById(R.id.editLastNameBtn);
        editLastNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputLastName.setEnabled(true);
                inputFirstName.setEnabled(false);
                inputPhone.setEnabled(false);
                inputEmail.setEnabled(false);
            }
        });

        ImageView editPhoneBtn = findViewById(R.id.editPhoneBtn);
        editPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputPhone.setEnabled(true);
                inputFirstName.setEnabled(false);
                inputLastName.setEnabled(false);
                inputEmail.setEnabled(false);
            }
        });

        ImageView editEmailBtn = findViewById(R.id.editEmailBtn);
        editEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEmail.setEnabled(true);
                inputFirstName.setEnabled(false);
                inputLastName.setEnabled(false);
                inputPhone.setEnabled(false);
            }
        });

        Button accountEditBtn = findViewById(R.id.updateUserBtn);
        accountEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalFirstName = inputFirstName.getText().toString();
                finalLastName = inputLastName.getText().toString();
                finalPhone = inputPhone.getText().toString();
                finalEmail = inputEmail.getText().toString();
                updatedUser = new User(finalUserId, finalFirstName, finalLastName, finalPhone, finalEmail);

                attemptEditAccount(storedToken, userEmail, updatedUser);
            }
        });

        Button updatePasswordBtn = findViewById(R.id.updatePasswordBtn);
        updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        AccountEditActivity.this, UpdatePasswordActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        FloatingActionButton homeAccFab = findViewById(R.id.editAccount_homeFab);
        homeAccFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountEditActivity.this, DashboardActivity.class);
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
                    gson = new GsonBuilder().create();
                    User newUser = gson.fromJson(response.body(), User.class);
                    getDefaultValue(newUser);
                }
                else{
                    Log.e("Error Code", String.valueOf(response.code()));
                    Log.e("Error Body", response.errorBody().toString());
                    Toast.makeText(getApplicationContext(),
                            "Unable to connect to Server", Toast.LENGTH_LONG).show();
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

    public User getDefaultValue(User inUserData){
        inputFirstName.setText(inUserData.getFirstName());
        defaultFirstName = inputFirstName.getText().toString();
        finalFirstName = defaultFirstName;

        inputLastName.setText(inUserData.getLastName());
        defaultLastName = inputLastName.getText().toString();
        finalLastName = defaultLastName;

        inputPhone.setText(inUserData.getPhone());
        defaultPhone = inputPhone.getText().toString();
        finalPhone = defaultPhone;

        inputEmail.setText(inUserData.getEmail());
        defaultEmail = inputEmail.getText().toString();
        finalEmail = defaultEmail;

        User outAccount = new User(finalFirstName, finalLastName, finalPhone, finalEmail);
        return outAccount;
    }

    public void attemptEditAccount(String inToken, String inEmail, User inUser){
        final String attemptEmail = inEmail;
        communicator.userUpdatePut(inToken, inUser, new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){
                if(response.isSuccessful()) {
                    Toast.makeText(AccountEditActivity.this,
                            "Account updated", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(AccountEditActivity.this, AccountActivity.class);
                    intent.putExtra("userEmail", attemptEmail);
                    startActivity(intent);
                    finish();
                }
                else{
                    Log.e("Error Code", String.valueOf(response.code()));
                    Log.e("Error Body", response.errorBody().toString());
                    Toast.makeText(getApplicationContext(),
                            "Unable to connect to Server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t){
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
