package com.android.wetmyplants.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import okhttp3.ResponseBody;
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
        final String userEmail = getEmail.getStringExtra("userEmail");

        inputPass1 = findViewById(R.id.passOne);
        inputPass2 = findViewById(R.id.passTwo);

        Button updatePwdBtn = findViewById(R.id.updatePasswordBtn);
        updatePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String outPass1 = inputPass1.getText().toString();
                final String outPass2 = inputPass2.getText().toString();
                attemptUpdatePassword(userEmail, outPass1, outPass2);
            }
        });

        FloatingActionButton homeAccFab = findViewById(R.id.newPass_homeFab);
        homeAccFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdatePasswordActivity.this, DashboardActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });
    }

    public void attemptUpdatePassword(final String userEmail, String inPass1, String inPass2){
        inputPass1.setError(null);
        inputPass2.setError(null);

        boolean cancel = false;
        View focusView = null;

        /**Check for a valid password, if the user entered one.*/
        if (!TextUtils.isEmpty(inPass1) && !isPasswordValid(inPass1)) {
            inputPass1.setError(getString(R.string.error_invalid_password));
            focusView = inputPass1;
            cancel = true;

            if(!TextUtils.isEmpty(inPass2) && !isPasswordValid(inPass2) && !inPass2.equals(inPass1)){
                inputPass2.setError(getString(R.string.error_invalid_password2));
                focusView = inputPass2;
                cancel = true;
            }
        }

        if (cancel) {
            focusView.requestFocus();
        }
        else {
            communicator.updatePasswordInternalPost(userEmail, inPass1, inPass2, new Callback<ResponseBody>(){
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                "Password updated", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UpdatePasswordActivity.this, AccountActivity.class);
                        intent.putExtra("userEmail", userEmail);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Log.e("Error Code", String.valueOf(response.code()));
                        Log.e("Error Body", response.errorBody().toString());
                        Toast.makeText(getApplicationContext(),
                                "Unable to connect to Server. Please try again later.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t){
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
