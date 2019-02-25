package com.wmp.android.wetmyplants;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wmp.android.wetmyplants.model.User;
import com.wmp.android.wetmyplants.remote.APIUtilities;
import com.wmp.android.wetmyplants.remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    //UI references
    private String mFirstName;
    private String mLastName;
    private String mPhone;
    private String mEmail;
    private String mPassword;
    private View mDashboardView;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Connect to UserService
        userService = APIUtilities.getUserService();

        //set up the register form
        mFirstName = ((EditText) findViewById(R.id.first_name_field)).toString();
        mLastName = ((EditText) findViewById(R.id.last_name_field)).toString();
        mPhone = ((EditText) findViewById(R.id.phone_number_field)).toString();
        mEmail = ((EditText) findViewById(R.id.email_field)).toString();
        mPassword = ((EditText) findViewById(R.id.password_field)).toString();

        //TODO: continue with creating an object w/o const
        final User newUser = new User(mFirstName, mLastName, mPhone, mEmail, mPassword);
        //TODO: create the dashboard form to turn this red to white
        //mDashboardView = findViewById(R.id.dashboard_form);

        Button mRegisterUserButton = (Button) findViewById(R.id.register_user_button);
        mRegisterUserButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(attemptRegister(newUser))
                {
                    //TODO: route the user to Dashboard after registration completed.
                }
                else
                {
                    //TODO: user will have to reset password.
                }
            }
        });
    }

    private boolean attemptRegister(User inUser)
    {
        //TODO:send data to WebAppAPI, then save in local DB after return true.
        boolean answerFromWebAppApi = false;
        Call<User> call = userService.addUser(inUser);

        if(call != null)
        {
            answerFromWebAppApi = false;
        }
        else
        {
            answerFromWebAppApi = true;
        }

        return answerFromWebAppApi;
    }




}
