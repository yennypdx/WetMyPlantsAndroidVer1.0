package com.android.wetmyplants.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.android.wetmyplants.R;

public class SettingsActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    int notifStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final CheckBox emailCb = findViewById(R.id.email_checkBox);
        final CheckBox textCb = findViewById(R.id.text_checkBox);
        final CheckBox noneCb = findViewById(R.id.none_checkBox);

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

        attemptSetNotificationMode(notifStatus);
    }

    public void attemptSetNotificationMode(int inNotifStatus){

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}

