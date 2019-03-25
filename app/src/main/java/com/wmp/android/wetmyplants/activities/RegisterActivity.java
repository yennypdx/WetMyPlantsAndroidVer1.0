package com.wmp.android.wetmyplants.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.otto.Bus;
import com.wmp.android.wetmyplants.R;
import com.wmp.android.wetmyplants.model.User;
import com.wmp.android.wetmyplants.model.UserService;
import com.wmp.android.wetmyplants.restAdapter.BusProvider;
import com.wmp.android.wetmyplants.restAdapter.Communicator;

public class RegisterActivity extends AppCompatActivity {

    /**Declaring related classes*/
    private Communicator communicator;
    private final static String TAG = "RegisterActivity";
    public static Bus bus;
    private UserService userService;

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

        /**ONLY for connection testing purpose. DELETE when done.*/
        communicator.registerPost("test", "test", "5037654321", "test@test.test", "password");

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
            public void onClick(View view)
            {
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
        String first_name = infname.getText().toString();
        String last_name = inlname.getText().toString();
        String phone_number = inphone.getText().toString();
        String email_addy = inemail.getText().toString();
        String pass_word = inpass.getText().toString();
        final User newUser = new User(first_name, last_name, phone_number, email_addy, pass_word);

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

    }

    private boolean isPhoneValid(String phone) { return phone.length() > 7; }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean>
    {
        private final String mFirstN;
        private final String mLastN;
        private final String mPhone;
        private final String mEmail;
        private final String mPass;

        UserRegisterTask(String fn, String ln, String ph, String ema, String pas) {
            mFirstN = fn;
            mLastN = ln;
            mPhone = ph;
            mEmail = ema;
            mPass = pas;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {
                return false;
            }

            /**proceed to send the HTTP request*/
            regPost(mFirstN, mLastN, mPhone, mEmail, mPass);
            return true;
        }

        private void regPost(String fn, String ln, String ph, String ema, String pas)
        {
            communicator.registerPost(fn, ln, ph, ema, pas);
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            if (success) {
                Intent intentToDashboard = new Intent(
                                RegisterActivity.this, DashboardActivity.class);
                startActivity(intentToDashboard);
            }
            else {
                mPasswordInput.setError(getString(R.string.error_incorrect_password));
                mPasswordInput.requestFocus();
            }
        }
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
