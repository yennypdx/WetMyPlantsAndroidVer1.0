package com.wmp.android.wetmyplants.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.wmp.android.wetmyplants.R;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Populate the dropdown menu for selecting a color theme
        Spinner spinner = (Spinner) findViewById(R.id.color_selection_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.color_theme_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void onNotificationCheckboxClicked(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_email_notifications:
                if (checked) {
                    // Send email notifications
                }
                else {
                    // DO NOT send email notifications
                }
            case R.id.checkbox_text_notifications:
                if (checked) {
                    // Send text notifications
                }
                else {
                    // DO NOT send text notifications
                }
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}

