package com.wmp.android.wetmyplants.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.restAdapter.Communicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Communicator communicator;
    public static final String appPREFERENCES = "appPref";
    public static final String Token = "tokenKey";
    public static final String Email = "emailKey";
    SharedPreferences sharedpref;
    SharedPreferences.Editor editor;

    private EditText emailInput;
    private EditText pinInput;
    Button emailOutBtn;
    Button textOutBtn;
    Button submitPinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        communicator = new Communicator();
        sharedpref = getSharedPreferences(appPREFERENCES, Context.MODE_PRIVATE);

        emailInput = findViewById(R.id.email_pinconfirm);
        final String emailOut = emailInput.getText().toString();
        pinInput = findViewById(R.id.pinconfirm);
        final String pinOut = pinInput.getText().toString();

        emailOutBtn = findViewById(R.id.emailBtn_pin);
        emailOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //attempt sending request for recovery text with PIN via SendGrid
                sendPinViaEmail(emailOut);
            }
        });

        textOutBtn = findViewById(R.id.textBtn_pin);
        textOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //attempt sending request for recovery text with PIN via SendGrid
                sendPinViaText(emailOut);
            }
        });

        submitPinBtn = findViewById(R.id.submitBtn_pin);
        submitPinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSubmitPin(pinOut);
            }
        });
    }

    private void sendPinViaEmail(String inEmail){
        communicator.forgotPasswordSendGridPost(inEmail, new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Sending email", Toast.LENGTH_LONG).show();
                }
                else {
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

    private void sendPinViaText(String inEmail){
        communicator.forgotPasswordRabbitmqPost(inEmail, new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Sending text", Toast.LENGTH_LONG).show();
                }
                else {
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

    private void attemptSubmitPin(String inPin){
        communicator.submitPinPost(inPin, new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                if(response.isSuccessful()) {
                    startActivity(new Intent(
                            ForgotPasswordActivity.this, NewPasswordActivity.class));
                }
                else {
                    Log.e("Error Code", String.valueOf(response.code()));
                    Log.e("Error Body", response.errorBody().toString());
                    Toast.makeText(getApplicationContext(),
                            "Unable to connect to Server. Please try again later.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t){
                Toast.makeText(getApplicationContext(),
                        "Confirmation failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
