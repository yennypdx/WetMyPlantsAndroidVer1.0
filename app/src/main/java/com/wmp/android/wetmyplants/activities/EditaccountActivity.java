package com.wmp.android.wetmyplants.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.restAdapter.Communicator;

public class EditaccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editaccount);


        Button updateButton = findViewById(R.id.update_user_button);
        updateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO: attemp to update the DB onboard and send to server

            }
        });
    }

    private void attemptUpdateAccount()
    {

    }
}
