package com.wmp.android.wetmyplants.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.wmp.android.wetmyplants.R;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CheckBox emailCb;
    CheckBox textCb;
    CheckBox nonCb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Notification opt
        emailCb = findViewById(R.id.checkbox_email_notifications);
        emailCb.setChecked(false);
        emailCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                emailCb.setChecked(true);
            }
        });

        textCb = findViewById(R.id.checkbox_text_notifications);
        textCb.setChecked(false);
        textCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                textCb.setChecked(true);
            }
        });

        //nonCb = findViewById(R.id.no_notifications);
        //nonCb.setChecked(false);
        //nonCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        //    @Override
        //    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //        nonCb.setChecked(true);
        //    }
        //});

        attemptNotifySystem();

        // Populate the dropdown menu for selecting a color theme
        Spinner spinner = findViewById(R.id.color_selection_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.color_theme_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void attemptNotifySystem(){
        if(emailCb.isChecked()){

        }
        else if(textCb.isChecked()){

        }
        else if(emailCb.isChecked() && textCb.isChecked()){

        }
        else if(nonCb.isChecked()){

        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}

