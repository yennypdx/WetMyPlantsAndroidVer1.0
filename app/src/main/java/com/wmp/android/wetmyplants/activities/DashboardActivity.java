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

    /**UI references*/
    private String emailKeyBox;
    private TextView emailPlaceholder;
    private ImageView mAccountButton;
    private ImageView mMyPlantsButton;
    private ImageView mNotificationButton;
    private ImageView mSettingButton;
    private ImageView mLogoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //extract key from
        Intent extractEmailForPlaceholder = getIntent();
        emailKeyBox = extractEmailForPlaceholder.getExtras().getString("emailKey");
        emailPlaceholder = findViewById(R.id.emailPlaceholder);
        emailPlaceholder.setText(emailKeyBox);

        mAccountButton = findViewById(R.id.accountImageView);
        mAccountButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //Pass the emailKey to Dashboard
                Intent key = new Intent(
                        DashboardActivity.this, AccountActivity.class);
                //key.putExtra("emailKey", emailKeyBox);
                startActivity(key);
            }
        });

        mMyPlantsButton = findViewById(R.id.plantImageView);
        mMyPlantsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //TODO: pass the emailKey
                Intent intentToPlants = new Intent(
                        DashboardActivity.this, PlantsActivity.class);
                startActivity(intentToPlants);
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
                //TODO: pass the emailKey
                Intent intentToSetting = new Intent(
                        DashboardActivity.this, SettingsActivity.class);
                startActivity(intentToSetting);
            }
        });

        mLogoutButton = findViewById(R.id.logoutImageView);
        mLogoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent logoutToLogin = new Intent(
                        DashboardActivity.this, LoginActivity.class);
                startActivity(logoutToLogin);
                finish();
            }
        });
    }

}
