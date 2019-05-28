package com.android.wetmyplants.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.android.wetmyplants.R;
import com.android.wetmyplants.restAdapter.Communicator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Communicator communicator;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        communicator = new Communicator();

        EditText emailInput = findViewById(R.id.email_pinconfirm);
        final String emailOut = emailInput.getText().toString();
        EditText pinInput = findViewById(R.id.pinconfirm);
        String tempPin = pinInput.getText().toString();
        final int pinOut = Integer.parseInt(tempPin);

        Button emailOutBtn = findViewById(R.id.emailBtn_pin);
        emailOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SendGrid
                requestPinViaEmail(emailOut);
            }
        });

        Button textOutBtn = findViewById(R.id.textBtn_pin);
        textOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Twilio
                requestPinViaText(emailOut);
            }
        });

        Button submitPinBtn = findViewById(R.id.submitBtn_pin);
        submitPinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSubmitPin(pinOut, emailOut);
            }
        });
    }

    private void requestPinViaEmail(String inEmail){
        communicator.forgotPasswordSendGridPost(inEmail, new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Email sent", Toast.LENGTH_LONG).show();
                }
                else {
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

    private void requestPinViaText(String inEmail){
        communicator.forgotPasswordRabbitmqPost(inEmail, new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Text sent",Toast.LENGTH_LONG).show();
                }
                else {
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

    private void attemptSubmitPin(int inPin, final String inEmail){
        communicator.submitPinPost(inPin, inEmail, new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){
                if(response.isSuccessful()) {
                    Intent intent = new Intent(ForgotPasswordActivity.this, NewPasswordActivity.class);
                    intent.putExtra("userEmail", inEmail);
                    startActivity(intent);
                }
                else {
                    Log.e("Error Code", String.valueOf(response.code()));
                    Log.e("Error Body", response.errorBody().toString());
                    Toast.makeText(getApplicationContext(),
                            "Unable to connect to Server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t){
                Toast.makeText(getApplicationContext(),
                        "Confirmation failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
