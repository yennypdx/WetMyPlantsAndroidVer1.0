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

        //syncing with website preference
        setLatestNotificationSetting(storedToken, userEmail);
        //get latest status from db
        int latestStatus = database.getNotificationStatus(userEmail);
        switch (latestStatus){
            case 0:
                emailCb.setChecked(true);
                textCb.setChecked(false);
                noneCb.setChecked(false);
            case 1:
                emailCb.setChecked(false);
                textCb.setChecked(true);
                noneCb.setChecked(false);
                break;
            case 2:
                emailCb.setChecked(true);
                textCb.setChecked(true);
                noneCb.setChecked(false);
                break;
            case 3:
                emailCb.setChecked(false);
                textCb.setChecked(false);
                noneCb.setChecked(true);
                break;
        }

        emailCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    emailCb.setChecked(true);
                } else{
                    emailCb.setChecked(false);
                }
            }
        });

        textCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    textCb.setChecked(true);
                } else{
                    textCb.setChecked(false);
                }
            }
        });

        noneCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    noneCb.setChecked(true);
                    emailCb.setChecked(false);
                    textCb.setChecked(false);
                }
                else{
                    noneCb.setChecked(false);
                }
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

        //when modification were made, this method should run
        attemptSetNotificationMode(storedToken, notifStatus);
    }

    public void attemptSetNotificationMode(String inToken, int inNotifStatus){
        communicator.notificationPreferencePost(inToken, inNotifStatus, new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "MessageLog preference set", Toast.LENGTH_LONG).show();
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

    public void setLatestNotificationSetting(String token, final String userEmail){
        communicator.notificationPreferenceGet(token, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    String content = response.body().toString();
                    int status = Integer.parseInt(content);
                    database.deleteNotificationStatusFromDb(userEmail);
                    database.insertNotificationStatus(status, userEmail);
                }
                else{
                    Log.e("Error Code", String.valueOf(response.code()));
                    Log.e("Error Body", response.errorBody().toString());
                    Toast.makeText(getApplicationContext(),
                            "Unable to connect to Server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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

