package com.android.wetmyplants;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wetmyplants.helperClasses.DbHelper;
import com.android.wetmyplants.model.UserCredentials;
import com.google.gson.JsonObject;
import com.android.wetmyplants.activities.DashboardActivity;
import com.android.wetmyplants.activities.ForgotPasswordActivity;
import com.android.wetmyplants.activities.RegisterActivity;
import com.android.wetmyplants.restAdapter.BusProvider;
import com.android.wetmyplants.restAdapter.Communicator;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity {

    private Communicator communicator;
    private DbHelper database;

    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        communicator = new Communicator();
        database = new DbHelper(getApplicationContext());

        final EditText inputEmail = findViewById(R.id.email);
        final EditText inputPassword= findViewById(R.id.password);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        inputPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

        Button mLogInButton = findViewById(R.id.log_in_button);
        mLogInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(inputEmail, inputPassword);
            }
        });

        Button mRegisterUserButton = findViewById(R.id.reg_user_button);
        mRegisterUserButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
            }
        });

        Button mNewPasswordButton = findViewById(R.id.forgot_password_button);
        mNewPasswordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getBaseContext(), ForgotPasswordActivity.class));
            }
        });
    }

    private void attemptLogin(EditText inEmail, EditText inPass) {
        final String email = inEmail.getText().toString();
        final String password = inPass.getText().toString();

        /**Reset errors.*/
        inEmail.setError(null);
        inPass.setError(null);

        boolean cancel = false;
        View focusView = null;

        /**Check for a valid password, if the user entered one.*/
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            inPass.setError(getString(R.string.error_invalid_password));
            focusView = inPass;
            cancel = true;
        }

        /**Check for a valid email address.*/
        if (TextUtils.isEmpty(email)) {
            inEmail.setError(getString(R.string.error_field_required));
            focusView = inEmail;
            cancel = true;
        }
        else if (!isEmailValid(email)) {
            inEmail.setError(getString(R.string.error_invalid_email));
            focusView = inEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();

        }
        else {
            showProgress(true);
            communicator.loginPost(email, password, new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()) {
                         String myToken = response.body();
                         JsonObject jsonObj = new JsonParser().parse(myToken).getAsJsonObject();
                         String token = jsonObj.get("content").toString();
                         token = token.substring(1, token.length()-1);

                        //storing token to db
                        UserCredentials user = new UserCredentials(email, token);
                        database.insertCredential(user);

                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        intent.putExtra("userEmail", email);
                        startActivity(intent);
                    }
                    else {
                        Log.e("Error Code", String.valueOf(response.code()));
                        Log.e("Error Body", response.errorBody().toString());
                        Toast.makeText(getApplicationContext(),
                                "Unable to connect to Server. Please try again later.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t){
                    Log.w("Error", t.getMessage());
                    Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(),
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

    /** Shows the progress UI and hides the login form. */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else  {

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }
}

