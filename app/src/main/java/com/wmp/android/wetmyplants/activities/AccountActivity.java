package com.wmp.android.wetmyplants.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.helperClasses.DbAdapter;
import com.wmp.android.wetmyplants.model.User;
import com.wmp.android.wetmyplants.restAdapter.Communicator;

public class AccountActivity extends AppCompatActivity {

    /**Declaring related classes*/
    private Communicator communicator;
    private User user;

    /**UI references*/
    private TextView outFirstName;
    private TextView outLastName;
    private TextView outPhone;
    private TextView outEmail;
    private TextView outPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        communicator = new Communicator();
        user = new User();

        outFirstName = findViewById(R.id.firstTextView);
        outFirstName.setText(user.getFirstName());

        outLastName = findViewById(R.id.lastTextView);
        outLastName.setText(user.getLastName());

        outPhone = findViewById(R.id.phoneTextView);
        outPhone.setText(user.getPhoneNumber());

        outEmail = findViewById(R.id.emailTextView);
        outEmail.setText(user.getEmail());

        outPassword = findViewById(R.id.passTextView);
        outPassword.setText(user.getPassword());

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

}
