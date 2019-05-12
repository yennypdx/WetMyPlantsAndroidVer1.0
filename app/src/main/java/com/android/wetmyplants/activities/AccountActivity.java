package com.android.wetmyplants.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wetmyplants.helperClasses.DbHelper;
import com.android.wetmyplants.model.User;
import com.android.wetmyplants.model.UserCredentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.android.wetmyplants.R;
import com.android.wetmyplants.model.User;
import com.android.wetmyplants.restAdapter.Communicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    private Communicator communicator;
    private DbHelper database;
    private Gson gson;

    TextView FirstNameBox;
    TextView LastNameBox;
    TextView PhoneBox;
    TextView EmailBox;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        communicator = new Communicator();
        database = new DbHelper(getApplicationContext());
        FirstNameBox = findViewById(R.id.firstTextView);
        LastNameBox = findViewById(R.id.lastTextView);
        PhoneBox = findViewById(R.id.phoneTextView);
        EmailBox = findViewById(R.id.emailTextView);

        Intent getEmail = getIntent();
        final String userEmail = getEmail.getStringExtra("userEmail");
        UserCredentials user = database.getUserCredential(userEmail);
        String storedToken = user.getToken();

        pullUserDataFromServer(storedToken);

        FloatingActionButton editAccFab = findViewById(R.id.edit_fab);
        editAccFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, AccountEditActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });
    }

    public void pullUserDataFromServer(String inToken){
        communicator.userDetailGet(inToken, new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                if(response.isSuccessful()) {
                    gson = new GsonBuilder().create();
                    User newUser = gson.fromJson(response.body(), User.class);
                    displayData(newUser);
                }
                else{
                    Log.e("Error Code", String.valueOf(response.code()));
                    Log.e("Error Body", response.errorBody().toString());
                    Toast.makeText(getApplicationContext(),
                            "Unable to connect to Server. Please try again later.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t){
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(
                        AccountActivity.this, DashboardActivity.class));
            }
        });
    }

    public void displayData(User inUser){
        userId = inUser.getId();
        String firstName = inUser.getFirstName();
        FirstNameBox.setText(firstName);
        String lastName = inUser.getLastName();
        LastNameBox.setText(lastName);
        String phone = inUser.getPhone();
        PhoneBox.setText(phone);
        String email = inUser.getEmail();
        EmailBox.setText(email);
    }
}
