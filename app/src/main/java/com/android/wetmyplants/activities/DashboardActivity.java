package com.android.wetmyplants.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.android.wetmyplants.LoginActivity;
import com.android.wetmyplants.R;
import com.android.wetmyplants.model.Notification;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent getEmail = getIntent();
        final String userEmail = getEmail.getStringExtra("userEmail");

        ImageView mAccountButton = findViewById(R.id.accountImageView);
        mAccountButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, AccountActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        ImageView mMyPlantsButton = findViewById(R.id.plantImageView);
        mMyPlantsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, PlantsActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        ImageView mNotificationButton = findViewById(R.id.notificationImageView);
        mNotificationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, NotificationActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        ImageView mSettingButton = findViewById(R.id.settingImageView);
        mSettingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });

        ImageView mHubButton = findViewById(R.id.hubImageView);
        mHubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: confirm what data needed to pull data from the server for the Hubs
                startActivity(new Intent(DashboardActivity.this, HubActivity.class));
            }
        });

        ImageView mLogoutButton = findViewById(R.id.logoutImageView);
        mLogoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(
                        DashboardActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}