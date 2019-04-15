package com.wmp.android.wetmyplants.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.wmp.android.wetmyplants.LoginActivity;
import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.restAdapter.Communicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPasswordActivity extends AppCompatActivity {

    private Communicator communicator;
    public static final String appPREFERENCES = "appPref";
    public static final String Token = "tokenKey";
    public static final String Email = "emailKey";
    SharedPreferences sharedpref;
    SharedPreferences.Editor editor;

    private EditText newPassword1;
    private EditText newPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        communicator = new Communicator();
        sharedpref = getSharedPreferences(appPREFERENCES, Context.MODE_PRIVATE);

        newPassword1 = findViewById(R.id.newpass_input_one);
        newPassword2 = findViewById(R.id.newpass_input_two);

        Button updatePasswordButton = findViewById(R.id.new_password_button);
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               attemptUpdatePassword(newPassword1, newPassword2);
            }
        });
    }

    private void attemptUpdatePassword(EditText inPass1, EditText inPass2){

        final String passWord1 = inPass1.getText().toString();
        final String passWord2 = inPass2.getText().toString();

        newPassword1.setError(null);
        newPassword2.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(passWord1) && !isPasswordValid(passWord1)) {
            newPassword1.setError(getString(R.string.error_invalid_password));
            focusView = newPassword1;
            cancel = true;
        }

        if (!TextUtils.isEmpty(passWord2) && !isPasswordValid(passWord2)) {
            newPassword2.setError(getString(R.string.error_invalid_password));
            focusView = newPassword2;
            cancel = true;
        }

        if(!attemptConfirmIsMatch(passWord1, passWord2)){
            newPassword2.setError(getString(R.string.error_NoMatch_password));
            focusView = newPassword2;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }
        else{
            communicator.updatePasswordPost(passWord2, new Callback<JsonObject>(){
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                    if(response.isSuccessful()) {
                        String token = response.body().getAsJsonObject().toString();

                        //storing token to shared preference
                        editor = sharedpref.edit();
                        editor.putString(Token, token);
                        editor.apply();

                        startActivity(new Intent(
                                NewPasswordActivity.this, DashboardActivity.class));
                    }
                    else
                    {
                        Log.e("Error Code", String.valueOf(response.code()));
                        Log.e("Error Body", response.errorBody().toString());
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Unable to connect to Server. Please try again later.",
                                Toast.LENGTH_LONG);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t){
                    Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(),
                            Toast.LENGTH_SHORT);
                    toast.show();

                    startActivity(new Intent(
                            NewPasswordActivity.this, LoginActivity.class));
                }
            });
        }
    }

    private boolean attemptConfirmIsMatch(String inPass1, String inPass2){
        if(inPass1 == inPass2) { return true; }
        return false;
    }

    private boolean isPasswordValid(String password) { return password.length() > 6; }
}
