package com.wmp.android.wetmyplants.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wmp.android.wetmyplants.R;

public class AccountActivity extends AppCompatActivity {

    private TextView firstNameDisplay;
    private TextView lastNameDisplay;
    private TextView phoneDisplay;
    private TextView emailDisplay;
    private TextView passwordDisplay;
    private static Context accountContext;
    //LoadAccountTask loadAccountTask;
    //String emailToCompare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_content);
        accountContext.getApplicationContext();

        firstNameDisplay = findViewById(R.id.firstTextView);
        lastNameDisplay = findViewById(R.id.lastTextView);
        phoneDisplay = findViewById(R.id.phoneTextView);
        emailDisplay = findViewById(R.id.emailTextView);
        passwordDisplay = findViewById(R.id.passTextView);

        //Intent extractKey = getIntent();
        //emailToCompare = extractKey.getExtras().getString("emailKey");
        //loadAccountTask = new LoadAccountTask();
        //loadAccountTask.execute();

        FloatingActionButton fab = findViewById(R.id.edit_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Edit Account", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent toEditAccount = new Intent(AccountActivity.this,
                        EditaccountActivity.class);
                startActivity(toEditAccount);
            }
        });
    }

    private class LoadAccountTask
    {
        //TODO: send GET request to ApiController via Communicator to WebAPI to get User detail

       /**
            int firstNameIndex = result.getColumnIndex("FirstName");
            int lastNameIndex = result.getColumnIndex("LastName");
            int phoneIndex = result.getColumnIndex("PhoneNumber");
            int emailIndex = result.getColumnIndex("Email");
            int passwordIndex = result.getColumnIndex("Password");

            firstNameDisplay.setText(result.getString(firstNameIndex));
            lastNameDisplay.setText(result.getString(lastNameIndex));
            phoneDisplay.setText(result.getString(phoneIndex));
            emailDisplay.setText(result.getString(emailIndex));
            passwordDisplay.setText(result.getString(passwordIndex));
        */
    }

}
