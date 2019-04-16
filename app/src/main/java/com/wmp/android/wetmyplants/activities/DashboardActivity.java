package com.wmp.android.wetmyplants.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wmp.android.wetmyplants.LoginActivity;
import com.wmp.android.wetmyplants.R;

public class DashboardActivity extends AppCompatActivity {

    private ImageView mAccountButton;
    private ImageView mMyPlantsButton;
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
                startActivity(new Intent(
                        DashboardActivity.this, AccountActivity.class));
            }
        });

        mMyPlantsButton = findViewById(R.id.plantImageView);
        mMyPlantsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(
                        DashboardActivity.this, PlantsActivity.class));
            }
        });

        mNotificationButton = findViewById(R.id.notificationImageView);
        mNotificationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(
                        DashboardActivity.this, NotificationActivity.class));
            }
        });

        mSettingButton = findViewById(R.id.settingImageView);
        mSettingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(
                        DashboardActivity.this, SettingsActivity.class));
            }
        });

        mLogoutButton = findViewById(R.id.logoutImageView);
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