package com.wmp.android.wetmyplants.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.model.Sensor;
import com.wmp.android.wetmyplants.restAdapter.Communicator;

public class DashboardActivity extends AppCompatActivity {

    /**Declaring related classes*/
    private Communicator communicator;
    private final static String TAG = "DashboardActivity";

    /**UI references*/
    private ImageView mAccountButton;
    private ImageView mMyPlantsButton;
    private ImageView mSensorsButton;
    private ImageView mNotificationButton;
    private ImageView mSettingButton;
    private ImageView mLogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAccountButton = findViewById(R.id.accountImageView);
        mAccountButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intentToAccount = new Intent(
                        DashboardActivity.this, AccountActivity.class);
                startActivity(intentToAccount);
            }
        });

        mMyPlantsButton = findViewById(R.id.plantImageView);
        mMyPlantsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intentToPlants = new Intent(
                        DashboardActivity.this, PlantsActivity.class);
                startActivity(intentToPlants);
            }
        });

        mSensorsButton = findViewById(R.id.sensorImageView);
        mSensorsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intentToSensors = new Intent(
                        DashboardActivity.this, SensorsActivity.class);
                startActivity(intentToSensors);
            }
        });

        mNotificationButton = findViewById(R.id.notificationImageView);
        mNotificationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intentToNotification = new Intent(
                        DashboardActivity.this, NotificationActivity.class);
                startActivity(intentToNotification);
            }
        });

        mSettingButton = findViewById(R.id.settingImageView);
        mSettingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intentToSetting = new Intent(
                        DashboardActivity.this, SettingActivity.class);
                startActivity(intentToSetting);
            }
        });

        mLogoutButton = findViewById(R.id.logoutImageView);
        mLogoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
