package com.wmp.android.wetmyplants.activities;

import android.content.Context;
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

    private Communicator communicator;

    public static final String appPREFERENCES = "appPref";
    public static final String Token = "tokenKey";
    public static final String Email = "emailKey";
    SharedPreferences sharedpref;
    SharedPreferences.Editor editor;

    EditText mFirstNameInput;
    EditText mLastNameInput;
    EditText mPhoneInput;
    EditText mEmailInput;
    EditText mPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        communicator = new Communicator();
        sharedpref = getSharedPreferences(appPREFERENCES, Context.MODE_PRIVATE);

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
    }

    private void attemptRegister(EditText infname, EditText inlname, EditText inphone,
                                 EditText inemail, EditText inpass)
    {
        final String firstName = infname.getText().toString();
        final String lastName = inlname.getText().toString();
        final String phoneNumber = inphone.getText().toString();
        final String emailAddress = inemail.getText().toString();
        final String passWord = inpass.getText().toString();

        mPhoneInput.setError(null);
        mEmailInput.setError(null);
        mPasswordInput.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(phoneNumber) && !isPhoneValid(phoneNumber)) {
            mPhoneInput.setError(getString(R.string.error_invalid_phone));
            focusView = mPhoneInput;
            cancel = true;
        }

        if (!TextUtils.isEmpty(passWord) && !isPasswordValid(passWord)) {
            mPasswordInput.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordInput;
            cancel = true;
        }

        if (TextUtils.isEmpty(emailAddress)) {
            mEmailInput.setError(getString(R.string.error_field_required));
            focusView = mEmailInput;
            cancel = true;
        }
        else if (!isEmailValid(emailAddress)) {
            mEmailInput.setError(getString(R.string.error_invalid_email));
            focusView = mEmailInput;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }
        else {
            communicator.registerPost(firstName, lastName, phoneNumber, emailAddress, passWord,
                    new Callback<JsonObject>(){
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                    if(response.isSuccessful()) {
                        String token = response.body().getAsJsonObject().toString();

                        //storing token to shared preference
                        editor = sharedpref.edit();
                        editor.putString(Token, token);
                        editor.putString(Email, emailAddress);
                        editor.apply();

                        startActivity(new Intent(
                                RegisterActivity.this, DashboardActivity.class));
                    }
                    else
                    {
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
                            RegisterActivity.this, LoginActivity.class));
                }
            });
        }
    }

    private boolean isPhoneValid(String phone) { return phone.length() > 10; }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
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
