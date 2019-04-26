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

    EditText inputPassword1;
    EditText inputPassword2;
    private String outPass1;
    private String outPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        communicator = new Communicator();
        sharedpref = getSharedPreferences(appPREFERENCES, Context.MODE_PRIVATE);

        inputPassword1 = findViewById(R.id.newpass_input_one);
        inputPassword2 = findViewById(R.id.newpass_input_two);

        Button updatePasswordButton = findViewById(R.id.new_password_button);
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               attemptUpdatePassword(inputPassword1, inputPassword2);
            }
        });
    }

    private void attemptUpdatePassword(EditText inPass1, EditText inPass2){

        inPass1.setError(null);
        inPass2.setError(null);

        outPass1 = inPass1.getText().toString();
        outPass2 = inPass2.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(outPass1) && !isPasswordValid(outPass1)) {
            inPass1.setError(getString(R.string.error_invalid_password));
            focusView = inPass1;
            cancel = true;
        }

        if (!TextUtils.isEmpty(outPass2) && !isPasswordValid(outPass2)) {
            inPass2.setError(getString(R.string.error_invalid_password));
            focusView = inPass2;
            cancel = true;
        }

        if(!attemptConfirmIsMatch(outPass1, outPass2)){
            inPass2.setError(getString(R.string.error_NoMatch_password));
            focusView = inPass2;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }
        else{
            communicator.updatePasswordPost(outPass2, new Callback<JsonObject>(){
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

    private boolean attemptConfirmIsMatch(String inPasswd1, String inPasswd2){
        if(inPasswd2.equals(inPasswd1)) { return true; }
        return false;
    }

    private boolean isPasswordValid(String password) { return password.length() > 6; }
}
