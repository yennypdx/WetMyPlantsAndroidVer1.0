package com.android.wetmyplants.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.wetmyplants.R;
import com.android.wetmyplants.helperClasses.DbHelper;
import com.android.wetmyplants.model.User;
import com.android.wetmyplants.model.UserCredentials;
import com.android.wetmyplants.restAdapter.Communicator;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    int notifStatus;
    private Communicator communicator;
    private DbHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        communicator = new Communicator();
        database = new DbHelper(getApplicationContext());
        final CheckBox emailCb = findViewById(R.id.email_checkBox);
        final CheckBox textCb = findViewById(R.id.text_checkBox);
        final CheckBox noneCb = findViewById(R.id.none_checkBox);

        Intent getEmail = getIntent();
        final String userEmail = getEmail.getStringExtra("userEmail");
        UserCredentials user = database.getUserCredential(userEmail);
        String storedToken = user.getToken();

        emailCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                emailCb.setChecked(true);
            }
        });

        textCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                textCb.setChecked(true);
            }
        });

        noneCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                noneCb.setChecked(true);
                emailCb.setChecked(false);
                textCb.setChecked(false);
            }
        });

        if(emailCb.isChecked()){
            notifStatus = 0;
        } else if(textCb.isChecked()){
            notifStatus = 1;
        } else if(emailCb.isChecked() && textCb.isChecked()){
            notifStatus = 2;
        } else if(noneCb.isChecked()){
            notifStatus = 3;
        }

        attemptSetNotificationMode(storedToken, notifStatus);
    }

    public void attemptSetNotificationMode(String inToken, int inNotifStatus){
        communicator.notificationPreferencePost(inToken, inNotifStatus, new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Notification preference set", Toast.LENGTH_LONG).show();
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

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}

