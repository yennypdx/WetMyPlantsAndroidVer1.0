package com.wmp.android.wetmyplants.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.helperClasses.DatabaseConnector;

public class AccountActivity extends AppCompatActivity {

    private TextView firstNameDisplay;
    private TextView lastNameDisplay;
    private TextView phoneDisplay;
    private TextView emailDisplay;
    private TextView passwordDisplay;
    LoadAccountTask loadAccountTask;
    String emailToCompare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        firstNameDisplay = findViewById(R.id.firstTextView);
        lastNameDisplay = findViewById(R.id.lastTextView);
        phoneDisplay = findViewById(R.id.phoneTextView);
        emailDisplay = findViewById(R.id.emailTextView);
        passwordDisplay = findViewById(R.id.passTextView);

        Intent extractKey = getIntent();
        emailToCompare = extractKey.getExtras().getString("emailKey");

        loadAccountTask = new LoadAccountTask();
        loadAccountTask.execute();

        ImageView editAccountButton = findViewById(R.id.userEditImageView);
        editAccountButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intentToEditAccount = new Intent(
                        AccountActivity.this, EditaccountActivity.class);
                startActivity(intentToEditAccount);
            }
        });
    }

    private class LoadAccountTask extends AsyncTask<Object, Object, Cursor>
    {
        DatabaseConnector databaseConnector =
                new DatabaseConnector(AccountActivity.this);

        @Override
        protected Cursor doInBackground(Object... params)
        {
            databaseConnector.open();
            return databaseConnector.loadOneUser(emailToCompare);
        }

        @Override
        protected void onPostExecute(Cursor result)
        {
            super.onPostExecute(result);
            result.moveToFirst();

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

            result.close();
            databaseConnector.close();
        }
    }

}
