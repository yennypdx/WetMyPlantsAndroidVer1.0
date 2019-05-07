package com.android.wetmyplants.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.wetmyplants.helperClasses.DbHelper;
import com.android.wetmyplants.model.UserCredentials;
import com.google.gson.JsonObject;
import com.android.wetmyplants.R;
import com.android.wetmyplants.restAdapter.Communicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends AppCompatActivity {

    private Communicator communicator;
    private DbHelper database;

    EditText inputPass1, inputPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepassword);
        communicator = new Communicator();
        database = new DbHelper(getApplicationContext());

        Intent getEmail = getIntent();
        final String userEmail = getEmail.getExtras().toString();
        UserCredentials user = database.getUserCredential(userEmail);
        final String userToken = user.getToken();

        inputPass1 = findViewById(R.id.passOne);
        final String outPass1 = inputPass1.getText().toString();
        inputPass2 = findViewById(R.id.passTwo);
        final String outPass2 = inputPass2.getText().toString();

        Button updatePwdBtn = findViewById(R.id.updatePasswordBtn);
        updatePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptUpdatePassword(userToken, outPass1, outPass2);
            }
        });
    }

    public void attemptUpdatePassword(String userToken, String inPass1, String inPass2){
        inputPass1.setError(null);
        inputPass2.setError(null);

        boolean cancel = false;
        View focusView = null;

        /**Check for a valid password, if the user entered one.*/
        if (!TextUtils.isEmpty(inPass1) && !isPasswordValid(inPass1)) {
            inputPass1.setError(getString(R.string.error_invalid_password));
            focusView = inputPass1;
            cancel = true;

            if(!TextUtils.isEmpty(inPass2) && !isPasswordValid(inPass2)){
                inputPass2.setError(getString(R.string.error_invalid_password));
                focusView = inputPass2;
                cancel = true;
            }
        }

        if (cancel) {
            focusView.requestFocus();

        }
        else {
            communicator.updatePasswordInternalPost(userToken, inPass2, new Callback<JsonObject>(){
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful()) {
                        startActivity(new Intent(UpdatePasswordActivity.this, AccountActivity.class));
                    }
                    else {
                        Log.e("Error Code", String.valueOf(response.code()));
                        Log.e("Error Body", response.errorBody().toString());
                        Toast.makeText(getApplicationContext(),
                                "Unable to connect to Server. Please try again later.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t){
                    Log.w("Error", t.getMessage());
                    Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(),
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }
}
