package com.wmp.android.wetmyplants.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.restAdapter.Communicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewpassActivity extends AppCompatActivity {

    private Communicator communicator;

    /**UI references*/
    private EditText newPasswordOnce;
    private EditText newPasswordTwice;
    String PassOnce;
    String PassTwice;
    String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        communicator = new Communicator();

        newPasswordOnce = findViewById(R.id.newpass_input_one);
        PassOnce = newPasswordOnce.getText().toString();
        newPasswordTwice = findViewById(R.id.newpass_input_two);
        PassTwice = newPasswordTwice.getText().toString();

        Button updatePasswordButton = findViewById(R.id.new_password_button);
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(attemptConfirmMatch(PassOnce, PassTwice)){
                    attemptUpdatePassword();
                }
            }
        });
    }

    private boolean attemptConfirmMatch(String inPass1, String inPass2){
        if(inPass1 == inPass2){
            return true;
        }
        return false;
    }

    private void attemptUpdatePassword(){
        //TODO: complete this after APIController is done
    }
}
