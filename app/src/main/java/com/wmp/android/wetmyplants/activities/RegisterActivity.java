package com.wmp.android.wetmyplants.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.wmp.android.wetmyplants.LoginActivity;
import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.helperClasses.DatabaseConnector;
import com.wmp.android.wetmyplants.restAdapter.BusProvider;
import com.wmp.android.wetmyplants.restAdapter.Communicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    /**Declaring related classes*/
    private Communicator communicator;
    private DatabaseConnector databaseConnector;

    /**UI references*/
    private EditText mFirstNameInput;
    private EditText mLastNameInput;
    private EditText mPhoneInput;
    private EditText mEmailInput;
    private EditText mPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        communicator = new Communicator();
        databaseConnector = new DatabaseConnector(RegisterActivity.this);

        //set up the register form
        mFirstNameInput = findViewById(R.id.first_name_field);
        mLastNameInput = findViewById(R.id.last_name_field);
        mPhoneInput = findViewById(R.id.phone_number_field);
        mEmailInput = findViewById(R.id.email_field);
        mPasswordInput = findViewById(R.id.password_field);

        Button mRegisterUserButton = findViewById(R.id.register_user_button);
        mRegisterUserButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                attemptRegister(mFirstNameInput, mLastNameInput, mPhoneInput,
                                    mEmailInput, mPasswordInput);
            }
        });

        //TODO: BACK button & Route to Dashboard
        //mDashboardView = findViewById(R.id.dashboard_form);
    }

    private void attemptRegister(EditText infname, EditText inlname, EditText inphone,
                                 EditText inemail, EditText inpass)
    {
        final String first_name = infname.getText().toString();
        final String last_name = inlname.getText().toString();
        final String phone_number = inphone.getText().toString();
        final String email_addy = inemail.getText().toString();
        final String pass_word = inpass.getText().toString();

        mPhoneInput.setError(null);
        mEmailInput.setError(null);
        mPasswordInput.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(phone_number) && !isPhoneValid(phone_number)) {
            mPhoneInput.setError(getString(R.string.error_invalid_phone));
            focusView = mPhoneInput;
            cancel = true;
        }

        if (!TextUtils.isEmpty(pass_word) && !isPasswordValid(pass_word)) {
            mPasswordInput.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordInput;
            cancel = true;
        }

        if (TextUtils.isEmpty(email_addy)) {
            mEmailInput.setError(getString(R.string.error_field_required));
            focusView = mEmailInput;
            cancel = true;
        }
        else if (!isEmailValid(email_addy)) {
            mEmailInput.setError(getString(R.string.error_invalid_email));
            focusView = mEmailInput;
            cancel = true;
        }

        if (cancel)
        {
            focusView.requestFocus();
        }
        else {
            communicator.registerPost(first_name, last_name, phone_number, email_addy, pass_word,
                    new Callback<JsonObject>(){
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                    if(response.isSuccessful()) {
                        //get token from webapi and save it to dblite
                        response.body();
                        String token = response.body().getAsJsonObject().toString();
                        databaseConnector.open();
                        databaseConnector.insertUserToken(token, email_addy);
                        databaseConnector.close();

                        //Pass the emailKey to Dashboard
                        Intent key = new Intent(
                                RegisterActivity.this, DashboardActivity.class);
                        key.putExtra("emailKey", email_addy);
                        startActivity(key);
                    }
                    else
                    {
                        Log.e("Error Code", String.valueOf(response.code()));
                        Log.e("Error Body", response.errorBody().toString());
                        Toast toast = Toast.makeText(getApplicationContext(), "Unable to connect to Server. Please try again later.",
                                Toast.LENGTH_LONG);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t){
                    Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(),
                            Toast.LENGTH_SHORT);
                    toast.show();

                    //TODO: Upgrade to having this Intent to pulled the email above to Login UI
                    Intent backToLogin = new Intent(
                            RegisterActivity.this, LoginActivity.class);
                    startActivity(backToLogin);
                }
            });
        }
    }

    private boolean isPhoneValid(String phone) { return phone.length() > 7; }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }
}
